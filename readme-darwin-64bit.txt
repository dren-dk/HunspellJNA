The OSX 64 bit hunspell library was compiled by Andrzej Zydron azydron at xml-intl dot com, this is the message he sent:

------------------------------

I have just built a 64 bit version of Hunspell for Mac OS 10.5. I had to do this because Java 1.6 under Leopard is compiled as a 64 bit executable and would not load the standard 32 bit build of hunspell. I had to configure using the following:

CFLAGS='-arch x86_64 -g -O2' CXXFLAGS='-arch x86_64 -g -O2' CPPFLAGS='-arch x86_64 -g -O2' LDFLAGS='-arch x86_64' ./configure --with-gnu-ld

and then I had to edit 'libtool' to add -arch x86_64 to all instances of '"\$CC -dynamiclib ' as per the following diff:

> diff libtool{,~}
214c214
< archive_cmds="\$CC -arch x86_64 -dynamiclib \$allow_undefined_flag -o \$lib \$libobjs \$deplibs \$compiler_flags -install_name \$rpath/\$soname \$verstring \$single_module~dsymutil \$lib || :"
---
> archive_cmds="\$CC -dynamiclib \$allow_undefined_flag -o \$lib \$libobjs \$deplibs \$compiler_flags -install_name \$rpath/\$soname \$verstring \$single_module~dsymutil \$lib || :"
6966,6967c6966,6967
< archive_cmds="\$CC -dynamiclib -arch x86_64  \$allow_undefined_flag -o \$lib \$libobjs \$deplibs \$compiler_flags -install_name \$rpath/\$soname \$verstring \$single_module~dsymutil \$lib || :"
< archive_expsym_cmds="sed 's,^,_,' < \$export_symbols > \$output_objdir/\${libname}-symbols.expsym~\$CC -dynamiclib -arch x86_64 \$allow_undefined_flag -o \$lib \$libobjs \$deplibs \$compiler_flags -install_name \$rpath/\$soname \$verstring \$single_module \${wl}-exported_symbols_list,\$output_objdir/\${libname}-symbols.expsym~dsymutil \$lib || :"
---
> archive_cmds="\$CC -dynamiclib \$allow_undefined_flag -o \$lib \$libobjs \$deplibs \$compiler_flags -install_name \$rpath/\$soname \$verstring \$single_module~dsymutil \$lib || :"
> archive_expsym_cmds="sed 's,^,_,' < \$export_symbols > \$output_objdir/\${libname}-symbols.expsym~\$CC -dynamiclib \$allow_undefined_flag -o \$lib \$libobjs \$deplibs \$compiler_flags -install_name \$rpath/\$soname \$verstring \$single_module \${wl}-exported_symbols_list,\$output_objdir/\${libname}-symbols.expsym~dsymutil \$lib || :"
>

I am not sure if the '--with-gnu-ld' was absolutely necessary, but it works. Please feel free to publish this info if it will help others.

