package hw8;
import java.io.Reader;

public interface ISpellChecker 
{
	/** Read a dictionary from the specified reader
	 * 
	 * import java.io.FileReader
	 * 
	 * spellChecker.ReadDictionary(new FileReader(filename));
	 * 
	 * @param reader a character stream
	 */
	void ReadDictionary(Reader reader);
	/** Check if the given word is properly spelled
	 * 
	 * If the given word is properly spelled return null.
	 * If the word is not properly spelled, return all variants as
	 * described by the homework write up. 
	 * 
	 * If no variants were found return an empty array.
	 * 
	 * @param word word to check
	 * @return null (word spelled correctly), array of variants (if not), or empty array (if no variants)
	 */
	
	String[] CheckWord(String word);
}
