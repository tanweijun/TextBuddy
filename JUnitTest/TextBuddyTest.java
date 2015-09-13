import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextBuddyTest {
	
	// to get output displayed using System.out.println()
	private final ByteArrayOutputStream printedContent = new ByteArrayOutputStream();
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(printedContent));
	}
	
	public void setUp() {
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add second line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add third line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add fourth line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add fifth line for testing");
		//assertEquals("added to mytextfile.txt: \"line for testing\"\r\n", printedContent.toString());
	}
	
	@Test
	public void testSort() {
		TextBuddy.executeCommandByType("mytextfile.txt", "display", "display");
		String unsorted = printedContent.toString();
		TextBuddy.executeCommandByType("mytextfile.txt", "sort", "sort");
		String sorted = printedContent.toString();
		assertEquals(unsorted, sorted);
	}
	
	
	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	}
}
