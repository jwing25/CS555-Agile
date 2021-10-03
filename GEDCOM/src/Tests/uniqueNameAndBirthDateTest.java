package Tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;

import GEDCOM.Individual;
import GEDCOM.GEDCOM;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class uniqueNameAndBirthDateTest {
    public static Individual i1;
    public static Individual i2;
    public static Individual i3;
    public static Individual i4;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void test1() {
        boolean uniqueNameAndBirthDate = i1.uniqueNameAndBirthDate();
        assertTrue(uniqueNameAndBirthDate);
    }

    @Test
    void test2() {
        boolean uniqueNameAndBirthDate= i2.uniqueNameAndBirthDate();
        assertTrue(uniqueNameAndBirthDate);
    }

    @Test
    void test3() {
        boolean uniqueNameAndBirthDate = i3.uniqueNameAndBirthDate();
        assertFalse(uniqueNameAndBirthDate);
    }

    @Test
    void test4() {
        boolean uniqueNameAndBirthDate = i4.uniqueNameAndBirthDate();
        assertTrue(uniqueNameAndBirthDate);
    }
    
}
