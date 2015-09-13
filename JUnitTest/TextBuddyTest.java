import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextBuddyTest {
	
	private final ByteArrayOutputStream printedContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(printedContent));
	}
	
	public void setUp() {

	}
	
	@Test
	public void test() {
		TextBuddy.executeCommandByType("mytextfile.txt", "add", "add line for testing");
		assertEquals("added to mytextfile.txt: \"line for testing\"\r\n", printedContent.toString());
	}
	
	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	}
}
