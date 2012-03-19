package dk.dren.hunspell;

import java.util.List;

/**
 * Simple testing and native build utility class, not useful in applications.
 *
 * The Hunspell java bindings are licensed under the same terms as Hunspell itself (GPL/LGPL/MPL tri-license),
 * see the file COPYING.txt in the root of the distribution for the exact terms.
 *
 * @author Flemming Frandsen (flfr at stibo dot com)
 */

public class HunspellMain {

	private static void println(String msg) {
		System.err.println(msg);
	}
	private static void print(String msg) {
		System.err.print(msg);
	}

    public static void main(String[] args) {
		try {
			if (args.length == 1 && args[0].equals("-libname")) {
				System.out.println(Hunspell.libName());
			
			} else { 

				System.err.println("Loading Hunspell");
				String dir = "/home/ff/projects/hunspell-java";
				if (System.getProperties().containsKey("root")) {
					dir = System.getProperty("root");
				}				
			
				Hunspell.Dictionary d = Hunspell.getInstance().getDictionary(dir+"/dict/da_DK");
				System.err.println("Hunspell library and dictionary loaded");
						
				String words[] = {"Test", "Hest", "guest", "ombudsmandshat", "ombudsman",
								  "ymerfest", "grøftegraver", "hængeplante", "garageport", "postbil", "huskop",
								  "arne", "pladderballe", "Doctor", "Leo", "Lummerkrog",
								  "Barnevognsbrand","barnehovedbeklædning",
								  "ymer", "drys", "ymerdrys",
								  "æsel", "mælk", "æselmælk"};
			
				for (int i=0;i<words.length;i++) {
				
					String word = words[i];
					if (d.misspelled(word)) {
						List<String> suggestions = d.suggest(word);
						print("misspelled: "+word);
						if (suggestions.isEmpty()) {
							print("\tNo suggestions.");
						} else {
							print("\tTry:");
							for (String s : suggestions) {
								print(" "+s);
							}	
						}
						println("");
					} else {
						println("ok: "+word);		
					}
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			println("Failed: "+e);
		}
    }
}

