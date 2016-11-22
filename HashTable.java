package hw8;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class HashTable implements IHashTable {

	NodeLL[] array;
	int arraySize;
	int itemCount = 0;
	int hashValue, c, rehash, longest;
	float loadFactor;
	final double load = 0.75;
	static Scanner scanner;

	public HashTable() {
		arraySize = 100;
		array = new NodeLL[arraySize];
		for (int y = 0; y<arraySize; y++){
			array[y] = new NodeLL();
		}
		itemCount = 0;
		c = 0;
		rehash = 0;
		longest = 0;
		loadFactor = itemCount/arraySize;
	}

	public HashTable(int size) {
		arraySize = size;
		array = new NodeLL[size];

		for (int y = 0; y<arraySize; y++){
			array[y] = new NodeLL();
		}
		itemCount = 0;
		c = 0;
		rehash = 0;
		longest = 0;
		loadFactor = itemCount/arraySize;
	}

	/** Insert the string value into the hash table
	 *
	 * @param value value to insert
	 * @return true if the value was inserted, false if the value was already present
	 */
	@Override
	public boolean Insert(String value) {
		if (load * arraySize < itemCount) {
			DoubleUp();
		}
		hashValue = hashFunction(value);
		if (Lookup(value)) {
			return false;
		} else {
			if (array[hashValue].nelems >= 1) {
				c++;
				if (array[hashValue].nelems > longest) {
					longest = array[hashValue].nelems;
				}
			}
			array[hashValue].add(value);
			itemCount++;
			loadFactor = (float) itemCount / arraySize;
			writeStats();
			return true;
		}
	}

	public void writeStats(){
		PrintWriter writer;
		try {
			writer = new PrintWriter("HW8.txt", "UTF-8");
			writer.println(rehash + " resizes, load factor " + loadFactor + ", " + c + " collisions, " + longest + " longest chain");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/** Delete the given value from the hash table
	 *
	 * @param value value to delete
	 * @return true if the value was deleted, false if the value was not found
	 */
	@Override
	public boolean Delete(String value) {
		hashValue = hashFunction(value);
		if(Lookup(value)){
			array[hashValue].remove(array[hashValue].get(value));
			return true;
		}else{
			return false;
		}
	}

	/** Check if the given value is present in the hash table
	 *
	 * @param value value to look up
	 * @return true if the value was found, false if the value was not found
	 */
	@Override
	public boolean Lookup(String value) {
		hashValue = hashFunction(value);
		int t = array[hashValue].get(value);
		if(t==-1){
			return false;
		}else{
			return true;
		}
	}

	public void DoubleUp(){
		NodeLL[] temp = array;
		int t = arraySize;
		arraySize = arraySize * 2;
		array = new NodeLL[arraySize*2];
		longest = 0;
		loadFactor = 0;
		c = 0;

		for (int y = 0; y<t; y++){
			array[y] = new NodeLL();
		}

		for(int x = 0; x<=t; x++){
			for(int y = 0; y < temp[x].size(); y++){
				Insert(temp[x].get(y));
			}
		}
		rehash++;
	}

	public int hashFunction(String item)
	{
		int hashKey = 0;

		for (int x = 0; x<item.length(); x++)
		{
			int askey = item.charAt(x) - 47;
			hashKey = (hashKey * 27 + askey) % arraySize;
		}
		return hashKey;
	}

	@Override
	public void Print(PrintStream out) {
		for(int x = 0; x<arraySize; x++){
			out.print(x + ": ");
			for(int y = 0; y < array[x].size(); y++){
				out.print(array[x].get(y));
				if(array[x].size() > y+1){
					out.print(",");
				}
			}
			out.println(" ");
		}
	}

	public static void main(String[] args) // This is used only for Part 1
	{
		HashTable ht = new HashTable(10);
		ht.Insert("a");
		ht.Insert("b");


		if(args.length != 1)
		{
			System.err.println("Expected only 1 argument");
			return;
		}
		try{
			scanner = new Scanner(new File(args[0]));
			while(scanner.hasNextLine())
			{
			     String s = scanner.nextLine();
			     String[] sa = s.split(" ", 2);
			     if(sa[0].equals("insert")){
			    	 if(ht.Insert(sa[1])){
			    		 System.out.println("item " + sa[1] + " successfully inserted");
			    	 }else{
			    		 System.out.println("item " + sa[1] + " already present");
			    	 }
			     }else if(sa[0].equals("delete")){
			    	 if(ht.Delete(sa[1])){
			    		 System.out.println("item " + sa[1] + " successfully deleted");
			    	 }else{
			    		 System.out.println("item " + sa[1] + " not found");
			    	 }
			     }else if(sa[0].equals("lookup")){
			    	 if(ht.Lookup(sa[1])){
			    		 System.out.println("item " + sa[1] + " found");
			    	 }else{
			    		 System.out.println("item " + sa[1] + " not found");
			    	 }
			     }
			}
		}catch(FileNotFoundException ex)
		{
			System.err.println("File not found: "+args[0]);
			return;
		}

	}


}

class Node{
	public String word;
	public int key;

	public Node next, prev;

	public Node(String word){
		this.word = word;
	}

	public Node(String data, Node prev, Node tail) {
		this.word = data;
		this.prev = prev;
		this.next = tail;
	}

	public void setNext(Node a){
		next = a;
	}

	public void setPrev(Node b){
		prev = b;
	}
	public Node getNext(){
		return next;
	}
	public Node getPrev(){
		return prev;
	}

	public void setString(String s){
		word = s;
	}
	public String toString(){
		return word;
	}
}

class NodeLL {
	int nelems, placeholder;
	Node head, tail, temp;
	String tempword, elem;

	/**
     * 	Constructor to create new linked
     *   @param element Element to add, can be null
     *   @param prevNode predecessor Node, can be null
     *   @param nextNode successor Node, can be null
     */
	NodeLL() {
		nelems = 0;
		head = new Node(null);
		tail = new Node(null);
		head.setNext(tail);
		tail.setPrev(head);
		placeholder = 0;
	}

	/**
	 * returns size of list (number of elements)
	 * @return int size of list
	 */

	public int size()
	{
		return nelems;
	}

	/**
	 * Gets value of element at index given
	 * @return E value of element at index
	 * @param index - index at which we want to get element
	 * @throws IndexOutOfBoundsException
	 */

	public String get(int index) throws IndexOutOfBoundsException
	{
		if (index < 0 || index >= nelems) {
			throw new IndexOutOfBoundsException();
		} else {
			return getNth(index).word;
		}
	}

	/**
	 * Gets index of value given
	 * @return int value of element's index
	 * @param word - word at which we want index
	 */

	public int get(String word)
	{
		for (int i = 0; i < nelems; i++) {
			if (word.equals(get(i))) {
				return i;
			}
		}
		return -1;
	}

	/** Add an element to the end of the list
	 * @param data data to add
	 * @throws NullPointerException
	 */
	public boolean add(String data) throws NullPointerException
	{
		try{
			tail.setPrev(new Node(data, tail.getPrev(), tail));
			tail.getPrev().getPrev().setNext(tail.getPrev());
			nelems++;
			return true;
		}catch(NullPointerException e){
			System.out.println(e.getMessage());
		}
		return false;
	}

	/** Set the element at an index in the list
	 * @param index  where in the list to add
	 * @param data data to add
	 * @return element that was previously at this index.
	 * @throws IndexOutOfBoundsException
	 * @throws NullPointerException
	 * @throws IllegalStateExceptoin
	 */
	public String set(int index, String data) throws IndexOutOfBoundsException,NullPointerException
	{
	  try{
		  tempword = getNth(index).toString();
		  getNth(index).setString(data);
	  }catch(IndexOutOfBoundsException e){
		  System.out.println(e.getMessage());
	  }catch(NullPointerException e){
		  System.out.println(e.getMessage());
		}
	  return tempword;
	}

	/** Remove the element at an index in the list
	 * @param index  where in the list to add
	 * @return element the data found
	 * @throws IndexOutOfBoundsException
	 * @throws IllegalStateException
	 */
	public String remove(int index) throws IndexOutOfBoundsException, IllegalStateException
	{
		try{
			elem = getNth(index).toString();
			getNth(index+1).setPrev(getNth(index -1));
			getNth(index-1).setNext(getNth(index + 1));
			nelems--;
		}catch(IndexOutOfBoundsException e){
			System.out.println(e.getMessage());
		}catch(IllegalStateException e){
			System.out.println(e.getMessage());
		}
		return elem;
	}

	/**
	 * Clear the linked list
	 */
	public void clear()
	{
		nelems = 0;
		head.setNext(tail);
		tail.setPrev(head);
	}

	/** Determine if the list empty
	 * @return boolean true if empty, false otherwise
	 */
	public boolean isEmpty()
	{
		if(nelems == 0){
			return true;
		}else{
			return false;
		}
	}



	/**
	 *  Helper method to get the Node at the Nth index
	 * @param index index to get
	 * @return Node element we want to get
	 * @throws IndexOutOfBoundsException
	 */
	private Node getNth(int index) throws IndexOutOfBoundsException
	{
		temp = head;
		placeholder = -1;
		try {
			while (temp != null ) {
				if (placeholder == index) {
					return temp;
				}
				temp = temp.getNext();
				placeholder++;
			}

		} catch(IndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
		}
		return temp;
	}
}
