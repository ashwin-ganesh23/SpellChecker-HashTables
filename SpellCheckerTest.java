package hw8;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Before;
import org.junit.Test;

public class SpellCheckerTest {

	private ISpellChecker spellChecker;

	@Before
	public void setUp() throws Exception {
		spellChecker = new SpellChecker();
		
		String data = "about\nabove\nabsolutely\nacceptable\nadd\nadjacent\nafter\nalgorithm\nall\nalong\nalso\nan";
		
		spellChecker.ReadDictionary(new StringReader(data));
	}

	@Test
	public void CheckCorrectWord() {
		String[] variants = spellChecker.CheckWord("acceptable");
		assertNull(variants);
	}
	@Test
	public void CheckMisspelledWord() {
		String[] expected={"above"};
		String[] variants = spellChecker.CheckWord("abuve");
		assertArrayEquals(variants, expected);
	}
	@Test
	public void CheckMisspelledWordWithNoVariant() {
		String[] expected={};
		String[] variants = spellChecker.CheckWord("bbbbb");
		assertArrayEquals(variants, expected);
	}

}
