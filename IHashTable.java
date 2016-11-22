package hw8;

import java.io.PrintStream;

public interface IHashTable {

	/** Insert the string value into the hash table
	 * 
	 * @param value value to insert
	 * @return true if the value was inserted, false if the value was already present
	 */
	boolean Insert(String value);
	/** Delete the given value from the hash table
	 * 
	 * @param value value to delete
	 * @return true if the value was deleted, false if the value was not found
	 */
	boolean Delete(String value);
	/** Check if the given value is present in the hash table
	 * 
	 * @param value value to look up
	 * @return true if the value was found, false if the value was not found
	 */
	boolean Lookup(String value);
	/** Print the contents of the hash table to the given print stream
	 * 
	 * Call this function using hashTable.Print(System.out);
	 * 
	 * Inside this function use: out.println(key+": "+value);
	 * Do not use System.out.println in this function!
	 * 
	 * Example output for this function:
	 * 
	 * 0:
	 * 1:
	 * 2: marina, fifty
	 * 3: table
	 * 4:
	 * 
	 * @param out the output stream
	 */
	void Print(PrintStream out);
}
