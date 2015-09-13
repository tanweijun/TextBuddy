import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TextBuddyTest {
	
	@Before
	public void setUp() {
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add a-line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add c-third line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add d-fourth line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add b-second line for testing");
	}
	
	@Test
	public void testSort() {
		String expectedMsg = "File content has been sorted.";
		String expectedContent = "1. a-line for testing\r\n2. b-second line for testing\r\n3. c-third line for testing\r\n4. d-fourth line for testing";
		assertEquals("check whether it is sorted", expectedMsg, TextBuddy.executeCommandByType("mytextfile.txt", "sort", "sort"));
		assertEquals("check whether it is sorted", expectedContent, TextBuddy.executeCommandByType("mytextfile.txt", "display", "display"));
	}
	
}
