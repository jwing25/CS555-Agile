package Tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import GEDCOM.Family;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import GEDCOM.GEDCOM;
import GEDCOM.Individual;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BirthBeforeDeathOfParentsTest {
    public static Individual i1;
    public static Individual dad1;
    public static Individual dad2;
    public static Individual mom1;
    public static Individual mom2;

    @BeforeAll
    public static void init() {
        try {
            // same day
            i1 = new Individual("I1", "individual1", "M",
                    new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"),
                    21, true, null,
                    new String[]{"individual1"}, null);

            dad1 = new Individual("husband1","husband1","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null,new String[]{"individual1"},new String[1]);

            dad2 = new Individual("123", "Justin", "Male",
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/03/1990"),
                    71, false,
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/04/1990"),
                    new String[]{"individual1"}, new String[1]);

            mom1 = new Individual("wife1","wife1","Female",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1953" ),
                    74 ,true,null,new String[]{"individual1"},new String[1]);

            mom2 = new Individual("456", "Queen Elizabeth", "Female",
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/03/1990"),
                    80, false,
                    new SimpleDateFormat("dd/MM/yyyy").parse("13/03/1991"),
                    new String[]{"individual1"}, new String[3]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() {
        // valid dates, both alive
        GEDCOM.individuals.add(dad1);
        GEDCOM.individuals.add(mom1);
        GEDCOM.families.add(new Family("1",
                null,
                null,
                dad1.getId(),dad1.getName(),mom1.getId(),mom1.getName(), new String[]{"individual1"}));
        assertTrue(i1.isBirthBeforeDeathOfParents());
        GEDCOM.families.remove(0);
        GEDCOM.individuals.remove(0);
        GEDCOM.individuals.remove(0);

    }

    @Test
    void test2() {
        // mom died day after, dad alive
        try {
            i1.setBirthday(new SimpleDateFormat("MM/dd/yyyy").parse( "02/13/1991" ));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GEDCOM.individuals.add(dad1);
        GEDCOM.individuals.add(mom2);
        GEDCOM.families.add(new Family("1",
                null,
                null,
                dad1.getId(),dad1.getName(),mom2.getId(),mom2.getName(), new String[]{"individual1"}));
        assertTrue(i1.isBirthBeforeDeathOfParents());
        GEDCOM.families.remove(0);
        GEDCOM.individuals.remove(0);
        GEDCOM.individuals.remove(0);
    }

    @Test
    void test3() {
        // mom alive, dad died 3 months prior to birth

        try {
            i1.setBirthday(new SimpleDateFormat("MM/dd/yyyy").parse( "01/13/1990" ));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        GEDCOM.individuals.add(dad2);
        GEDCOM.individuals.add(mom1);
        GEDCOM.families.add(new Family("1",
                null,
                null,
                dad2.getId(),dad2.getName(),mom1.getId(),mom1.getName(), new String[]{"individual1"}));
        assertFalse(i1.isBirthBeforeDeathOfParents());
        GEDCOM.families.remove(0);
        GEDCOM.individuals.remove(0);
        GEDCOM.individuals.remove(0);
    }

    @Test
    void test4() {
        // mom alive, dad died 12 months after to birth
        try {
            i1.setBirthday(new SimpleDateFormat("MM/dd/yyyy").parse( "01/13/1990" ));
            dad2.setDeath(new SimpleDateFormat("MM/dd/yyyy").parse( "01/13/1991" ));
            mom1.setDeath(new SimpleDateFormat("MM/dd/yyyy").parse( "01/14/1990" ));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GEDCOM.individuals.add(dad2);
        GEDCOM.individuals.add(mom2);
        GEDCOM.families.add(new Family("1",
                null,
                null,
                dad2.getId(),dad2.getName(),mom2.getId(),mom2.getName(), new String[]{"individual1"}));
        assertTrue(i1.isBirthBeforeDeathOfParents());
        GEDCOM.families.remove(0);
        GEDCOM.individuals.remove(0);
        GEDCOM.individuals.remove(0);
    }
}
