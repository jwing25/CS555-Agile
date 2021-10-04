package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import GEDCOM.GEDCOM;

class DatesBeforeCurrent {
	
	File file1 = new File("/test.ged");
	File file2 = new File("/test2.ged");
	File file3 = new File("/test3.ged");
	
	static Date current_date = new Date();
	static Calendar calendar = Calendar.getInstance();
	
	private static void changeDatesToCurrent(File file) {
		// Main logic followed from: https://stackoverflow.com/questions/20039980/java-replace-line-in-text-file
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			StringBuffer linebuf = new StringBuffer();
			String line;
			
			while ((line = reader.readLine()) != null) {
				String[] array = line.split(" ");
				String level = array[0], tag = array[1];
				
				if (tag.equals("DATE")) {
					String d = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)),
							m = (new SimpleDateFormat("MMM").format(current_date)).toUpperCase(), // + 1 since starts at 0
							y = Integer.toString(calendar.get(Calendar.YEAR));
					line = level + " " + tag + " " + d + " " + m + " " + y;
				}
				linebuf.append(line);
				linebuf.append('\n');
			}
			reader.close();
			
			FileOutputStream overwrite = new FileOutputStream(file);
			overwrite.write(linebuf.toString().getBytes());
			overwrite.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Test
	void testCheckDatesValid() {
		assertTrue(GEDCOM.checkDates(file1));
	}
	
	@Test
	void testCheckDatesInvalid() {
		assertFalse(GEDCOM.checkDates(file2));
	}
	
	@Test
	void testCheckDatesCurrentDate() {
		// Change dates of file3 to the current date
		changeDatesToCurrent(file3);
		assertTrue(GEDCOM.checkDates(file3));
	}
	
	@Test
	void testParseLine() {
		assertEquals(GEDCOM.getDate("0 DATE 5 APR 2020").toString(), "Sun Apr 05 00:00:00 EDT 2020");
	}
	
	@Test
	void testParseLineNull() {
		assertNull(GEDCOM.getDate("0 NOTE dates after now"));
	}
}
