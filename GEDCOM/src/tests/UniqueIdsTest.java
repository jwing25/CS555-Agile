package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;

import GEDCOM.GEDCOM;
import GEDCOM.Individual;
import GEDCOM.Family;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UniqueIdsTest {
    public static Individual i1;
    public static Individual i2;
    public static Individual i3;
    public static Individual i4;

    public static Family f1;
    public static Family f2;
    public static Family f3;

    @BeforeAll
    public static void init() {
        try {
            i1 = new Individual("I1", "individual1", "M", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                                21, true, null, null, null);                 
            i2 = new Individual("I2", "individual2", "F", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                                21, true, null, null, null);
            i3 = new Individual("I2", "individual2", "M", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                                21, true, null, null, null);
            i4 = new Individual("I3", "individual2", "F", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                                21, true, null, null, null);

            f1 = new Family("F1", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                            null, "I1", "individual1", "I2", "individual2", null);
            f2 = new Family("F2", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                            null, "I3", "individual1", "I4", "individual2", null);
            f3 = new Family("F2", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                            null, "I1", "individual1", "I4", "individual2", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() {
        GEDCOM.individuals.add(i1);
        GEDCOM.individuals.add(i2);
        GEDCOM.individuals.add(i4);

        boolean is_unique = GEDCOM.checkUniqueIds();
        assertTrue(is_unique);

        GEDCOM.individuals.removeAll(GEDCOM.individuals);
    }

    @Test
    void test2() {
        GEDCOM.individuals.add(i1);
        GEDCOM.individuals.add(i2);
        GEDCOM.individuals.add(i3);

        boolean is_unique = GEDCOM.checkUniqueIds();
        assertFalse(is_unique);

        GEDCOM.individuals.removeAll(GEDCOM.individuals);
    }

    @Test
    void test3() {
        GEDCOM.families.add(f1);
        GEDCOM.families.add(f2);

        boolean is_unique = GEDCOM.checkUniqueIds();
        assertTrue(is_unique);

        GEDCOM.families.removeAll(GEDCOM.families);
    }

    @Test
    void test4() {
        GEDCOM.families.add(f1);
        GEDCOM.families.add(f2);
        GEDCOM.families.add(f3);

        boolean is_unique = GEDCOM.checkUniqueIds();
        assertFalse(is_unique);

        GEDCOM.families.removeAll(GEDCOM.families);
    }



}
