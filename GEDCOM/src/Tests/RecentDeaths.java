package Tests;

import GEDCOM.GEDCOM;
import GEDCOM.Individual;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import GEDCOM.Family;

public class RecentDeaths {
    public static Individual husband1;
    public static Individual husband2;
    public static Individual wife1;
    public static Individual wife2;
    DateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    @BeforeAll
    public static void init() {
        try {
            ArrayList<String> spouse1 = new ArrayList<String>();
            spouse1.add("spouse1");

            ArrayList<String> spouse2 = new ArrayList<String>();
            spouse2.add("spouse2");
            spouse2.add("spouse3");
            spouse2.add("spouse4");

            husband1 = new Individual("husband1","husband1","M",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null,null, spouse1);

            husband2 = new Individual("123", "Justin", "M",
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/03/1950"),
                    71, false,
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/03/2021"),
                    null, spouse1);

            wife1 = new Individual("wife1","wife1","F",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1953" ),
                    74 ,true,null,null, spouse1);

            wife2 = new Individual("456", "Queen Elizabeth", "F",
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/03/1900"),
                    80, false,
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/03/1980"),
                    null, spouse2);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() throws ParseException {
        GEDCOM.individuals.add(new Individual("1", "1", "male", format.parse("12/1/1999"), 21,
        false, format.parse("11/1/2020"), null, null));
        assertTrue(GEDCOM.listRecentDeaths().size() == 0);
        GEDCOM.individuals.remove(0);

    }

    @Test
    void test2() throws ParseException {
        GEDCOM.individuals.add(new Individual("1", "1", "male", format.parse("12/1/1999"), 21,
                false, format.parse("11/1/2021"), null, null));
        GEDCOM.individuals.add(new Individual("1", "1", "male", format.parse("12/1/1999"), 21,
                false, format.parse("11/1/2020"), null, null));

        assertTrue(GEDCOM.listRecentDeaths().size() == 1);
        GEDCOM.individuals.clear();

    }

    @Test
    void test3() {
        assertTrue(GEDCOM.listRecentDeaths().size() == 0);
    }

}
