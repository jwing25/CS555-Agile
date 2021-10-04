package Tests;

import GEDCOM.Family;
import GEDCOM.GEDCOM;
import GEDCOM.Individual;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DivorceBeforeDeathTest {
    public static Individual husband1;
    public static Individual husband2;
    public static Individual wife1;
    public static Individual wife2;

    @BeforeAll
    public static void init() {
        try {
            husband1 = new Individual("husband1","husband1","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null,null,new String[1]);

            husband2 = new Individual("123", "Justin", "Male",
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/03/1950"),
                    71, false,
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/03/2021"),
                    null, new String[1]);

            wife1 = new Individual("wife1","wife1","Female",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1953" ),
                    74 ,true,null,null,new String[1]);

            wife2 = new Individual("456", "Queen Elizabeth", "Female",
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/03/1900"),
                    80, false,
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/03/1980"),
                    null, new String[3]);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //    public Family(String id, Date marriage_date, Date divorce_date, String husband_id, 
    // String husband_name, String wife_id, String wife_name, String[] children)

    @Test
    void test1() {
        try {
            GEDCOM.families.add(new Family("1",
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/1950"),
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/1960"),
                    husband1.getId(),husband1.getName(),wife1.getId(),wife1.getName(), null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(husband1.isDivorceBeforeDeath(wife1));
        GEDCOM.families.remove(0);

    }

    @Test
    void test2() {
        try {
            GEDCOM.families.add(new Family("1",
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/1980"),
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/2030"),
                    husband1.getId(),husband1.getName(),wife2.getId(),wife2.getName(), null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertFalse(husband1.isDivorceBeforeDeath(wife2));
        GEDCOM.families.remove(0);

    }

    @Test
    void test3() {
        try {
            GEDCOM.families.add(new Family("1",
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/2019"),
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/2025"),
                    husband2.getId(),husband2.getName(),wife1.getId(),wife1.getName(), null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertFalse(husband2.isDivorceBeforeDeath(wife1));
        GEDCOM.families.remove(0);

    }

    @Test
    void test4() {
        try {
            GEDCOM.families.add(new Family("1",
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/1980"),
                    new SimpleDateFormat("dd/MM/yyyy").parse("23/12/1979"),
                    husband2.getId(),husband2.getName(),wife2.getId(),wife2.getName(), null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertFalse(husband2.isDivorceBeforeDeath(wife2));
        GEDCOM.families.remove(0);

    }



}
