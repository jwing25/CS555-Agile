package GEDCOM;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author: Francis Borja
 * I pledge my honor that I have abided by the Stevens Honor System.
 */

public class GEDCOM {
	// Data fields
	public static HashMap<String, String> valid; // HashMap of all valid key/value pairs
	public static ArrayList<Family> individuals;
	public static ArrayList<Individual> families;

	// Constructor: fills out HashMap with valid tags and corresponding levels
	public GEDCOM() {
		valid = new HashMap<String, String>();
		valid.put("INDI", "0");
		valid.put("NAME", "1");
		valid.put("SEX", "1");
		valid.put("BIRT", "1");
		valid.put("DEAT", "1");
		valid.put("FAMC", "1");
		valid.put("FAMS", "1");
		valid.put("FAM", "0");
		valid.put("MARR", "1");
		valid.put("HUSB", "1");
		valid.put("WIFE", "1");
		valid.put("CHIL", "1");
		valid.put("DIV", "1");
		valid.put("DATE", "2");
		valid.put("HEAD", "0");
		valid.put("TRLR", "0");
		valid.put("NOTE", "0");
	}
	
	// Methods

	/**
	 * Reads specified file and saves each line into an array of strings
	 * @param file
	 * @return An ArrayList of each line as a String
	 */
	private ArrayList<String> readFile(File file) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			Scanner input = new Scanner(file);
			
			while (input.hasNextLine()) {
				lines.add(input.nextLine());
			}
			
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}

	/**
	 * Checks whether or not each level/tag pair is valid
	 * @param level The level to check (value)
	 * @param tag The tag to check (key)
	 * @return "Y" if it is a valid pair, "N" if it is not a valid pair.
	 */
	private String checkValid(String level, String tag) {
		return (valid.get(tag) != null && valid.get(tag).equals(level)) ? "Y" : "N";
	}

	/**
	 * Parses a line to give the specified output
	 * @param line The line to parse
	 */
	private void parseLine(String line) {
		String[] array = line.split(" ");
		String level = array[0], tag, valid, arguments = "";

		// Special format for INDI and FAM tags
		if (line.contains("INDI") || line.contains("FAM")) {
			tag = array[2];
			arguments += array[1];
			if (array.length > 3) {
				for (int i = 3; i < array.length; i++) {
					System.out.println(array[i]);
					arguments += array[i] + " ";
				}
			}
		} else {
			tag = array[1];
			if (array.length > 2) {
				for (int i = 2; i < array.length; i++) {
					arguments += array[i] + " ";
				}
			}
		}
		valid = checkValid(level, tag);

		if (arguments.isEmpty()) {
			System.out.println("<-- " + level + "|" + tag + "|" + valid + "|");
		} else {
			System.out.println("<-- " + level + "|" + tag + "|" + valid + "|" + arguments);
		}
	}

    public static void main(String[] args) {
		GEDCOM parser = new GEDCOM();

		// Ask for user input
		Scanner input = new Scanner(System.in);
		File file;
		String s;
		while (true) {
			System.out.println("Enter file name:");
			s = input.nextLine();
			file = new File(s);
			if (file.exists() && !file.isDirectory()) {
				break;
			}
		}
		input.close();

		// Read file
		ArrayList<String> lines = parser.readFile(file);

		// Parse each line
		for (String line : lines) {
			System.out.println("--> " + line);
			parser.parseLine(line);
		}
    }
}
