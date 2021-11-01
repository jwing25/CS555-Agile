package Tests;

import GEDCOM.Family;
import GEDCOM.GEDCOM;
import GEDCOM.Individual;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarriageAfterFourteen {
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

            ArrayList<String> spouse3 = new ArrayList<String>();
            spouse3.add("spouse3");

            ArrayList<String> spouse4 = new ArrayList<String>();
            spouse4.add("spouse4");
            husband1 = new Individual("husband1","husband1","Male",
                new SimpleDateFormat("MM/dd/yyyy").parse( "1/13/1989" ),
                32 ,true,null, null, spouse1);

            wife1 = new Individual("wife1","wife1","Female",
                new SimpleDateFormat("MM/dd/yyyy").parse( "3/14/1992" ),
                29 ,true,null, null, spouse2);


            husband2 = new Individual("husband1","husband2","Male",
                new SimpleDateFormat("MM/dd/yyyy").parse( "1/13/1993" ),
                27 ,true,null, null, spouse1);

            wife2 = new Individual("wife1","wife2","Female",
                new SimpleDateFormat("MM/dd/yyyy").parse( "3/14/1985" ),
                36 ,true,null, null, spouse2);

            // System.out.println(husband1.getBirthday());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() {
        //True if married when both are over 14
        try {
            GEDCOM.families.add(new Family("1",
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/2008"),
                    null,
                    husband1.getId(),husband1.getName(),wife1.getId(),wife1.getName(), null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(husband1.marriageAfterFourteen(wife1));
        GEDCOM.families.remove(0);

    }

    @Test
    void test2() {
        //Wife is underage when married
        try {
            GEDCOM.families.add(new Family("1",
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/2004"),
                    null,
                    husband1.getId(),husband1.getName(),wife1.getId(),wife1.getName(), null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertFalse(husband1.marriageAfterFourteen(wife1));
        GEDCOM.families.remove(0);
    }

    @Test
    void test3() {
        //Husband is underage when married
        try {
            GEDCOM.families.add(new Family("1",
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/2005"),
                    null,
                    husband2.getId(),husband2.getName(),wife2.getId(),wife2.getName(), null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertFalse(husband2.marriageAfterFourteen(wife2));
        GEDCOM.families.remove(0);
    }

    @Test
    void test4() {
        try {
            GEDCOM.families.add(new Family("1",
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/2000"),
                    null,
                    husband1.getId(),husband1.getName(),wife1.getId(),wife1.getName(), null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertFalse(husband1.marriageAfterFourteen(wife1));
        GEDCOM.families.remove(0);

    }

    @Test
    void test5() {
        //True if married when both are over 14 for husband2 and wfe2
        try {
            GEDCOM.families.add(new Family("1",
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/2010"),
                    null,
                    husband2.getId(),husband2.getName(),wife2.getId(),wife2.getName(), null));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(husband2.marriageAfterFourteen(wife2));
        GEDCOM.families.remove(0);
    }


}
