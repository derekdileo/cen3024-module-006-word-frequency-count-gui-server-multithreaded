package testing;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.Word;

class WordTest {

	private static Word wordTest;
	//private static Word wordTest2;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("setUpBeforeClass()... (runs once)");
		wordTest = new Word("testing", 5);
		//wordTest2 = new Word("testing", 5);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("tearDownAfterClass()...(runs once at end)");
	}

	@BeforeEach
	void setUp() throws Exception {
		System.out.println("setUp()... (runs before each test)");
	}

	@AfterEach
	void tearDown() throws Exception {
		System.out.println("tearDown()...(runs after each test)");
	}

	@Test
	void toString_Test() {
		int index = 0;
		String word = wordTest.getKey();
		int frequency = wordTest.getValue();
		
		String strToString = wordTest.toString(index);
		String strCode = "\n" + (index + 1) + ") Word: " + word + "\t\tFrequency: " + frequency;
		
		System.out.println(strToString);
		System.out.println(strCode);
		
		assertEquals(strToString, strCode);
	}
//
//	@Test
//	void compareTo_Test(Word wordTest2) {
//		int result = wordTest.compareTo(wordTest2);		
//		System.out.println("Result: " + result);
//		assertEquals(0, result, 0);
//		//fail("Not yet implemented");
//	}
	
	// An example with a fail and expected Exception
//	@Test(expected IllegalArgumentException.class)
//	void test() {
//		fail("Not yet implemented");
//	}
	
}
