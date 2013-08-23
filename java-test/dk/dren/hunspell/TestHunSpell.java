/**
 *
 */
package dk.dren.hunspell;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author pbri
 *
 */
public class TestHunSpell {

	private String dir;
	private String language;
	private Hunspell instance;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.err.println("Loading Hunspell");
		dir = "/usr/share/hunspell";
		if (System.getProperties().containsKey("root")) {
			dir = System.getProperty("root");
		}

		language = "da_DK";

		instance = Hunspell.getInstance();
		Hunspell.Dictionary d = instance.getDictionary(
				dir + "/" + language);
	}

	/**
	 * Test method for {@link dk.dren.hunspell.Hunspell#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		assertNotNull("Could not obtain instance", Hunspell.getInstance());
	}

	/**
	 * Test method for
	 * {@link dk.dren.hunspell.Hunspell#getInstance(java.lang.String)}.
	 */
	@Test
	public void testGetInstanceString() {
		assertNotNull("Could not obtain instance", Hunspell.getInstance(dir));
	}

	/**
	 * Test method for {@link dk.dren.hunspell.Hunspell#getLibFile()}.
	 */
	@Test
	public void testGetLibFile() {
		assertNotNull("Could not get libfile", instance.getLibFile());
	}

	/**
	 * Test method for {@link dk.dren.hunspell.Hunspell#libName()}.
	 */
	@Test
	public void testLibName() {
		System.out.println(Hunspell.libName());
	}

	/**
	 * Test method for {@link dk.dren.hunspell.Hunspell#libNameBare()}.
	 */
	@Test
	public void testLibNameBare() {
		System.out.println(Hunspell.libNameBare());
	}

	/**
	 * Test method for
	 * {@link dk.dren.hunspell.Hunspell#getDictionary(java.lang.String)}.
	 * @throws UnsupportedOperationException
	 * @throws UnsatisfiedLinkError
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	@Test
	public void testGetDictionary() throws FileNotFoundException, UnsupportedEncodingException, UnsatisfiedLinkError, UnsupportedOperationException {
		assertNotNull("Could not obtain dictionary", Hunspell.getInstance(dir)
				.getDictionary(dir+"/"+language));
	}

	/**
	 * Test method for
	 * {@link dk.dren.hunspell.Hunspell#destroyDictionary(java.lang.String)}.
	 */
	@Test
	public void testDestroyDictionary() {
		instance.destroyDictionary(dir+"/"+language);
	}

}
