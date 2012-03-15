#!/usr/bin/perl
use strict;
use warnings;
use POSIX;
use FindBin qw'$Bin';
use File::Copy qw'cp';

my ($sysname, $nodename, $release, $version, $machine) = POSIX::uname();
print "Running under $sysname on $machine\n";

chdir $Bin;
system("ant") and die "Must be able to build.";
print "Done building java code\n";

my $libName = `java -cp build/jar/hunspell.jar dk.dren.hunspell.HunspellMain -libname`;
die "Don't know this platform, implement support for this platform in Hunspell.java (and possibly Hunspell and JNA)"
	unless $libName;
chomp $libName;
print "Going to try to build $libName\n";
$libName = "$Bin/native-lib/$libName";

die "Whoa there, cowboy, $libName already exists, delete it if you want to rebuild!" if -f $libName;

# Untar and apply the diffs found.

sub untar() {

	opendir D, "$Bin/native-src" or die "Urgh: $!";
	my @nsf = readdir D;
	closedir D;

	my ($huntar) = grep {/^hunspell-.*\.tar\.gz$/} @nsf; 
	die "Unable to find the hunspell tar file in native-src" unless $huntar;

	chdir "$Bin/native-src";
	system("tar xfz $huntar") and die "Unable to untar $huntar";

	# Now find the source dir:
	opendir D, "$Bin/native-src" or die "Urgh: $!";
	my ($hundir) = grep {-d $_ and /^hunspell-/} readdir D;
	closedir D;

	die "Unable to find the hunspell source dir" unless $hundir;

	for my $p (grep {/\.diff$/} @nsf) {
		system("patch -u -p 2 -d $hundir -i ../$p") and die "Unable to apply patch: $p";
	}
	
	chdir "$Bin/native-src/$hundir";
	return "$Bin/native-src/$hundir";
}

mkdir "$Bin/native-lib";

my $unixy = 0;
if ($sysname eq 'Linux') {
    if ($machine eq 'i686' or $machine eq 'x86_64') {
		$unixy = 1;
    } else {
		die "The architecture $machine is not supported, please fix native-build.pl";		
    }
    
} elsif ($sysname eq 'Darwin') {
    if ($machine eq 'Power Macintosh') {
		$unixy = 1;
    } elsif ($machine eq 'i386') {
		$unixy = 1;
    } else {
		die "The architecture $machine is not supported, please fix native-build.pl";		
    }
} 


if ($unixy) { # aah, a sane OS, so life is simple.

    my $ns = untar;	
    system("./configure && make") and die "Unable to configure and make, please fix";  
    
    my $outputDir = "$ns/src/hunspell/.libs";
    chdir $outputDir or die "Unable to chdir to $outputDir: $!";
    opendir D, "." or die "Urgh: $!";
    my ($output) = grep {!readlink($_) and /^libhunspell-.*\.(so|dylib$)/} readdir D;
    closedir D;
    
    die "Unable to find dynamic hunspell lib in $outputDir" unless $output;
    
    cp("$outputDir/$output", $libName) 
		or die "Unable to copy $outputDir/$output to $Bin/native-lib/$libName: $!";
    
    system("rm -rf $ns");

} elsif ($sysname eq 'Windows NT') {

    if ($machine eq 'x86') {
		my $ns = untar;
		my $apiDir = "$ns/src/win_api";
		chdir $apiDir  or die "Unable to chdir to $apiDir";
		my $cmd = "devenv Hunspell.sln /build Release_dll /project libhunspell";
		print "Compiling...\n";
		system($cmd) and die "Unable to run '$cmd' in $apiDir, good luck figuring out why";
		print "Done compiling.\n";
		
		cp "$apiDir/Release_dll/libhunspell/libhunspell.dll", $libName
			or die "Unable to copy $apiDir/Release_dll/libhunspell/libhunspell.dll to $libName: $!";
		system("rm -rf $ns");
		
    } else {
		die "The architecture $machine is not supported, please fix native-build.pl";				
    }

} else {
	die "The $sysname on $machine is not supported, please fix native-build.pl";
}

print "Check out $libName\n";
exit 0;
