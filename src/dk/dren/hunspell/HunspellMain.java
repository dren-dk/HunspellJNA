package dk.dren.hunspell;

import java.util.List;

/**
 * Simple testing and native build utility class, not useful in applications.
 *
 * The Hunspell java bindings are licensed under the same terms as Hunspell itself (GPL/LGPL/MPL tri-license),
 * see the file COPYING.txt in the root of the distribution for the exact terms.
 *
 * @author Flemming Frandsen (flfr at stibo dot com)
 * @author Hartmut Goebel (h dot goebel at crazy-compilers dot com)
 *
 *  Usage:  java -Droot=/usr/share/dicts -Dlang=de_DE HunspellMain
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
				String dir = "/usr/share/hunspell";
				if (System.getProperties().containsKey("root")) {
					dir = System.getProperty("root");
				}

				String language = "da_DK";
				if (System.getProperties().containsKey("lang")) {
					language = System.getProperty("lang");
				}

				Hunspell.Dictionary d = Hunspell.getInstance().getDictionary(dir+"/"+language);
				System.err.println("Hunspell library and dictionary loaded");

				String words[] = {"Test", "Hest", "guest", "ombudsmandshat", "ombudsman",
								  "ymerfest", "g0r\u00f8ftegraver", "h\u00e6ngeplante", "garageport", "postbil", "huskop",
								  "arne", "pladderballe", "Doctor", "Leo", "Lummerkrog",
								  "Barnevognsbrand","barnehovedbekl\u00e6dning",
								  "ymer", "drys", "ymerdrys",
								  "\u00e6sel", "m\u00e6lk", "\u00e6selm\u00e6lk",
						  "Brotbacken", "Pausbacken", "pausbackig", "Backenknochenbruch",
						  "Donnerdampfschifffahrt",
						  "Donnerdampfschifffahrtsgesellschaftskapit\u00e4n",
						  "Messer", "Schleifer", "Messerschleifer",
						  "muss", "mu\u00df"
						  };

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

