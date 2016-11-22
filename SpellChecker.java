package hw8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellChecker implements ISpellChecker {
	
	static Scanner scanner;
	String[] words;
	int count, len;
	HashTable ht;
	BufferedReader input;
	String w, check;
	ArrayList<String> al;
	
	public SpellChecker(){
		ht = new HashTable(1000);
	}
	
	/** Read a dictionary from the specified reader
	 * 
	 * import java.io.FileReader
	 * 
	 * spellChecker.ReadDictionary(new FileReader(filename));
	 * 
	 * @param reader a character stream 
	 */
	@Override
	public void ReadDictionary(Reader reader){
		input = new BufferedReader(reader);
		count = 0;
		try {
			while(input.ready()){
				w = input.readLine();
				if(w == null){
					return;
				}else{
					ht.Insert(w);
				}
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

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
	@Override
	public String[] CheckWord(String word) {
		String [] correct = new String[0];
		al = new ArrayList<String>();
		check = word.toLowerCase();
		if(ht.Lookup(check)){
			return null;
		}
		//check for wrong letter
		for(int y =0; y < word.length(); y++){
			for(int z = 0; z< 26; z++){
				if(y==0){
					check = String.valueOf((char) (z+ 97)) + word.substring(y+1, word.length());
				}else if(y == word.length()-1){
					check = word.substring(0,y) + String.valueOf((char) (z+ 97));
				}else{
					check = word.substring(0,y) + String.valueOf((char) (z+ 97)) + word.substring(y+1, word.length());
				}
				if(ht.Lookup(check)){
					al.add(check);
				}
			}
		}
		//check for inserted letter
		
		//check for deleted letter
		
		//check for adjacent transposed letters
		if(al.size() > 0){
			correct = new String[al.size()];
			for(int i = 0; i<al.size(); i++){
				correct[i] = al.get(i);
			}
		}
		
		return correct;
	}
	public static void main(String[] args)  // This is used for Part 2
	{
		SpellChecker sp = new SpellChecker();
		
		if(args.length != 1)
		{
			System.err.println("Expected only 1 argument");
			return;
		}
		try{
			sp.ReadDictionary(new FileReader(args[0]));
		}catch(FileNotFoundException ex)
		{
			System.err.println("File not found: "+args[0]);
			return;
		}
	}

}
