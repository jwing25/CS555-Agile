package GEDCOM;

import java.io.*;
import java.time.Instant;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * Author: Francis Borja
 * I pledge my honor that I have abided by the Stevens Honor System.
 */

public class GEDCOM {
	// Data fields
	public static HashMap<String, String> valid; // HashMap of all valid key/value pairs
	public static ArrayList<Individual> individuals = new ArrayList<Individual>();
	public static ArrayList<Family> families = new ArrayList<Family>();

	public static class Line {
		public String level;
		public String tag;
		public boolean isValid;
		public String arguments;

		public Line(String level, String tag, boolean isValid, String arguments) {
			this.level = level;
			this.tag = tag;
			this.isValid = isValid;
			this.arguments = arguments.trim();
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
	public static ArrayList<String> readFile(File file) {
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
	private static boolean checkValid(String level, String tag) {
		return (valid.get(tag) != null && valid.get(tag).equals(level)) ? true : false;
	}

	/**
	 * Parses a line into a 
	 * @param line The line to parse
	 */
	public Line parseLine(String line) {
		if (line == null || line == "") {
			throw new IllegalArgumentException("parseLine: line is null or empty");
		}
		
		String[] array = line.split(" ");
		String level = array[0], tag, arguments = "";

		// Special format for INDI and FAM tags
		if (array.length > 2 && (array[2].compareTo("INDI") == 0 || array[2].compareTo("FAM") == 0)) {
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
		
		Line parsed_line = new Line(level, tag, isValid, arguments);
		return parsed_line;
	}

	private void getIndividuals(ArrayList<String> lines) {
		Line current;
		Individual indi = null;
		Date currentdate = new Date();
		for (int i = 0; i < lines.size(); i++) {
			current = parseLine(lines.get(i));

			// If line is invalid, skip
			if (!current.isValid) {
				continue;
			}

			if (indi == null) {
				if (current.tag.compareTo("INDI") == 0) {
					indi = new Individual(current.arguments);
				}
			} else {
				if (current.level.compareTo("0") == 0) { // End of individual
					// Set alive status
					if (indi.getDeath() == null) {
						indi.setAlive(true);
					} else {
						indi.setAlive(false);
					}
					
					// Set age
					try {
						SimpleDateFormat f = new SimpleDateFormat("yyyMMdd");
						int date1 = Integer.parseInt(f.format(currentdate));
						int date2 = Integer.parseInt(f.format(indi.getBirthday()));
						indi.setAge((date1 - date2) / 10000);
					} catch (Exception e) {
						// do nothing
					}

					// Add individual to list
					GEDCOM.individuals.add(indi);

					// Create new individual
					if (current.tag.compareTo("INDI") == 0) {
						indi = new Individual(current.arguments);
					} else {
						indi = null;
					}
					continue;
				}
				switch (current.tag) {
					case "NAME":
						indi.setName(current.arguments);
						break;
					case "SEX":
						indi.setGender(current.arguments);
						break;
					case "BIRT":
						Line birthline = parseLine(lines.get(i+1));
						try {
							Date birthday = new SimpleDateFormat("dd/MMM/yyyy").parse(birthline.arguments.replaceAll(" ", "/"));
							indi.setBirthday(birthday);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "DEAT":
						indi.setAlive(false);
						Line deathline = parseLine(lines.get(i+1));
						if (deathline.tag.compareTo("DATE") == 0) {
							try {
								Date deathdate = new SimpleDateFormat("dd/MMM/yyyy").parse(deathline.arguments.replaceAll(" ", "/"));
								indi.setDeath(deathdate);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						break;
					case "FAMC":
						indi.addChildOf(current.arguments);
						break;
					case "FAMS":
						indi.addSpouse(current.arguments);
						break;
					default: 
						break;
				}
			}
		}
		if (indi != null) {
			individuals.add(indi);
		}
	}

	private void getFamilies(ArrayList<String> lines) {
		Line current;
		Family fam = null;
		for (int i = 0; i < lines.size(); i++) {
			current = parseLine(lines.get(i));
			// If line is invalid, skip
			if (!current.isValid) {
				continue;
			}
			if (fam == null) {
				if (current.tag.compareTo("FAM") == 0) {
					fam = new Family(current.arguments);
				}
			} else {
				if (current.level.compareTo("0") == 0) { // End of family
					// Add family to list
					GEDCOM.families.add(fam);

					// Create new family
					if (current.tag.compareTo("FAM") == 0) {
						fam = new Family(current.arguments);
					} else {
						fam = null;
					}
					continue;
				}
				switch (current.tag) {
					case "MARR":
						Line marriageline = parseLine(lines.get(i+1));
						try {
							Date marriage_date = new SimpleDateFormat("dd/MMM/yyyy").parse(marriageline.arguments.replaceAll(" ", "/"));
							fam.setMarriageDate(marriage_date);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					case "HUSB":
						fam.setHusbandId(current.arguments);
						for (Individual indi : individuals) {
							if (indi.getId().compareTo(current.arguments) == 0) {
								fam.setHusbandName(indi.getName());
							}
						}
						break;
					case "WIFE":
						fam.setWifeId(current.arguments);
						for (Individual indi : individuals) {
							if (indi.getId().compareTo(current.arguments) == 0) {
								fam.setWifeName(indi.getName());
							}
						}
						break;
					case "CHIL":
						fam.addChild(current.arguments);
						break;
					case "DIV":
						Line divorceline = parseLine(lines.get(i+1));
						if (divorceline.tag.compareTo("DATE") == 0) {
							try {
								Date divorce_date = new SimpleDateFormat("dd/MMM/yyyy").parse(divorceline.arguments.replaceAll(" ", "/"));
								fam.setDivorceDate(divorce_date);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						break;
					default: 
						break;
				}
			}
		}
		if (fam != null) {
			families.add(fam);
		}
	}

	public static boolean checkUniqueIds() {
		boolean unique = true;
		ArrayList<String> ids = new ArrayList<String>();
		for (Individual i : individuals) {
			if (ids.contains(i.getId())) {
				unique = false;
				System.out.println("ERROR: [US22] INDIVIDUAL " + i.getId() + " - Duplicate ID.");
			} else {
				ids.add(i.getId());
			}
		}

		for (Family f : families) {
			if (ids.contains(f.getId())) {
				unique = false;
				System.out.println("ERROR: [US22] FAMILY " + f.getId() + " - Duplicate ID.");
			} else {
				ids.add(f.getId());
			}
		}

		// Put ids into hash sets and compare sizes
		// HashSet<String> individual_set = new HashSet<String>(individual_ids);
		// HashSet<String> family_set = new HashSet<String>(family_ids);
		// if (individual_set.size() < individual_ids.size() || family_set.size() < family_ids.size()) {
		// 	return false;
		// }

		return unique;
	}

	public static void printIndividuals() {
		System.out.println("Individuals");
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
		System.out.format("%5s | %20s | %6s | %12s | %4s | %6s | %12s | %10s | %10s \n", "ID", "NAME", "GENDER", "BIRTHDAY", "AGE", "ALIVE", "DEATH", "CHILD", "SPOUSE");
		System.out.println("-------------------------------------------------------------------------------------------------------------");
		String id, name, gender, birthday, age, alive, deathdate, child, spouse;
		Collections.sort(individuals, (Individual i1, Individual i2) -> {
			if (i1.getId().length() > i2.getId().length()) {
				return 1;
			} else if (i1.getId().length() < i2.getId().length()) {
				return -1;
			} else {
				return i1.getId().compareTo(i2.getId());
			}
		});
		for (Individual i : individuals) {
			id = i.getId() != null ? i.getId() : "N/A";
			name = i.getName() != null ? i.getName() : "N/A";
			gender = i.getGender() != null ? i.getGender() : "N/A";
			age = i.getAge() == 0 ? Integer.toString(i.getAge()) : "N/A";
			alive = i.getAlive() != null ? i.getAlive().toString() : "N/A";
			birthday = i.getBirthday() != null ? f.format(i.getBirthday()) : "N/A";
			try {
				deathdate = !i.getAlive() ? f.format(i.getDeath()) : "N/A";
			} catch (Exception e) {
				deathdate = "N/A";
			}
			child = i.getChildren().size() == 0 ? "N/A" : i.getChildren().toString();
			spouse= i.getSpouse().size() == 0 ? "N/A" : i.getSpouse().toString();
			System.out.format("%5s | %20s | %6s | %12s | %4s | %6s | %12s | %10s | %10s \n", id, name, gender, 
				birthday, i.getAge(), i.getAlive(), deathdate, child, spouse);
		}
		System.out.println("");
    }

	public static void printFamilies() {
		System.out.println("Families");
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
		System.out.format("%5s | %12s | %12s | %10s | %20s | %8s | %20s | %16s \n", "ID", "MARRIED", "DIVORCED", "HUSBAND ID", "HUSBAND NAME", "WIFE ID", "WIFE NAME", "CHILDREN");
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		String id, marriagedate, divorcedate, husband_id, husband_name, wife_id, wife_name, children;
		Collections.sort(families, (Family f1, Family f2) -> {
			if (f1.getId().length() > f2.getId().length()) {
				return 1;
			} else if (f1.getId().length() < f2.getId().length()) {
				return -1;
			} else {
				return f1.getId().compareTo(f2.getId());
			}
		});
		for (Family fam : families) {
			id = fam.getId() != null ? fam.getId() : "N/A";
			husband_id = fam.getHusbandId() != null ? fam.getHusbandId() : "N/A";
			husband_name = fam.getHusbandName() != null ? fam.getHusbandName() : "N/A";
			wife_id = fam.getWifeId() != null ? fam.getWifeId() : "N/A";
			wife_name = fam.getWifeName() != null ? fam.getWifeName() : "N/A";
			marriagedate = fam.getMarriageDate() != null ? f.format(fam.getMarriageDate()) : "N/A";
			divorcedate = fam.getDivorceDate() != null ? f.format(fam.getDivorceDate()) : "N/A";
			children = fam.getChildren().size() == 0 ? "N/A" : fam.getChildren().toString();
			System.out.format("%5s | %12s | %12s | %10s | %20s | %8s | %20s | %16s \n", id, marriagedate, divorcedate, 
				husband_id, husband_name, wife_id, wife_name, children);
		}
		System.out.println("");
    }

	public static ArrayList<String> printDeceased(){
		ArrayList<String> deceasedList = new ArrayList<String>();
		for (Individual i : individuals) {
			if (!i.getAlive()) {
				deceasedList.add(i.getId());
				System.out.println("INDIVIDUAL " + i.getId() + " is deceased");
			} 
		}
		return deceasedList;
	}

	public static Family getFamily(String id) {
		for (Family f : families) {
			if (f.getId().equals(id)) {
				return f;
			}
		}
		// throw new IllegalArgumentException("Family with id '" + id + "' not found.");
		return null;
	}

    public static Individual getIndividual(String id) {
        for (Individual i : individuals) {
            // is child and is a mother
            if (i.getId().equals(id)) {
                return i;
            }
        }
        // throw new IllegalArgumentException("Individual with id '" + id + "' not found.");
		return null;
    }

	public static boolean listLivingMarried() {
		System.out.println("\nLiving and Married Individuals:");
		System.out.println("===============================");
		try {

			if (GEDCOM.individuals.isEmpty()) {
				System.out.println("No individuals found");
			} else {
				// Add all marriages to a list of pairs to check if individual is currently married
				ArrayList<Marriage> marriages = new ArrayList<Marriage>();
				Family f;
				Individual s;
				ArrayList<String> spouses;
				for (Individual indi : GEDCOM.individuals) {
					spouses = indi.getSpouse();
					if (indi.getDeath() == null && !spouses.isEmpty()) {
						// Check if not divorced or widowed
						for (int i = 0; i < spouses.size(); i++) {
							f = getFamily(spouses.get(i));
							if (f == null) {
								System.out.println("[ERROR] Family " + spouses.get(i) + " does not exist.");
								continue;
							}
							if (indi.getGender().equals("Male") || indi.getGender().equals("M")) {
								s = getIndividual(f.getWifeId());
							} else {
								s = getIndividual(f.getHusbandId());
							}
							if (f.getDivorceDate() != null) { // Check if divorced 
								marriages.add(new Marriage("M" + i, f.getMarriageDate(), f.getDivorceDate()));
							} else if (!s.getAlive()) { // Check if widow/widower
								marriages.add(new Marriage("M" + i, f.getMarriageDate(), s.getDeath()));
							} else {
								marriages.add(new Marriage("M" + i, f.getMarriageDate(), null));
							}
						}
					}
					Date currentdate = new Date();
					for (Marriage m : marriages) {
						if (m.during(currentdate)) {
							System.out.println("ID: " + indi.getId() + ", Name: " + indi.getName());
						}
					}
					marriages.clear();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static List<Individual> listRecentDeaths(){
		ArrayList<Individual> deadPeople = new ArrayList<Individual>();
		for (Individual i : individuals) {
			if(!i.getAlive()){

				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
				Date firstDate = null;
				firstDate = Date.from(Instant.now());
				Date secondDate = i.getDeath();

				long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
				long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

				if(diff < 31){
					deadPeople.add(i);
				}
			}
		}
		return deadPeople;
	}

	public static List<Individual> listUpComingBday(){
		ArrayList<Individual> bdayPeople = new ArrayList<Individual>();
		for (Individual i : individuals) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
			Date firstDate = null;
			firstDate = Date.from(Instant.now());
			Date secondDate = i.getBirthday();
			long diffInMillies = firstDate.getTime() - secondDate.getTime();
			long difference_In_Days
					= (diffInMillies
					/ (1000 * 60 * 60 * 24))
					% 365;

			long days = difference_In_Days%365;
			if(days < 31){
				bdayPeople.add(i);
			}
		}
		return bdayPeople;
	}
	public static ArrayList<String> getSinglePeople(){
		ArrayList<String> singleList = new ArrayList<String>();
		for (Individual i : individuals) {
			if (i.getSpouse() == null && i.getAge() > 30 && i.getAlive()) {
				singleList.add(i.getId());
				System.out.println("INDIVIDUAL " + i.getId() + " is single all their life and over 30");
			} 
		}
		return singleList;
	}

	public static ArrayList<String> getMultipleBirths(){
		ArrayList<String> multipleBirths = new ArrayList<String>();
		for(Family f: families){
			HashMap<Date,ArrayList<String>> mapOfBDayAndName = new HashMap<>();
			for (String id : f.getChildren()) {
				Individual sibling = getIndividual(id);
				if(!mapOfBDayAndName.containsKey(sibling.getBirthday())){
					mapOfBDayAndName.put(sibling.getBirthday(),new ArrayList<>());
				}
				mapOfBDayAndName.get(sibling.getBirthday()).add(sibling.getId());
			}
			for (Map.Entry<Date, ArrayList<String>> entry : mapOfBDayAndName.entrySet()) {
				if(entry.getValue().size() > 1){
					multipleBirths.addAll(entry.getValue());
				}
			}
		}
		return multipleBirths;
	}


	/**
	 * Prints a list of all living couples' marriage anniversaries that occur in the next 30 days
	 */
	public static void upcomingAnniversaries() {
		System.out.println("\nUpcoming Anniversaries:");
		System.out.println("===============================");
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
		long day30 = 30l * 24 * 60 * 60 * 1000; // https://stackoverflow.com/questions/29252949/best-way-to-check-if-a-java-util-date-is-older-than-30-days-compared-to-current
		Individual husband, wife;
		boolean upcoming;
		if (GEDCOM.families.isEmpty()) {
			System.out.println("No families found");
		} else {
			Calendar cal = Calendar.getInstance();
			Date marriage;

			// Set year of current date to default
			Date currentdate = new Date();
			cal.setTime(currentdate); // your date (java.util.Date)
			cal.add(Calendar.YEAR, -cal.get(Calendar.YEAR)); 
			currentdate = cal.getTime(); // New date

			for (Family fam : families) {
				husband = getIndividual(fam.getHusbandId());
				wife = getIndividual(fam.getWifeId());
				if (husband == null || wife == null) {
					System.out.println("Error: Family ID: " + fam.getId() + ", Husband or Wife does not exist");
					continue;
				}
				if (!husband.getAlive() || !wife.getAlive()) {
					continue;
				}
				// Set year to default year
				cal.setTime(fam.getMarriageDate()); // your date (java.util.Date)
				cal.add(Calendar.YEAR, -cal.get(Calendar.YEAR)); 
				marriage = cal.getTime(); // New date
				upcoming = marriage.after(currentdate) && !currentdate.before(new Date((marriage.getTime() - day30)));
				if (upcoming) {
					System.out.println("Family ID: " + fam.getId() + ", Husband: " + fam.getHusbandName() + ", Wife: " + fam.getWifeName() + ", Marriage: " + f.format(fam.getMarriageDate()));
				}
			}
		}
	}

    public static void main(String[] args) throws ParseException {
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
		ArrayList<String> lines = GEDCOM.readFile(file);

		// Line current;
		// // Parse each line
		// for (String line : lines) {
		// 	System.out.println("--> " + line);
		// 	current = parser.parseLine(line);
		// 	System.out.println(current);
		// }

		// Print individuals
		parser.getIndividuals(lines);
		printIndividuals();

		parser.getFamilies(lines);
		printFamilies();

		// US01 - Dates before current date
		Dates.checkDates(file);

		// US22 - Unique IDs
		checkUniqueIds();

		// US11 - No bigamy
		for (Individual i : GEDCOM.individuals) {
			i.checkBigamy();
		}

		// US15 - Fewer than 15 Siblings
		for (Family f : GEDCOM.families) {
			f.fewerThanFifteenSiblings();
		}
		// US06 - Divorce before death

		// for (Individual fam: families){
		// 	fam.isBirthBeforeDeath();
		// }

		// Birth Before Death
		for (Individual i : individuals){
			ArrayList<String> spouse_id = i.getSpouse();
				i.isBirthBeforeDeath();
				for(String j: spouse_id){
					i.isDivorceBeforeDeath(i.getIndividual(j));
					i.isTheMarriageBeforeDeath(i.getIndividual(j));
					i.isTheMarriageBeforeDivorce(i.getIndividual(j));
					i.marriageAfterFourteen(i.getIndividual(j));
					i.isBirthBeforeMarriage(i.getIndividual(j));
					i.uniqueNameAndBirthDate();
					i.isUncleOrAunt(i.getIndividual(j));

			}
		}

		// US02 - Birth Before Marriage
		for (Individual i : individuals){
			i.birthBeforeMarriage();
		}

		// US29 - Print Deceased
		printDeceased();

		// US30 - List living married
		listLivingMarried();
		
		// US39 - List upcoming anniversaries
		upcomingAnniversaries();
		
		// US42 - Reject illegitimate dates
		Boolean illegitimate_dates = Dates.checkIllegitimateDates(lines, parser);
		if (illegitimate_dates) {
			System.out.println("Date(s) validated.");
		} else {
			System.out.println("==> ERROR: Illegitimate date(s) found.");
		}
    }
}
