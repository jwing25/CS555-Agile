package Tests;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import GEDCOM.Family;
import GEDCOM.GEDCOM;
import GEDCOM.Individual;

public class UpcomingAnniversariesTest {
    public static Individual i1;
    public static Individual i2;
    public static Individual i3;
    public static Individual i4;
    public static Individual i5;
    public static Individual i6;
    public static Individual i7;
    public static Individual i8;

    public static Family f1;
    public static Family f2;
    public static Family f3;
    public static Family f4;

    @BeforeAll
    public static void init() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
        Date date = new Date();
        Calendar c1 = Calendar.getInstance();
        String currentdate = formatter.format(date);
        
        // Within 30 days
        String within30 = "";
        try {
            c1.setTime(formatter.parse(currentdate));
            c1.add(Calendar.DATE, 29);
            Date resultdate = new Date(c1.getTimeInMillis());
            within30 = formatter.format(resultdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // After 30 days
        String after30 = "";
        try {
            c1.setTime(formatter.parse(currentdate));
            c1.add(Calendar.DATE, 31);
            Date resultdate = new Date(c1.getTimeInMillis());
            after30 = formatter.format(resultdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // On 30 days
        String on30 = "";
        try {
            c1.setTime(formatter.parse(currentdate));
            c1.add(Calendar.DATE, 30);
            Date resultdate = new Date(c1.getTimeInMillis());
            on30 = formatter.format(resultdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        // Day before
        String beforeDate = "";
        try {
            c1.setTime(formatter.parse(currentdate));
            c1.add(Calendar.DATE, -1);
            Date resultdate = new Date(c1.getTimeInMillis());
            beforeDate = formatter.format(resultdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            ArrayList<String> spouse1 = new ArrayList<String>();
            spouse1.add("F1");
            ArrayList<String> spouse2 = new ArrayList<String>();
            spouse2.add("F1");
            i1 = new Individual("I1", "individual1", "M", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/2000"), 
                                21, true, null, null, spouse1);          
            i2 = new Individual("I2", "individual2", "F", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/2000"), 
                                21, true, null, null, spouse2);
            // Within 30
            f1 = new Family("F1", new SimpleDateFormat("dd/MMM/yyyy").parse(within30), null, "I1", "INDI1", "I2", "INDI2", new ArrayList<String>());

            ArrayList<String> spouse3 = new ArrayList<String>();
            spouse3.add("F2");
            ArrayList<String> spouse4 = new ArrayList<String>();
            spouse4.add("F2");
            i3 = new Individual("I3", "individual2", "M", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/2000"), 
                                21, true, null, null, spouse3);
            i4 = new Individual("I4", "individual2", "F", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/1999"), 
                                21, true, null, null, spouse4);
            // After 30
            f2 = new Family("F2", new SimpleDateFormat("dd/MMM/yyyy").parse(after30), null, "I3", "INDI3", "I4", "INDI4", new ArrayList<String>());

            ArrayList<String> spouse5 = new ArrayList<String>();
            spouse5.add("F3");
            ArrayList<String> spouse6 = new ArrayList<String>();
            spouse6.add("F3");
            i5 = new Individual("I5", "individual2", "M", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JUL/1999"), 
                                21, true, null, null, spouse5);
            i6 = new Individual("I6", "individual2", "F", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/2000"), 
                                21, true, null, null, spouse6);
            // On 30
            f3 = new Family("F3", new SimpleDateFormat("dd/MMM/yyyy").parse(on30), null, "I5", "INDI5", "I6", "INDI6", new ArrayList<String>());

            ArrayList<String> spouse7 = new ArrayList<String>();
            spouse5.add("F4");
            ArrayList<String> spouse8 = new ArrayList<String>();
            spouse6.add("F4");
            i7 = new Individual("I7", "individual2", "M", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JUL/1999"), 
                                21, true, null, null, spouse7);
            i8 = new Individual("I8", "individual2", "F", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/2000"), 
                                21, true, null, null, spouse8);
            // Before date
            f4 = new Family("F3", new SimpleDateFormat("dd/MMM/yyyy").parse(beforeDate), null, "I7", "INDI7", "I8", "INDI8", new ArrayList<String>());
            
            GEDCOM.families.add(f1);
            GEDCOM.families.add(f2);
            GEDCOM.families.add(f3);
            GEDCOM.families.add(f4);

            GEDCOM.individuals.add(i1);
            GEDCOM.individuals.add(i2);
            GEDCOM.individuals.add(i3);
            GEDCOM.individuals.add(i4);
            GEDCOM.individuals.add(i5);
            GEDCOM.individuals.add(i6);
            GEDCOM.individuals.add(i7);
            GEDCOM.individuals.add(i8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() {
        GEDCOM.upcomingAnniversaries();
    }

}
