
/** Defines SearchEngine class
 * @author Rimon Yacoub
 * CIS 22C: Final Project 
 */
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class SearchEngine {
	private HashTable<WordId> wordIdTable; // word mapped to index
	private ArrayList<BST<Haiku>> wordList; // index mapped to haiku containing word
	private String[] stopWords = { "a", "the", "an", "and", "but", "yet", "away", "for", "nor", "or", "of", "as", "by",
			"on", "in", "into", "after", "against", "to", "about", "above", "up", "from", "during", "then", "its",
			"though", "like", "within", "out", "this", "my", "as", "if", "through", "get", "i" };
	private static int count;

	// Constructor

	/**
	 * Default constructor for SearchEngine class
	 */
	public SearchEngine() {
		wordIdTable = new HashTable<>(300);
		wordList = new ArrayList<>();
		count = 0;
	}

	// Additional Methods
// For testing and running the SearchEngine
	
	public ArrayList<Haiku> readFile(Scanner input) {
		Haiku temp = new Haiku();
		String title, author, first, second, third, poem;
		int birthYear;
		ArrayList<Haiku> records = new ArrayList<>();
		while (input.hasNextLine()) {
			title = input.nextLine();
			author = input.nextLine();
			birthYear = input.nextInt();
			input.nextLine();
			first = input.nextLine();
			second = input.nextLine();
			third = input.nextLine();
			if (input.hasNextLine()) {
				input.nextLine();
			}
			poem = first + "\n" + second + "\n" + third;
			temp = new Haiku(title, author, birthYear, poem);
			records.add(temp);
		}
		return records;
	}

	/**
	 * Searches for the haiku containing a key word and returns a list of the haiku
	 * containing that word
	 * 
	 * @param word the String key word
	 * 
	 * @return returns an ArrayList of haiku
	 */
	public ArrayList<Haiku> searchWord(String word) {
		word = word.toLowerCase();
		word = removePunctuation(word);
		int id = 0;
		WordId keyWord = new WordId(word, id);
		WordId wordIdReturned = wordIdTable.findObj(keyWord);
		if (wordIdReturned != null) {
			ArrayList<Haiku> list = new ArrayList<>();
			int index = wordIdReturned.getId();
			wordList.get(index).inOrderList(list);
			return list;
		}
		return new ArrayList<>();
	}

	/**
	 * Determines whether the given word is a stopWord
	 * 
	 * @param word the word to identify as a stopWord
	 * @return returns whether the word is or is not a stopWord
	 */
	public boolean isStopWord(String word) {
		for (int i = 0; i < stopWords.length; i++) {
			if (word.equals(stopWords[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Maps a key word to an index and creates a WordId object with the two values
	 * 
	 * @param input the Scanner that accepts the input
	 * 
	 */
	public void index(Haiku poem) {
		Scanner input = new Scanner(poem.getPoem()); 
		while (input.hasNext()) {
			String word = input.next().toLowerCase();
			word = removePunctuation(word);
			if (!isStopWord(word) && !word.equals("")) {
				WordId temp = wordIdTable.findObj(new WordId(word, 0));//-1, find() returns int
				if (temp == null) {
					temp = new WordId(word, count);
					wordIdTable.add(temp);
					BST<Haiku> bst = new BST<>();
					bst.insert(poem);
					wordList.add(count, bst);
					count++;
				} else {
					int wordIndex = temp.getId();
					BST<Haiku> bst = wordList.get(wordIndex);
					if (!bst.search(poem)) {
						bst.insert(poem);
					}
				}
			}
		}
		input.close();
	}

	/**
	 * Removes punctuation after a word and returns word without punctuation
	 * 
	 * @param word the String containing the word from the poem
	 * 
	 * @return returns the word without punctuation after it
	 */
	public String removePunctuation(String word) {
		String cleanWord = "";
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != '.' && word.charAt(i) != '-' && word.charAt(i) != ',') {
				cleanWord += word.charAt(i);
			}
		}
		return cleanWord;
	}

	/**
	 * Deletes a poem from the array list of BSTs
	 * 
	 * @param Haiku the String word to be added
	 * 
	 */
	//public void delete(Haiku poem) {

	//}
	
		// testing to show how to run methods
	public static void main(String[] args) throws IOException {    
		File in = new File("Haiku.txt");
		Scanner input = new Scanner(in);
		SearchEngine searchEngine = new SearchEngine();
		ArrayList<Haiku> records = searchEngine.readFile(input);
		for (int i = 0; i < records.size(); i++) {
			Haiku poem = records.get(i);
			searchEngine.index(poem);
		}
		String keyWord = "skylarks";
		ArrayList<Haiku> thereThere = searchEngine.searchWord(keyWord);
		for(int i = 0; i < thereThere.size(); i++) {
			System.out.println(thereThere.get(i));
		}
		System.out.println(searchEngine.isStopWord("and"));

	}
	
}
