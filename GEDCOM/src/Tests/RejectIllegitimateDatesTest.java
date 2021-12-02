package Tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import GEDCOM.Dates;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RejectIllegitimateDatesTest {
    public static String date1;
    public static String date2;
    public static String date3;
    public static String date4;
    public static String date5;

    @BeforeAll
    public static void init() {
        date1 = "30/FEB/2015"; // Illegitimate
        date2 = "33/JAN/2022"; // Illegitimate
        date3 = "5/MAR/2016"; // Legitimate
        date4 = "22/JUL/2021"; // Legitimate
        date5 = "29/FEB/2024"; // Legitimate leap year
    }

    @Test
    void test1() {
        assertFalse(Dates.validDate(date1));
    }

    @Test
    void test2() {
        assertFalse(Dates.validDate(date2));
    }

    @Test
    void test3() {
        assertTrue(Dates.validDate(date3));
    }

    @Test
    void test4() {
        assertTrue(Dates.validDate(date4));
    }

    @Test
    void test5() {
        assertTrue(Dates.validDate(date5));
    }

}
