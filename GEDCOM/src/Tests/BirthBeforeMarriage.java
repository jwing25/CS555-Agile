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

public class BirthBeforeMarriage {
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


            Dad = new Individual("husband1","husband1","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "1/13/1989" ),
                    32 ,true,null, children, spouse1);

            Mom = new Individual("wife1","wife1","Female",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "3/14/1992" ),
                    29 ,true,null, children, spouse2);
            GEDCOM.families.add(new Family("1",
                    new SimpleDateFormat("MM/dd/yyyy").parse("12/02/2015"),new SimpleDateFormat("MM/dd/yyyy").parse("12/06/2023"),
                    Dad.getId(),Dad.getName(),Mom.getId(),Mom.getName(), null));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() {
        // Children are born after marriage
        try {
            Sibling1 = new Individual("sibling1","sibling1","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "12/16/2017" ),
                    4 ,true,null, null, null);
            Sibling2 = new Individual("Sibling2","Sibling2","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "4/12/2019" ),
                    2 ,true,null, null, null);
            Sibling3 = new Individual("Sibling3","Sibling3","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "9/29/2021" ),
                    0 ,true,null, null, null);
  
            List<String> siblingNames = Arrays.asList(Sibling1.getName(),Sibling2.getName(), Sibling3.getName());
            GEDCOM.individuals.addAll(Arrays.asList(Sibling1,Sibling2, Sibling3));
            GEDCOM.families.get(0).setChildren(new ArrayList<String>(siblingNames));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertFalse(Dad.isBirthBeforeMarriage(Mom));
        GEDCOM.families.get(0).getChildren().clear();
        GEDCOM.individuals.clear();
    }

    @Test
    void test2() {
        //Child born before wedding date
        
        try {
            Sibling1 = new Individual("sibling1","sibling1","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "12/16/2013" ),
                    4 ,true,null, null, null);
            Sibling2 = new Individual("Sibling2","Sibling2","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "4/12/2019" ),
                    2 ,true,null, null, null);
            Sibling3 = new Individual("Sibling3","Sibling3","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "9/29/2021" ),
                    0 ,true,null, null, null);
  
            List<String> siblingNames = Arrays.asList(Sibling1.getName(),Sibling2.getName(), Sibling3.getName());
            GEDCOM.individuals.addAll(Arrays.asList(Sibling1,Sibling2, Sibling3));
            GEDCOM.families.get(0).setChildren(new ArrayList<String>(siblingNames));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(Dad.isBirthBeforeMarriage(Mom));
        GEDCOM.families.get(0).getChildren().clear();
        GEDCOM.individuals.clear();
    }

    @Test
    void test3() {
        //Child born within 9 months of Divorce Date
        
        try {
            Sibling1 = new Individual("sibling1","sibling1","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "4/16/2024" ),
                    4 ,true,null, null, null);
            Sibling2 = new Individual("Sibling2","Sibling2","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "4/12/2019" ),
                    2 ,true,null, null, null);
            Sibling3 = new Individual("Sibling3","Sibling3","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "9/29/2021" ),
                    0 ,true,null, null, null);
  
            List<String> siblingNames = Arrays.asList(Sibling1.getName(),Sibling2.getName(), Sibling3.getName());
            GEDCOM.individuals.addAll(Arrays.asList(Sibling1,Sibling2, Sibling3));
            GEDCOM.families.get(0).setChildren(new ArrayList<String>(siblingNames));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertFalse(Dad.isBirthBeforeMarriage(Mom));
        GEDCOM.families.get(0).getChildren().clear();
        GEDCOM.individuals.clear();
    }

    @Test
    void test4() {
        // Child born after 9 months of Divorce Date
        try {
            Sibling1 = new Individual("sibling1","sibling1","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "4/16/2018" ),
                    4 ,true,null, null, null);
            Sibling2 = new Individual("Sibling2","Sibling2","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "11/12/2024" ),
                    2 ,true,null, null, null);
            Sibling3 = new Individual("Sibling3","Sibling3","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "9/29/2021" ),
                    0 ,true,null, null, null);
  
            List<String> siblingNames = Arrays.asList(Sibling1.getName(),Sibling2.getName(), Sibling3.getName());
            GEDCOM.individuals.addAll(Arrays.asList(Sibling1,Sibling2, Sibling3));
            GEDCOM.families.get(0).setChildren(new ArrayList<String>(siblingNames));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(Dad.isBirthBeforeMarriage(Mom));
        GEDCOM.families.get(0).getChildren().clear();
        GEDCOM.individuals.clear();

    }

}
