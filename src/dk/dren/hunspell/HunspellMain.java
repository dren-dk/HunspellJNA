package dk.dren.hunspell;

import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Simple testing and native build utility class, not useful in applications.
 *
 * The Hunspell java bindings are licensed under LGPL, see the file COPYING.txt
 * in the root of the distribution for the exact terms.
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
			
						
				String words[] = {"Test", "Hest", "guest", "ombudsmandshat", "ombudsman",
								  "ymerfest", "grøftegraver", "hængeplante", "garageport", "postbil", "huskop",
								  "arne", "pladderballe", "Doctor", "Leo", "Lummerkrog",
								  "Barnevognsbrand","barnehovedbeklædning"};
			
				for (int i=0;i<words.length;i++) {
				
					for (int j=0;j<3;j++) {
						String word = words[i];
						if (d.misspelled(word)) {
							print("misspelled: "+word);
							List<String> suggestions = d.suggest(word);
							print("\tTry:");
							for (String s : d.suggest(word)) {
								print(" "+s);
							}	
							println("");
						} else {
							println("ok: "+word);		
						}
					}
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			println("Failed: "+e);
		}
    }
}

