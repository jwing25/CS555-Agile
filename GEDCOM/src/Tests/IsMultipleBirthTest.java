package Tests;

import GEDCOM.GEDCOM;
import GEDCOM.Individual;
import GEDCOM.Family;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsMultipleBirthTest {
    public static Individual Mom;
    public static Individual Dad;
    public static Individual Sibling1;
    public static Individual Sibling2;
    public static Individual Sibling3;
    public static Individual Sibling4;
    public static Individual Sibling5;
    static ArrayList<String> children = new ArrayList<String>();

    @BeforeAll
    public static void init() {
        try {
            ArrayList<String> spouse1 = new ArrayList<String>();
            spouse1.add("wife1");

            ArrayList<String> spouse2 = new ArrayList<String>();
            spouse2.add("husband1");


            Dad = new Individual("husband1","husband1","M",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, children, spouse1);

            Mom = new Individual("wife1","wife1","F",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1953" ),
                    74 ,true,null, children, spouse2);
            GEDCOM.families.add(new Family("1",
                    new SimpleDateFormat("dd/MM/yyyy").parse("12/02/1950"),null,
                    Dad.getId(),Dad.getName(),Mom.getId(),Mom.getName(), null));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() {
        // Siblings are all on the same day
        try {
            Sibling1 = new Individual("sibling1","sibling1","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            Sibling2 = new Individual("Sibling2","Sibling2","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            Sibling3 = new Individual("Sibling3","Sibling3","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            Sibling4 = new Individual("Sibling4","Sibling4","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            Sibling5 = new Individual("Sibling5","Sibling5","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            List<String> siblingNames = Arrays.asList(Sibling1.getName(),Sibling2.getName(), Sibling3.getName(),Sibling4.getName(),Sibling5.getName());
            GEDCOM.individuals.addAll(Arrays.asList(Sibling1,Sibling2, Sibling3,Sibling4,Sibling5));
            GEDCOM.families.get(0).setChildren(new ArrayList<String>(siblingNames));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertFalse(Sibling1.isMultipleBirth());
        GEDCOM.families.get(0).getChildren().clear();
        GEDCOM.individuals.clear();
    }

    @Test
    void test2() {
        // 4/5 Siblings are all on the same day
        try {
            Sibling1 = new Individual("sibling1","sibling1","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/26/1923" ),
                    98 ,true,null, null, null);
            Sibling2 = new Individual("Sibling2","Sibling2","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            Sibling3 = new Individual("Sibling3","Sibling3","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            Sibling4 = new Individual("Sibling4","Sibling4","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            Sibling5 = new Individual("Sibling5","Sibling5","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            List<String> siblingNames = Arrays.asList(Sibling1.getName(),Sibling2.getName(), Sibling3.getName(),Sibling4.getName(),Sibling5.getName());
            GEDCOM.individuals.addAll(Arrays.asList(Sibling1,Sibling2, Sibling3,Sibling4,Sibling5));
            GEDCOM.families.get(0).setChildren(new ArrayList<String>(siblingNames));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(Sibling1.isMultipleBirth());
        GEDCOM.families.get(0).getChildren().clear();
        GEDCOM.individuals.clear();

    }

    @Test
    void test3() {
        // Single Child
        try {
            Sibling1 = new Individual("sibling1","sibling1","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/26/1923" ),
                    98 ,true,null, null, null);
            List<String> siblingNames = Arrays.asList(Sibling1.getName());
            GEDCOM.individuals.addAll(Arrays.asList(Sibling1));
            GEDCOM.families.get(0).setChildren(new ArrayList<String>(siblingNames));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(Sibling1.isMultipleBirth());
        GEDCOM.families.get(0).getChildren().clear();
        GEDCOM.individuals.clear();
    }

    @Test
    void test4() {
        // 3/5 born on same day
        try {
            Sibling1 = new Individual("sibling1","sibling1","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/26/1923" ),
                    98 ,true,null, null, null);
            Sibling2 = new Individual("Sibling2","Sibling2","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/26/1923" ),
                    98 ,true,null, null, null);
            Sibling3 = new Individual("Sibling3","Sibling3","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/26/1923" ),
                    98 ,true,null, null, null);
            Sibling4 = new Individual("Sibling4","Sibling4","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            Sibling5 = new Individual("Sibling5","Sibling5","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            List<String> siblingNames = Arrays.asList(Sibling1.getName(),Sibling2.getName(), Sibling3.getName(),Sibling4.getName(),Sibling5.getName());
            GEDCOM.individuals.addAll(Arrays.asList(Sibling1,Sibling2, Sibling3,Sibling4,Sibling5));
            GEDCOM.families.get(0).setChildren(new ArrayList<String>(siblingNames));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(Sibling1.isMultipleBirth());
        GEDCOM.families.get(0).getChildren().clear();
        GEDCOM.individuals.clear();

    }



}
