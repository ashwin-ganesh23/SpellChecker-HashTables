package hw8;

import static org.junit.Assert.*; 

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.junit.*;
import org.junit.Before;
import org.junit.Test;

public class HashTableTest 
{
	private IHashTable hashTable = new HashTable();
    
    @Before
    public void setUp() {
    	hashTable = new HashTable(100);
    }
    
	@Test
	public void TestAdd() 
	{
		for(int i=0;i<50;i++)
			assertTrue(hashTable.Insert(""+i));
		for(int i=0;i<50;i++)
			assertFalse(hashTable.Insert(""+i));
	}
	@Test
	public void TestLookup() 
	{
		for(int i=0;i<50;i++)
			assertFalse(hashTable.Lookup(""+i));
		for(int i=0;i<50;i++)
			hashTable.Insert(""+i);
		for(int i=0;i<50;i++)
			assertTrue(hashTable.Lookup(""+i));
	}
	@Test
	public void TestDelete() 
	{
		for(int i=0;i<50;i++)
			assertFalse(hashTable.Delete(""+i));
		for(int i=0;i<50;i++)
			hashTable.Insert(""+i);
		for(int i=0;i<50;i++)
			assertTrue(hashTable.Delete(""+i));
	}
	@Test
	public void TestPrint() 
	{
		String expected="";
		for(int i=0;i<50;i++)
		{
			hashTable.Insert(""+i);
			expected += "";
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		hashTable.Print(ps);
		try {
			String content = baos.toString("UTF8");
			assertEquals(content, expected);
		} catch (UnsupportedEncodingException e) {
			fail("Unable to convert to string");
		}
	}

}
