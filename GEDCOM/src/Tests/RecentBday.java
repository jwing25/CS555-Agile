package Tests;

import GEDCOM.GEDCOM;
import GEDCOM.Individual;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecentBday {
    public static Individual husband1;
    public static Individual husband2;
    public static Individual wife1;
    public static Individual wife2;
    DateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    @Test
    void test1() throws ParseException {
        GEDCOM.individuals.add(new Individual("1", "1", "male", format.parse("12/1/1999"), 21,
        false, format.parse("11/1/2020"), null, null));
        assertTrue(GEDCOM.listUpComingBday().size() == 1);
        GEDCOM.individuals.remove(0);

    }

    @Test
    void test2() throws ParseException {
        GEDCOM.individuals.add(new Individual("1", "1", "male", format.parse("12/1/1999"), 21,
                false, format.parse("11/1/2021"), null, null));
        GEDCOM.individuals.add(new Individual("1", "1", "male", format.parse("1/1/1912"), 21,
                false, format.parse("11/1/2021"), null, null));
        GEDCOM.individuals.add(new Individual("1", "1", "male", format.parse("11/1/1939"), 21,
                false, format.parse("11/1/2021"), null, null));
        GEDCOM.individuals.add(new Individual("1", "1", "male", format.parse("12/1/1999"), 21,
                false, format.parse("11/1/2020"), null, null));

        assertTrue(GEDCOM.listUpComingBday().size() == 2);
        GEDCOM.individuals.clear();

    }

    @Test
    void test3() {
        assertTrue(GEDCOM.listUpComingBday().size() == 0);
    }

}
