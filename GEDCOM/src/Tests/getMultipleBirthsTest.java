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

public class getMultipleBirthsTest {
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
            Sibling2 = new Individual("sibling2","Sibling2","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            Sibling3 = new Individual("sibling3","Sibling3","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            Sibling4 = new Individual("sibling4","Sibling4","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            List<String> siblingNames = Arrays.asList(Sibling1.getId(),Sibling2.getId(), Sibling3.getId(),Sibling4.getId());
            System.out.println(siblingNames);
            GEDCOM.individuals.addAll(Arrays.asList(Sibling1,Sibling2, Sibling3,Sibling4));
            GEDCOM.families.get(0).setChildren(new ArrayList<String>(siblingNames));
            ArrayList<String> multipleBirthsList = GEDCOM.getMultipleBirths();
            System.out.println(multipleBirthsList);
            assertTrue(multipleBirthsList.contains("sibling1") && multipleBirthsList.contains("sibling2") && multipleBirthsList.contains("sibling3") && multipleBirthsList.contains("sibling4"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        GEDCOM.families.get(0).getChildren().clear();
        GEDCOM.individuals.clear();
    }

    @Test
    void test2() {
        // 4/5 Siblings are all on the same day
        try {
            Sibling1 = new Individual("sibling1","sibling1","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1908" ),
                    98 ,true,null, null, null);
            Sibling2 = new Individual("sibling2","Sibling2","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            Sibling3 = new Individual("sibling3","Sibling3","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            Sibling4 = new Individual("sibling4","Sibling4","Male",
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
            List<String> siblingNames = Arrays.asList(Sibling1.getId(),Sibling2.getId(), Sibling3.getId(),Sibling4.getId());
            System.out.println(siblingNames);
            GEDCOM.individuals.addAll(Arrays.asList(Sibling1,Sibling2, Sibling3,Sibling4));
            GEDCOM.families.get(0).setChildren(new ArrayList<String>(siblingNames));
            ArrayList<String> multipleBirthsList = GEDCOM.getMultipleBirths();
            System.out.println(multipleBirthsList);
            assertTrue(!multipleBirthsList.contains("sibling1") && multipleBirthsList.contains("sibling2") && multipleBirthsList.contains("sibling3") && multipleBirthsList.contains("sibling4"));

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
                    new SimpleDateFormat("MM/dd/yyyy").parse( "8/16/1923" ),
                    98 ,true,null, null, null);
    
            List<String> siblingNames = Arrays.asList(Sibling1.getId());
            GEDCOM.individuals.addAll(Arrays.asList(Sibling1));
            GEDCOM.families.get(0).setChildren(new ArrayList<String>(siblingNames));
            ArrayList<String> multipleBirthsList = GEDCOM.getMultipleBirths();
            System.out.println(multipleBirthsList);
            assertTrue(multipleBirthsList.isEmpty());


        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertTrue(Sibling1.isMultipleBirth());
        GEDCOM.families.get(0).getChildren().clear();
        GEDCOM.individuals.clear();
    }

 



}
