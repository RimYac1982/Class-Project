/** Defines Haiku class
* @author Rimon Yacoub
* CIS 22C: Final Project 
*/
public class Haiku implements Comparable <Haiku> {
	private String title;
	private String author;
	private int birthYear;
	private String poem;
	
	// Constructors
	
	/**
	 * Default constructor for Haiku class 
	 */
	
	
public Haiku() {
	title = "";
	author = "";
	birthYear = 0;
	poem = "";
}


/**
 * Four parameter constructor for Haiku class 
 * 
 * @param title the title of the poem
 * @param author  the author of the poem
 * @param birthYear the date of the author's birth
 * @param poem The lines of verse in the haiku
 * 
 */
public Haiku (String title, String author, int birthYear, String poem) {
	this.title = title;
	this.author = author;
	this.birthYear = birthYear;
	this.poem = poem;
}

// Accessors

/**
 * returns the title of the haiku
 * 
 * @return the title of the haiku
 */
public String getTitle() {
	return title;
}

/**
 * returns the author of the haiku
 * 
 * @return the author of the haiku
 */
public String getAuthor() {
	return author;
}

/**
 * returns the birth year of the author of the haiku
 * 
 * @return the birth year of the author of the haiku
 */
public int getBirthYear() {
	return birthYear;
}

/**
 * returns three lines of the haiku
 * 
 * @return three lines of the haiku
 */
public String getPoem() {
	return poem;
}

// mutators

/**
 * sets the title of the haiku
 * 
 * @param title the title of the haiku
 */
public void setTitle(String title) {
	this.title = title;
}

/**
 * sets the author of the haiku
 * 
 * @param author the author of the haiku
 */
public void setAuthor(String author) {
	this.author = author;
}

/**
 * sets the year of the poet's birth
 * 
 * @param birthYear the year of the poet's birth
 */
public void setbirthYear(int birthYear) {
	this.birthYear = birthYear;
}

/**
 * sets the lines in the haiku
 * 
 * @param poem the lines in the haiku
 */
public void setPoem(String poem) {
	this.poem = poem;
}

// additional methods


/**
 * Returns a String containing the title and the lines of the poem
 * 
 * @return a String of the title and lines of the poem
 */
@Override public String toString() {
	return "     " + title + "\n" + poem + "\n  by " + author + " (" + birthYear + ")\n";
}

/**
 * Determines whether the given Object is another Haiku, containing the
 * same data in the same order
 * 
 * @param obj another Object
 * @return whether there is equality
 */
@Override public boolean equals(Object obj) {
	if (this == obj) {
		return true;
	} else if (! (obj instanceof Haiku)) {
		return false;
	}  else {
		Haiku h = (Haiku)obj;
		return this.getTitle().equals(h.getTitle()) && this.getAuthor().equals(h.getAuthor());
	}
}

/**
 * Determines whether the given haiku compares to another Haiku, 
 * 
 * @param hai another Haiku
 * @return whether the two Haiku are the same or different based on memory location
 * and variables
 */
@Override public int compareTo(Haiku hai) {
	if (this.equals(hai)) {
		return 0;
	} else if (this.getTitle().equals(hai.getTitle())) {
		return this.getTitle().compareTo(hai.getTitle());
	} else {
		return this.getAuthor().compareTo(hai.getAuthor());
		}
	}

/**
 * Calculates an integer value based on the character values 
 * of the title and author of the haiku 
 *
 * @return the integer value of the sum of the numeric representations of the 
 * letters in the author and title
 * 
 */
@Override public int hashCode() {
    String key = title + author; 
    int sum = 0;
    for (int i = 0; i < key.length(); i++) {
        sum += (int) key.charAt(i);
    }
    return sum;
}
}