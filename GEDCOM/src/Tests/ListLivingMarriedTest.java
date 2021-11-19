package Tests;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import GEDCOM.Family;
import GEDCOM.GEDCOM;
import GEDCOM.Individual;

public class ListLivingMarriedTest {
    public static Individual i1;
    public static Individual i2;
    public static Individual i3;
    public static Individual i4;
    public static Individual i5;
    public static Individual i6;

    public static Family f1;
    public static Family f2;
    public static Family f3;

    @BeforeAll
    public static void init() {
        try {
            ArrayList<String> spouse1 = new ArrayList<String>();
            spouse1.add("F1");
            ArrayList<String> spouse2 = new ArrayList<String>();
            spouse2.add("F1");
            i1 = new Individual("I1", "individual1", "M", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/2000"), 
                                21, true, null, null, spouse1);          
            i2 = new Individual("I2", "individual2", "F", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/2000"), 
                                21, true, null, null, spouse2);
            f1 = new Family("F1", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JUL/2020"), null, "I1", "INDI1", "I2", "INDI2", new ArrayList<String>());

            ArrayList<String> spouse3 = new ArrayList<String>();
            spouse3.add("F2");
            ArrayList<String> spouse4 = new ArrayList<String>();
            spouse4.add("F2");
            i3 = new Individual("I3", "individual2", "M", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/2000"), 
                                21, true, null, null, spouse3);
            i4 = new Individual("I4", "individual2", "F", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/1999"), 
                                21, true, null, null, spouse4);
            f2 = new Family("F2", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JUL/2020"), null, "I3", "INDI3", "I4", "INDI4", new ArrayList<String>());

            ArrayList<String> spouse5 = new ArrayList<String>();
            spouse5.add("F3");
            ArrayList<String> spouse6 = new ArrayList<String>();
            spouse6.add("F3");
            i5 = new Individual("I5", "individual2", "M", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JUL/1999"), 
                                21, true, null, null, spouse5);
            i6 = new Individual("I6", "individual2", "F", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/2000"), 
                                21, true, null, null, spouse6);
            f3 = new Family("F3", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JUL/2020"), null, "I5", "INDI5", "I6", "INDI6", new ArrayList<String>());

            GEDCOM.families.add(f1);
            GEDCOM.families.add(f2);
            GEDCOM.families.add(f3);

            GEDCOM.individuals.add(i1);
            GEDCOM.individuals.add(i2);
            GEDCOM.individuals.add(i3);
            GEDCOM.individuals.add(i4);
            GEDCOM.individuals.add(i5);
            GEDCOM.individuals.add(i6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() {
        assertTrue(GEDCOM.listLivingMarried());
    }
}
