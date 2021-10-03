package GEDCOM;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Author: Francis Borja
 * I pledge my honor that I have abided by the Stevens Honor System.
 */

public class GEDCOM {
	// Data fields
	public static HashMap<String, String> valid; // HashMap of all valid key/value pairs
	public static ArrayList<Individual> individuals = new ArrayList<Individual>();
	public static ArrayList<Family> families = new ArrayList<Family>();

	private class Line {
		public String level;
		public String tag;
		public boolean isValid;
		public String arguments;

		public Line(String level, String tag, boolean isValid, String arguments) {
			this.level = level;
			this.tag = tag;
			this.isValid = isValid;
			this.arguments = arguments;
		}

		public String toString() {
			String valid = this.isValid ? "Y" : "N";

			if (arguments.isEmpty()) {
				return "<-- " + level + "|" + tag + "|" + valid + "|";
			} else {
				return "<-- " + level + "|" + tag + "|" + valid + "|" + arguments;
			}
		}
	}

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
	 * @return true if it is a valid pair, false if it is not a valid pair.
	 */
	private boolean checkValid(String level, String tag) {
		return (valid.get(tag) != null && valid.get(tag).equals(level)) ? true : false;
	}

	/**
	 * Parses a line into a 
	 * @param line The line to parse
	 */
	private Line parseLine(String line) {
		String[] array = line.split(" ");
		String level = array[0], tag, arguments = "";

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
		boolean isValid = checkValid(level, tag);

		return new Line(level, tag, isValid, arguments);
	}

	private void getIndividuals(ArrayList<String> lines) {
		for (String line : lines) {
			Line parsed_line = parseLine(line);
		}
	}

	public static boolean checkUniqueIds() {
		ArrayList<String> individual_ids = new ArrayList<String>();
		for (Individual i : individuals) {
			individual_ids.add(i.getId());
		}

		ArrayList<String> family_ids = new ArrayList<String>();
		for (Family f : families) {
			family_ids.add(f.getId());
		}

		// Put ids into hash sets and compare sizes
		HashSet<String> individual_set = new HashSet<String>(individual_ids);
		HashSet<String> family_set = new HashSet<String>(family_ids);

		if (individual_set.size() < individual_ids.size() || family_set.size() < family_ids.size()) {
			return false;
		}
		return true;
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

		Line current;
		// Parse each line
		for (String line : lines) {
			System.out.println("--> " + line);
			current = parser.parseLine(line);
			System.out.println(current);
		}
    }
}
