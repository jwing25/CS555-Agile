package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;

import GEDCOM.Individual;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BirthBeforeDeathTest {
    public static Individual i1;
    public static Individual i2;
    public static Individual i3;
    public static Individual i4;

    @BeforeAll
    public static void init() {
        try {
            // same day
            i1 = new Individual("I1", "individual1", "M", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                                21, true, new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), null, null);        
            // after         
            i2 = new Individual("I2", "individual2", "F", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                                21, true, new SimpleDateFormat("MM/dd/yyyy").parse("1/2/2020"), null, null);
            // before
            i3 = new Individual("I2", "individual2", "M", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                                21, true, new SimpleDateFormat("MM/dd/yyyy").parse("1/1/1999"), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() {
        boolean is_birthbeforedeath = i1.isBirthBeforeDeath();
        assertTrue(is_birthbeforedeath);
    }

    @Test
    void test2() {
        boolean is_birthbeforedeath = i2.isBirthBeforeDeath();
        assertTrue(is_birthbeforedeath);
    }

    @Test
    void test3() {
        boolean is_birthbeforedeath = i3.isBirthBeforeDeath();
        assertFalse(is_birthbeforedeath);
    }

}
