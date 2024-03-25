
/**
 * Defines HaikuApp class
 * @author Rimon Yacoub
 * CIS 22C,Final Project 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HaikuApp {

	private static HashTable<Haiku> haikuTable = new HashTable<Haiku>(300);
	private static SearchEngine se = new SearchEngine();

	public static void main(String[] arg) {

		String inputFileName = "Haiku.txt";
		String choice;
		String outputFileName;

		// initHaikuApp
		haikuTable = readFromFile(inputFileName);
		ArrayList<Haiku> records = haikuTable.listAllElements();
		for (int i = 0; i < records.size(); i++) {
			Haiku poem = records.get(i);
			se.index(poem);
		}

		Scanner input = new Scanner(System.in);

		System.out.println("Welcome to Haiku Poem Collection!\n");

		do {
			printMenu();
			choice = input.nextLine().toLowerCase();
			if (choice.equals("v")) {
				view();
			} else if (choice.equals("u")) {
				upload(input);
			} else if (choice.equals("d")) {
				delete(input);
			} else if (choice.equals("s")) {
				System.out.println("\nSelect one of the following:\n" + "1. Title + Author\n" + "2. KeyWord");
				System.out.print("\nEnter your choice: ");
				String searchChoice = input.nextLine();
				if ("1".equals(searchChoice)) {
					searchHaiku(input);
				} else if ("2".equals(searchChoice)) {
					searchKeyword(input);
				} else {
					System.out.println("Invalid input");
				}
				System.out.println("\nReturning to main menu.\n");
			} else if (choice.equals("m")) {
				modify(input);
			} else if (choice.equals("t")) {
				displayStatistics(input);
			} else if (choice.equals("x")) {
				System.out.print("\nPlease enter a file name to store the collection: ");
				outputFileName = input.nextLine();
				writeToFile(outputFileName, haikuTable);
				System.out.println("\nThe Haiku Poem Collection is stored in \"" + outputFileName + "\"");
				break;
			} else {
				System.out.println("\nInvalid menu option.\n");
			}

		} while (!choice.equals("x"));

		System.out.println("\nGoodbye!");
		input.close();
	}

	/**
	 * Prints the menu to the console
	 */
	public static void printMenu() {
		System.out.print("Please select from one of the options:\n\n" + "V. View Haiku Poems\n"
				+ "U. Upload a new Haiku\n" + "D. Delete a Haiku Poem\n" + "S. Search a Haiku Poem\n"
				+ "M. Modify a Haiku Poem\n" + "T. Statistics\n" + "X. Exit\n\n" + "Enter your choice: ");
	}

	/**
	 * Prints out all current Haikus
	 */
	public static void view() {
		System.out.println("\nThese are Haiku poems in our collection!\n");
		ArrayList<Haiku> haikuList = haikuTable.listAllElements();
		for (int i = 0; i < haikuList.size(); i++) {
			System.out.println((i + 1) + haikuList.get(i).toString());
		}

		System.out.println("\nReturning to main menu.\n");
	}

	/**
	 * uploads a new poem from a file
	 * 
	 * @param s the Scanner that accepts the user input
	 */
	public static void upload(Scanner s) {
		System.out.println("\nUploading haiku from a file.");
		System.out.print("Enter the file name: ");
		String filepath = s.next();
		s.nextLine();
		HashTable<Haiku> temp = readFromFile(filepath);
		ArrayList<Haiku> records = temp.listAllElements();
		if (records.isEmpty()) {
			System.out.println("\n" + filepath + " has no entries. :(");
		} else {
			for (int i = 0; i < records.size(); i++) {
				Haiku poem = records.get(i);
				System.out.println("\n" + poem.getTitle() + " has been added.");
				se.index(poem);
				haikuTable.add(poem);
			}
		}

		System.out.println("\nReturning to main menu.\n");
	}

	/**
	 * deletes a record from haikuTable
	 * 
	 * @param s the Scanner that accepts the user input
	 */
	public static void delete(Scanner s) {
		System.out.println("\n** Deleting a Record **");
		ArrayList<Haiku> temp = haikuTable.listAllElements();
		for (int i = 0; i < temp.size(); i++) {
			System.out.println(i + 1 + ": " + temp.get(i).getTitle());
		}
		System.out.print("\nPlease select the number of the haiku you wish to delete: ");
		if (s.hasNextInt()) {
			int choice = s.nextInt();
			if (choice < 1 || choice > temp.size()) {
				System.out.println("You chose an invalid number. Try again.");
				s.nextLine();
				return;
			}
			s.nextLine();
			Haiku poem = temp.remove(choice - 1);
			System.out.println("\n" + poem.getTitle() + " has been removed.\n");
			haikuTable.delete(poem);
			se = new SearchEngine();
			for (int i = 0; i < temp.size(); i++) {
				se.index(temp.get(i));
			}
		} else {
			System.out.println("Invalid input\n");
			s.nextLine();
		}
	}

	/**
	 * Displays statistics according to the user's choice
	 * 
	 * @param s the Scanner that accepts the user's input
	 */
	public static void displayStatistics(Scanner s) {
		int numElem = haikuTable.getNumElements();

		System.out.println("\nSelect one of the following:\n" + "1. Number of records\n" + "2. Average word count\n"
				+ "3. Oldest and Newest Haiku");
		System.out.print("\nEnter your choice: ");
		String statisticsChoice = s.nextLine();
		if ("1".equals(statisticsChoice)) {
			System.out.println("Statistic #1, Number of Records: " + numElem);

		} else if ("2".equals(statisticsChoice)) {
			int wordCount = 0;
			boolean flag = false;
			ArrayList<Haiku> haikuList = haikuTable.listAllElements();
			for (int i = 0; i < haikuList.size(); i++) {
				String str = haikuList.get(i).getPoem();
				for (int j = 0; j < str.length(); j++) {
					if (Character.isLetter(str.charAt(j))) {
						flag = true;
					} else if (!Character.isLetter(str.charAt(j)) && flag) {
						wordCount++;
						flag = false;
					}
				}
			}
			System.out.println("Statistic #2, Average Word Count of Haiku: " + (Math.round(1.0 * wordCount / numElem)));

		} else if ("3".equals(statisticsChoice)) {
			ArrayList<Haiku> haikuList = haikuTable.listAllElements();
			int latestIndex = 0;
			int oldestIndex = 0;
			for (int i = 0; i < haikuList.size(); i++) {
				int num = haikuList.get(i).getBirthYear();
				if (num > haikuList.get(latestIndex).getBirthYear()) {
					latestIndex = i;
				}
				if (num < haikuList.get(oldestIndex).getBirthYear()) {
					oldestIndex = i;
				}
			}
			System.out.println("Statistic #3: ");
			System.out.println("The oldest Haiku in the record is: " + haikuList.get(oldestIndex).getTitle() + ", By "
					+ haikuList.get(oldestIndex).getAuthor() + " (" + haikuList.get(oldestIndex).getBirthYear() + ")");
			System.out.println("The newest Haiku in the record is: " + haikuList.get(latestIndex).getTitle() + ", By "
					+ haikuList.get(latestIndex).getAuthor() + " (" + haikuList.get(latestIndex).getBirthYear() + ")");
		} else {
			System.out.println("Invalid input");
		}

		System.out.println("\nReturning to main menu.\n");
	}

	/**
	 * Searches for a Haiku given the title and author
	 * 
	 * @param input the Scanner that accepts the user's input
	 */
	public static void searchHaiku(Scanner input) {
		String title, author;

		System.out.print("\nPlease enter the Title: ");
		title = input.nextLine();
		System.out.print("Please enter the Author: ");
		author = input.nextLine();

		Haiku hk = new Haiku(title, author, 0, "");

		if (author.isEmpty() || title.isEmpty()) {
			System.out.println("Please enter both a title and author.");
		} else if (haikuTable.find(hk) == -1) {
			System.out.println("\n" + author + "'s " + title + " is not in the collection.\n");
		} else {
			System.out.println("\nThe " + author + " and " + title + " is:");
			System.out.println(haikuTable.findObj(hk).toString()); // one method in HashTable to return an exist Haiku
																	// in detail
		}
	}

	/**
	 * searches for poems given a keyword
	 * 
	 * @param s the Scanner that accepts the user input
	 */
	public static void searchKeyword(Scanner s) {
		System.out.print("Enter the keyword: ");
		String keyword = s.next();
		s.nextLine();
		ArrayList<Haiku> records = se.searchWord(keyword);
		if (records.isEmpty()) {
			System.out.println(keyword + " has no entries.");
		} else {
			System.out.println("\nThe following haiku contain the keyword " + keyword + ":");
			for (int i = 0; i < records.size(); i++) {
				System.out.println((i + 1) + ". " + records.get(i).getTitle());
			}
			System.out.print("\nSelect the haiku number to print the poem or " + "-1 to return to main menu: ");
			if (s.hasNextInt()) {
				int num = s.nextInt();
				num--;
				s.nextLine(); // clear newline
				if (num >= 0 && num < records.size()) {
					System.out.println("\nHere is the poem:\n");
					System.out.print(records.get(num).toString());
				} else if (num == -2) {
					return;
				} else {
					System.out.println("\nInvalid input");
				}
			} else {
				System.out.println("\nInvalid input");
				s.nextLine();
			}
		}

	}

	/**
	 * Modifies the birth year of a Haiku
	 * 
	 * @param input the Scanner that accepts the user's input
	 */
	public static void modify(Scanner input) {

		String title, author;
		int year;

		System.out.print("\nPlease enter the Title:");
		title = input.nextLine();
		System.out.print("Please enter the Author:");
		author = input.nextLine();

		Haiku hk = new Haiku(title, author, 0, "");

		if (haikuTable.find(hk) == -1) {
			System.out.println("\n" + author + "'s " + title + " is not in the collection.");
		} else {
			System.out.println("\nThe information for " + author + "'s " + title + " is:");
			hk = haikuTable.findObj(hk);
			System.out.println(hk.toString());

			System.out.println("Do you want to modify the birth year? ?(y/n)");
			String modify = input.nextLine().toLowerCase();
			if (modify.equals("y")) {
				System.out.print("Please enter the Year:");
				year = input.nextInt();
				input.nextLine();

				hk.setbirthYear(year);

				System.out.println(
						"\nModifying...\n\n" + "The updated information for " + author + "'s " + title + " is:");

				System.out.println(hk.toString());
			}
		}
		System.out.println("\nReturning to main menu.\n");
	}

	/**
	 * Reads from a given file that contains current Haikus
	 * 
	 * @param fileName the name of the file to read from
	 * @return a HashTable that contains all current Haikus
	 */
	public static HashTable<Haiku> readFromFile(String fileName) {
		File file = new File(fileName);
		Scanner scan = null;
		HashTable<Haiku> tempTable = new HashTable<Haiku>(300);
		try {
			scan = new Scanner(file);
			while (scan.hasNextLine()) {
				String title = scan.nextLine();
				String author = scan.nextLine();
				int year = Integer.valueOf(scan.nextLine());
				String lineOne = scan.nextLine();
				String lineTwo = scan.nextLine();
				String lineThree = scan.nextLine();
				if (scan.hasNextLine()) {
					scan.nextLine();
				}
				Haiku haiku = new Haiku();
				haiku.setAuthor(author);
				haiku.setbirthYear(year);
				haiku.setTitle(title);
				String poem = lineOne + "\n" + lineTwo + "\n" + lineThree + "\n";
				haiku.setPoem(poem);
				tempTable.add(haiku);
			}
			scan.close();
			return tempTable;
		} catch (FileNotFoundException e) {
			System.out.println("Invalid File Input Name");
			tempTable = new HashTable<Haiku>(300);
			return tempTable;
		}
	}

	/**
	 * Writes to a given file to store current Haikus in a given HashTable
	 * 
	 * @param filename the name of the file to write to
	 * @param hTable   the HashTable that contains current Haikus that need to be
	 *                 written to a file
	 */
	public static void writeToFile(String filename, HashTable<Haiku> hTable) {
		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(hTable.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
