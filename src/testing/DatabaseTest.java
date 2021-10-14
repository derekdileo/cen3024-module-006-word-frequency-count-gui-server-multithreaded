package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.Database;

class DatabaseTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Database.deleteTable("words");
		Database.createWordsTable("words");
		Database.post("The", 5);
	}


	@BeforeEach
	void setUp() throws Exception {
	}


	@Test
	void queryFrequency_testPass() {
		int frequency = Database.queryFrequency("The");
		// expected, actual
		assertEquals(5, frequency);
	}
	@Test
	void queryFrequency_testFail() {
		int frequency = Database.queryFrequency("The");
		// expected, actual
		assertNotEquals(6, frequency);
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Database.deleteTable("words");
		Database.createWordsTable("words");
	}
}
