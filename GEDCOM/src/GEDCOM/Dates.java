package GEDCOM;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import GEDCOM.GEDCOM.Line;

public class Dates {
	/**
     * Checks if a line is a date
     * @param line The line to parse
     * @return The date if the tag is DATE, null otherwise.
     */
	public static Date getDate(String line) {
		GEDCOM parser = new GEDCOM();
		Date date;
		Line parsed_line;
		try {
			parsed_line = parser.parseLine(line);
		} catch (Exception e) {
			throw e;
		}
        if (parsed_line.tag.equals("DATE")) {
            String[] date_arr = parsed_line.arguments.split(" ");
            String date_string = date_arr[0] + "/" + date_arr[1] + "/" + date_arr[2];
            try {
                date = new SimpleDateFormat("dd/MMM/yyyy").parse(date_string);
            } catch (Exception e) {
                e.printStackTrace();
                date = null;
            }
        } else {
            date = null;
        }
        return date;
	}

	/**
	 * Checks if dates in Individuals and Families are before the current date
	 * @param file
	 * @return True if the date is before the current date, false if the date is after the current date
	 */
	public static Boolean checkDates(File file) {
		Date current_date = new Date();
        // System.out.println("The current date is: " + current_date);
        
        Boolean valid = true;
        ArrayList<String> lines = GEDCOM.readFile(file);
        Date date;
        for (String line : lines) {
            date = getDate(line);
            if (date != null) {
                if (date.after(current_date)) {
					valid = false;
					break;
				}
            }
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        // Error messages
        for (Individual i : GEDCOM.individuals) {
            if (i.getBirthday() != null && i.getBirthday().after(current_date)) {
                System.out.println("ERROR: [US01] INDIVIDUAL " + i.getId() + " - Birthday on " + formatter.format(i.getBirthday()) + " occurs after the current date " + formatter.format(current_date) + ".");
            }
            if (i.getDeath() != null && i.getDeath().after(current_date)) {
                System.out.println("ERROR: [US01] INDIVIDUAL " + i.getId() + " - Death on " + formatter.format(i.getDeath()) + " occurs after the current date " + formatter.format(current_date) + ".");
            }
        }
        for (Family f : GEDCOM.families) {
            if (f.getMarriageDate() != null && f.getMarriageDate().after(current_date)) {
                System.out.println("ERROR: [US01] FAMILY " + f.getId() + " - Marriage on " + formatter.format(f.getMarriageDate()) + " occurs after the current date " + formatter.format(current_date) + ".");
            }
            if (f.getDivorceDate() != null && f.getDivorceDate().after(current_date)) {
                System.out.println("ERROR: [US01] FAMILY " + f.getId() + " - Divorce on " + formatter.format(f.getDivorceDate()) + " occurs after the current date " + formatter.format(current_date) + ".");
            }
        }
		return valid;
	}

    public static Boolean checkIllegitimateDates(File file) {
        Boolean valid = true;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
        ArrayList<String> lines = GEDCOM.readFile(file);
        Date date;
        for (String line : lines) {
            date = getDate(line);
            if (!validDate(formatter.format(date))) {
                valid = false;
                System.out.println("ERROR: [US42] INVALID DATE " + formatter.format(date) + ".");
            }
        }
        return valid;
    }

    public static Boolean validDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
        formatter.setLenient(false);
        try {
            formatter.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }


	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}
	public static Date convertToDateViaInstant(LocalDate dateToConvert) {
		return java.util.Date.from(dateToConvert.atStartOfDay()
				.atZone(ZoneId.systemDefault())
				.toInstant());
	}

}
