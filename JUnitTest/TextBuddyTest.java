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
	
	@Before
	public void setUp() {
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add a-line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add c-third line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add d-fourth line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add b-second line for testing");
		//assertEquals("added to mytextfile.txt: \"line for testing\"\r\n", printedContent.toString());
	}
	
	@Test
	public void testSort() {
		String expected = "1. a-line for testing\r\n2. b-second line for testing\r\n3. c-third line for testing\r\n4. d-fourth line for testing\r\n";
		TextBuddy.executeCommandByType("mytextfile.txt", "sort", "sort");
		TextBuddy.executeCommandByType("mytextfile.txt", "display", "display");
		String actual = printedContent.toString();
		assertEquals("check whether it is sorted", expected, actual);
	}
	
	
	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	}
}
