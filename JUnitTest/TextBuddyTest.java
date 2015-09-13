import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TextBuddyTest {
	
	@Test
	public void testSortCaps() {
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add A-line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add C-second line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add D-third line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add B-fourth line for testing");
		
		String expectedMsg = "File content has been sorted.";
		String expectedContent = "1. A-line for testing\r\n2. B-fourth line for testing\r\n3. C-second line for testing\r\n4. D-third line for testing";
		assertEquals("check whether it is sorted", expectedMsg, TextBuddy.executeCommandByType("mytextfile.txt", "sort", "sort"));
		assertEquals("check whether it is sorted", expectedContent, TextBuddy.executeCommandByType("mytextfile.txt", "display", "display"));
		
		TextBuddy.executeCommandByType("mytextfile.txt", "clear", "clear");
	}
	
	@Test
	public void testSortWithoutCaps() {
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add a-line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add d-second line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add d-aaathird line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add g-fourth line for testing");
		
		String expectedMsg = "File content has been sorted.";
		String expectedContent = "1. a-line for testing\r\n2. d-aaathird line for testing\r\n3. d-second line for testing\r\n4. g-fourth line for testing";
		assertEquals("check whether it is sorted", expectedMsg, TextBuddy.executeCommandByType("mytextfile.txt", "sort", "sort"));
		assertEquals("check whether it is sorted", expectedContent, TextBuddy.executeCommandByType("mytextfile.txt", "display", "display"));
		
		TextBuddy.executeCommandByType("mytextfile.txt", "clear", "clear");
	}
	
	@Test
	public void testSortWithCapsAndNoCaps() {
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add a-line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add d-second line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add D-aaathird line for testing");
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add A-long fourth line for testing");
		
		String expectedMsg = "File content has been sorted.";
		String expectedContent = "1. a-line for testing\r\n2. A-long fourth line for testing\r\n3. D-aaathird line for testing\r\n4. d-second line for testing";
		assertEquals("check whether it is sorted", expectedMsg, TextBuddy.executeCommandByType("mytextfile.txt", "sort", "sort"));
		assertEquals("check whether it is sorted", expectedContent, TextBuddy.executeCommandByType("mytextfile.txt", "display", "display"));
	
		TextBuddy.executeCommandByType("mytextfile.txt", "clear", "clear");
	}
	
}
