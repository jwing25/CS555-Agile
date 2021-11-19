package Tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;

import GEDCOM.GEDCOM;
import GEDCOM.Individual;
import java.util.ArrayList;
import GEDCOM.Family;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class getDeceasedList {
    public static Individual i1;
    public static Individual i2;
    public static Individual i3;
    public static Individual i4;
    public static Individual i5;


    @BeforeAll

    // public Individual(String id, String name, String gender, Date birthday, int age, 
    // Boolean is_alive, Date death_date, ArrayList<String> childof, ArrayList<String> spouse)
    public static void init() {
        try {
            i1 = new Individual("I1", "individual1", "M", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                                21, false, new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2021"), null, null);                 
            i2 = new Individual("I2", "individual2", "F", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                                21, true, null, null, null);
            i3 = new Individual("I3", "individual3", "M", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                                21, false, new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2021"), null, null);
            i4 = new Individual("I4", "individual4", "F", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                                21, true, null, null, null);
            i5 = new Individual("I5", "individual5", "F", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
            21, false, new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2021"), null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() {
        GEDCOM.individuals.add(i1);
        GEDCOM.individuals.add(i3);
        GEDCOM.individuals.add(i5);

        ArrayList<String> deceasedList = GEDCOM.printDeceased();
        assertTrue(deceasedList.contains("I1") && deceasedList.contains("I3") && deceasedList.contains("I5"));

        GEDCOM.individuals.removeAll(GEDCOM.individuals);
    }

    @Test
    void test2() {
        GEDCOM.individuals.add(i2);
        GEDCOM.individuals.add(i4);

        ArrayList<String> deceasedList = GEDCOM.printDeceased();
        assertTrue(deceasedList.isEmpty());

        GEDCOM.individuals.removeAll(GEDCOM.individuals);
    }

    @Test
    void test3() {


        GEDCOM.individuals.add(i1);
        GEDCOM.individuals.add(i2);
        GEDCOM.individuals.add(i3);


        ArrayList<String> deceasedList = GEDCOM.printDeceased();
        assertTrue(deceasedList.contains("I1") && !deceasedList.contains("I2") && deceasedList.contains("I3"));

        GEDCOM.individuals.removeAll(GEDCOM.individuals);
    }




}
