/** Defines WordID class
 * @author Rimon Yacoub
 * CIS 22C: Final Project 
 */
public class WordId {

	private String word;
	private int id;
	
	/**
	 * Default constructor for WordId sets word to " " and id to 0
	 */
	public WordId() {
		word = " ";
		id = 0;
	}
	
	/**
	 * Two argument constructor for the WordId class. 
	 * 
	 * @param word  
	 * @param author 
	 */
	public WordId(String word, int id) {
		this.word = word;
		this.id = id;
	}

	/**
	 * returns the WordId's word
	 * 
	 * @return word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * returns the WordId's id
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Updates the WordId's word
	 * 
	 * @param setWord the WordId's word
	 */
	public void setWord(String word) {
		this.word = word;
	}
	
	/**
	 * Updates the WordId's id
	 * 
	 * @param setId the WordId's id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * returns the word and the id of the WordId
	 */
	@Override
	public String toString() {
		return word + id;
	}

	/**
	 * Overrides equals for Object using the formula given in class. 
	 * We will consider two WordIds to be equal on the basis of word
	 * 
	 * @return whether two WordId have the same word
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof WordId)) {
			return false;
		} else {
			WordId w = (WordId) obj;
			return word.equals(w.word);
		}
	}
	
	/**
	 * Overrides hashCode for the word using the formula given in class.
	 * by summing the Unicode values for each char and dividing 
	 * the summed Unicode values by table_size and storing remainder as the index
	 * @return sum
    **/
	@Override
	public int hashCode() {
		String key = word; // define key for this class
		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}

}
