package tests;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import GEDCOM.GEDCOM;
import GEDCOM.Individual;
import GEDCOM.Family;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DatesTest {
    public static Individual husband1;
    public static Individual husband2;
    public static Individual wife1;
    public static Individual wife2;

    @BeforeAll
    public static void init() {
        try {
            ArrayList<String> spouse1 = new ArrayList<String>();
            spouse1.add("spouse1");

            ArrayList<String> spouse2 = new ArrayList<String>();
            spouse2.add("spouse2");
            spouse2.add("spouse3");
            spouse2.add("spouse4");

            husband1 = new Individual("husband1","husband1","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null,null, spouse1);

            husband2 = new Individual("123", "Justin", "Male",
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/03/1950"),
                    71, false,
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/03/2021"),
                    null, spouse1);

            wife1 = new Individual("wife1","wife1","Female",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1953" ),
                    74 ,true,null,null, spouse1);

            wife2 = new Individual("456", "Queen Elizabeth", "Female",
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/03/1900"),
                    80, false,
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/03/1980"),
                    null, spouse2);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() {
        // Date is good; valid
        try {
            GEDCOM.families.add(new Family("1",
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/1950"),null,
                    husband1.getId(),husband1.getName(),wife1.getId(),wife1.getName(), null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(husband1.isTheMarriageBeforeDeath(wife1));
        GEDCOM.families.remove(0);

    }

    @Test
    void test2() {
        // Date is from future; not valid
        try {
            GEDCOM.families.add(new Family("1",
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/1950"),null,
                    husband1.getId(),husband1.getName(),wife2.getId(),wife2.getName(), null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(husband1.isTheMarriageBeforeDeath(wife2));
        GEDCOM.families.remove(0);

    }

    @Test
    void test3() {
        // Date is from future; not valid
        try {
            GEDCOM.families.add(new Family("1",
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/2022"),null,
                    husband2.getId(),husband2.getName(),wife1.getId(),wife1.getName(), null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertFalse(husband2.isTheMarriageBeforeDeath(wife1));
        GEDCOM.families.remove(0);

    }

    @Test
    void test4() {
        // Date is good; valid
        try {
            GEDCOM.families.add(new Family("1",
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/1990"),null,
                    husband2.getId(),husband2.getName(),wife2.getId(),wife2.getName(), null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertFalse(husband2.isTheMarriageBeforeDeath(wife2));
        GEDCOM.families.remove(0);

    }



}
