package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import application.Database;

class DatabaseTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Database.deleteTable("words");
		Database.createWordsTable("words");
		Database.post("The", 5);
		System.out.println("Created a words table and posted 'The', 5.");
	}

	@BeforeEach
	void setUp() throws Exception {
		Database.update("The", 5);
		System.out.println("Frequency of 'The' updated to 5");
	}
	
	@DisplayName("Frequency should be 5")
	@Test
	void shouldQueryFrequencyAndPass() {
		int frequency = Database.queryFrequency("The");
		// expected, actual
		assertEquals(5, frequency);
	}
	
	@DisplayName("Frequency should be 7, not 6 ")
	@Test
	void shouldQueryFrequencyAndFail() {
		int frequency = Database.queryFrequency("The");
		// expected, actual
		assertNotEquals(6, frequency);
	}
	
	@DisplayName("Frequency should update from 5 to 7")
	@Test
	void shouldUpdateFrequencyCheckUpdateAndPass() {
		assertEquals(5, Database.queryFrequency("The"));
		Database.update("The", 7);
		int frequency = Database.queryFrequency("The");
		// expected, actual
		assertNotEquals(5, Database.queryFrequency("The"));
		assertEquals(7, frequency);
	}
	
	@DisplayName("Frequency should update from 5 to 7")
	@Test
	void updateFrequency_testFail() {
		Database.update("The", 7);
		int frequency = Database.queryFrequency("The");
		// expected, actual
		assertNotEquals(5, frequency);
	}
	
	@Nested
	class RepeatedTests {
		@DisplayName("Search for words not in table")
		@RepeatedTest(value = 5, name = "Repeating Database.query(word) Test {currentRepetition} of {totalRepetitions}")
		void shouldQueryForWordNotInTable() {
			int frequency = Database.queryFrequency("burrito");
			assertEquals(-1, frequency);
		}
	}
	
	@Nested
	class ParameterizedTests {
		@DisplayName("Search for word not present in database")
		@ParameterizedTest
		@ValueSource(strings = {"rice", "cake", "wife", "lake", "twice", "baked"})
		public void shouldSearchForWordNotPresentInDatabase(String word) {
			int frequency = Database.queryFrequency(word);
			assertEquals(-1, frequency);
		}
	
		@DisplayName("CSV Source Case - Word should be posted to database, found and then deleted")
		@ParameterizedTest
		@CsvSource({"rice", "cake", "wife", "lake", "twice", "baked"})
		public void shouldTestWordFormatUsingCSVSource(String word) {
			try {
				Database.post(word, 1);
				assertEquals(1, Database.queryFrequency(word));
				Database.delete(word);
				assertEquals(-1, Database.queryFrequency(word));
			} catch (Exception e) {
				System.out.println("Exception in CSV Source Case: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	@DisplayName("Method Source Case - Search for word not in database")
	@ParameterizedTest
	@MethodSource("wordList")
	public void shouldSearchForWordNotPresentInDatabasUsingMethodSource(String word) {
		int frequency = Database.queryFrequency(word);
		assertEquals(-1, frequency);
	}
		
	private static List<String> wordList() {
		return Arrays.asList("rice", "cake", "wife", "lake", "twice", "baked");
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
