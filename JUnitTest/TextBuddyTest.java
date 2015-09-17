import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TextBuddyTest {
	
	@Before
	public void setUp() {
		//clear the file to prevent the written contents to interfere with the testing
		TextBuddy.executeCommandByType("mytextfile.txt", "clear", "clear");
	}
	
	@Test
	public void testAdd() {
		String expected = "added to mytextfile.txt: \"This is the line being added.\"";
		assertEquals("add a simple line", expected, TextBuddy.executeCommandByType("mytextfile.txt", "add", "add This is the line being added."));
		expected = "1. This is the line being added.";
		assertEquals("check whether content is added", expected, TextBuddy.executeCommandByType("mytextfile.txt", "display", "display"));
		
		TextBuddy.executeCommandByType("mytextfile.txt", "clear", "clear");
		
		expected = "added to mytextfile.txt: \"add add add\"";
		assertEquals("add multiple words same as command", expected, TextBuddy.executeCommandByType("mytextfile.txt", "add", "add add add add"));
		expected = "1. add add add";
		assertEquals("check whether content is added", expected, TextBuddy.executeCommandByType("mytextfile.txt", "display", "display"));

		TextBuddy.executeCommandByType("mytextfile.txt", "clear", "clear");
		
		expected = "added to mytextfile.txt: \"\"";
		assertEquals("add without entering content", expected, TextBuddy.executeCommandByType("mytextfile.txt", "add", ""));
		expected = "1.";
		assertEquals("check whether nothing is added", expected, TextBuddy.executeCommandByType("mytextfile.txt", "display", "display"));
	}
	
	@Test
	public void testDelete() {
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add This is the first line being added.");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add This is the second line being added.");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add This is the third line being added.");
		
		String expected = "deleted from mytextfile.txt: \"This is the second line being added.\"";
		assertEquals("delete middle line", expected, TextBuddy.executeCommandByType("mytextfile.txt", "delete", "delete 2"));
		expected = "1. This is the first line being added.\r\n2. This is the third line being added.";
		assertEquals("check whether the correct line has been deleted", expected, TextBuddy.executeCommandByType("mytextfile.txt", "display", "display"));
		
		expected = "deleted from mytextfile.txt: \"This is the first line being added.\"";
		assertEquals("delete first line", expected, TextBuddy.executeCommandByType("mytextfile.txt", "delete", "delete 1"));
		expected = "1. This is the third line being added.";
		assertEquals("check whether first line has been deleted", expected, TextBuddy.executeCommandByType("mytextfile.txt", "display", "display"));
		
		expected = "deleted from mytextfile.txt: \"\"";
		assertEquals("delete none-existant line", expected, TextBuddy.executeCommandByType("mytextfile.txt", "delete", "delete 45"));
		expected = "1. This is the third line being added.";
		assertEquals("check whether first line has been deleted", expected, TextBuddy.executeCommandByType("mytextfile.txt", "display", "display"));
	}
	
	@Test
	public void testClear() {
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add This is the first line being added.");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add This is the second line being added.");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add This is the third line being added.");
		
		String expected = "all content deleted from mytextfile.txt";
		assertEquals("clear the file", expected, TextBuddy.executeCommandByType("mytextfile.txt", "clear", "clear"));
		expected = "mytextfile.txt is empty.";
		assertEquals("check whether file is really empty", expected, TextBuddy.executeCommandByType("mytextfile.txt", "display", "display"));
	}
	
	@Test
	public void testSortCaps() {
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add A-line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add C-second line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add D-third line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add B-fourth line for testing");
		
		String expectedMsg = "File content has been sorted.";
		String expectedContent = "1. A-line for testing\r\n2. B-fourth line for testing\r\n3. C-second line for testing\r\n4. D-third line for testing";
		assertEquals("check whether sort function ran successfully", expectedMsg, TextBuddy.executeCommandByType("mytextfile.txt", "sort", "sort"));
		assertEquals("check whether it is sorted", expectedContent, TextBuddy.executeCommandByType("mytextfile.txt", "display", "display"));
		
	}
	
	@Test
	public void testSortWithoutCaps() {
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add a-line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add d-second line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add d-aaathird line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add g-fourth line for testing");
		
		String expectedMsg = "File content has been sorted.";
		String expectedContent = "1. a-line for testing\r\n2. d-aaathird line for testing\r\n3. d-second line for testing\r\n4. g-fourth line for testing";
		assertEquals("check whether sort function ran successfully", expectedMsg, TextBuddy.executeCommandByType("mytextfile.txt", "sort", "sort"));
		assertEquals("check whether it is sorted", expectedContent, TextBuddy.executeCommandByType("mytextfile.txt", "display", "display"));
		
	}
	
	@Test
	public void testSortWithMixedCaps() {
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add a-line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add d-second line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add D-aaathird line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add A-long fourth line for testing");
		
		String expectedMsg = "File content has been sorted.";
		String expectedContent = "1. a-line for testing\r\n2. A-long fourth line for testing\r\n3. D-aaathird line for testing\r\n4. d-second line for testing";
		assertEquals("check whether sort function ran successfully", expectedMsg, TextBuddy.executeCommandByType("mytextfile.txt", "sort", "sort"));
		assertEquals("check whether it is sorted", expectedContent, TextBuddy.executeCommandByType("mytextfile.txt", "display", "display"));
	
	}
	
	@Test
	public void testSearch() {
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add Once upon a time, there was a horse.");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add THE horse's name is mary.");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add Mary had a little lamb called HORSE.");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add One fine day, a wolf named john came to visit.");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add John ate both Mary and Horse.");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add THE END.");

		String expectedUpon = "1. Once upon a time, there was a horse.";
		assertEquals("search same case", expectedUpon, TextBuddy.executeCommandByType("mytextfile.txt", "search", "search upon"));
		
		String expectedThe = "1. THE horse's name is mary.\r\n2. THE END.";
		assertEquals("search different case", expectedThe, TextBuddy.executeCommandByType("mytextfile.txt", "search", "search the"));
		
		String expectedMary = "1. THE horse's name is mary.\r\n2. Mary had a little lamb called HORSE.\r\n3. John ate both Mary and Horse.";
		assertEquals("search mixed case", expectedMary, TextBuddy.executeCommandByType("mytextfile.txt", "search", "search MAry"));
		
		String expected = "No results found.";
		assertEquals("search with no results returned", expected, TextBuddy.executeCommandByType("mytextfile.txt", "search", "search Apple"));
	}
	

	
}
