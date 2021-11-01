package Tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import GEDCOM.Family;
import GEDCOM.GEDCOM;
import GEDCOM.Individual;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BigamyTest {
    public static Individual i1;
    public static Individual i2;
    public static Individual i3;
    public static Individual i4;
    public static Individual i5;
    public static Individual i6;
    public static Individual i7;
    public static Individual i8;
    public static Individual i9;
    public static Individual i10;
    public static Individual i11;
    public static Individual i12;

    public static Family f1;
    public static Family f2;
    public static Family f3;
    public static Family f4;
    public static Family f5;
    public static Family f6;
    public static Family f7;


    @BeforeAll
    public static void init() {
        try {
            // No bigamy; One spouse
            ArrayList<String> spouse1 = new ArrayList<String>();
            spouse1.add("F1");
            ArrayList<String> spouse2 = new ArrayList<String>();
            spouse2.add("F1");
            i1 = new Individual("I1", "INDI1", "M", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/1995"), 26, true, null, null, spouse1);
            i2 = new Individual("I2", "INDI2", "F", new SimpleDateFormat("dd/MMM/yyyy").parse("1/APR/1995"), 26, true, null, null, spouse2);
            f1 = new Family("F1", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JUL/2020"), null, "I1", "INDI1", "I2", "INDI2", new ArrayList<String>());

            // No bigamy; No spouses
            ArrayList<String> spouse3 = new ArrayList<String>();
            i3 = new Individual("I3", "INDI3", "F", new SimpleDateFormat("dd/MMM/yyyy").parse("7/JAN/1995"), 26, true, null, null, spouse3);

            // Bigamy; No divorce/death
            ArrayList<String> spouse4 = new ArrayList<String>();
            spouse4.add("F2");
            spouse4.add("F3");
            i4 = new Individual("I4", "INDI4", "M", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/1995"), 26, true, null, null, spouse4);
            i5 = new Individual("I5", "INDI5", "F", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/1995"), 26, true, null, null, new ArrayList<String>());
            f2 = new Family("F2", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/2020"), null, "I4", "INDI4", "I5", "INDI5", new ArrayList<String>());
            i6 = new Individual("I6", "INDI5", "F", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/1995"), 26, true, null, null, new ArrayList<String>());
            f3 = new Family("F3", new SimpleDateFormat("dd/MMM/yyyy").parse("1/MAR/2020"), null, "I4", "INDI4", "I6", "INDI6", new ArrayList<String>());

            // No bigamy; Marriage after death
            ArrayList<String> spouse7 = new ArrayList<String>();
            spouse7.add("F4");
            spouse7.add("F5");
            i7 = new Individual("I7", "INDI7", "M", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/1995"), 26, true, null, null, spouse7);
            i8 = new Individual("I8", "INDI8", "F", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/1995"), 26, false, new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/2021"), null, new ArrayList<String>());
            f4 = new Family("F4", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/2020"), null, "I7", "INDI7", "I8", "INDI8", new ArrayList<String>());
            i9 = new Individual("I9", "INDI9", "F", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/1995"), 26, true, null, null, new ArrayList<String>());
            f5 = new Family("F5", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JUL/2021"), null, "I7", "INDI7", "I9", "INDI9", new ArrayList<String>());

            // No bigamy; Marriage after divorce
            ArrayList<String> spouse10 = new ArrayList<String>();
            spouse10.add("F6");
            spouse10.add("F7");
            i10 = new Individual("I10", "INDI10", "M", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/1995"), 26, true, null, null, spouse10);
            i11 = new Individual("I11", "INDI11", "F", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/1995"), 26, true, null, null, new ArrayList<String>());
            f6 = new Family("F6", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/2020"), new SimpleDateFormat("dd/MMM/yyyy").parse("1/MAY/2020"), "I10", "INDI10", "I11", "INDI11", new ArrayList<String>());
            i12 = new Individual("I12", "INDI12", "F", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JAN/1995"), 26, true, null, null, new ArrayList<String>());
            f7 = new Family("F7", new SimpleDateFormat("dd/MMM/yyyy").parse("1/JUL/2021"), null, "I10", "INDI10", "I12", "INDI12", new ArrayList<String>());

            GEDCOM.families.add(f1);
            GEDCOM.families.add(f2);
            GEDCOM.families.add(f3);
            GEDCOM.families.add(f4);
            GEDCOM.families.add(f5);
            GEDCOM.families.add(f6);
            GEDCOM.families.add(f7);

            GEDCOM.individuals.add(i1);
            GEDCOM.individuals.add(i2);
            GEDCOM.individuals.add(i3);
            GEDCOM.individuals.add(i4);
            GEDCOM.individuals.add(i5);
            GEDCOM.individuals.add(i6);
            GEDCOM.individuals.add(i7);
            GEDCOM.individuals.add(i8);
            GEDCOM.individuals.add(i9);
            GEDCOM.individuals.add(i10);
            GEDCOM.individuals.add(i11);
            GEDCOM.individuals.add(i12);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() {
        // Single spouse
        assertTrue(i1.checkBigamy());
        assertTrue(i2.checkBigamy());
    }

    @Test
    void test2() {
        // No spouses
        assertTrue(i3.checkBigamy());
    }

    @Test
    void test3() {
        // Two spouses; No death/divorce
        assertFalse(i4.checkBigamy());
    }

    @Test
    void test4() {
        // Two spouses; Remarriage after death
        assertTrue(i7.checkBigamy());
    }

    @Test
    void test5() {
        // Two spouses; Remarriage after divorce
        assertTrue(i10.checkBigamy());
    }
}