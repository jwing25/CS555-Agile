package Tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;

import GEDCOM.GEDCOM;
import GEDCOM.Individual;
import GEDCOM.Family;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AuntsandUncles {
    public static Individual i1;
    public static Individual i2;
    public static Individual i3;
    public static Individual i4;
    public static Individual i5;
    public static Individual i6;
    public static Individual i7;
    public static Individual i8;
    public static Individual i9;
    public static Individual i10;


    public static Family f1;
    public static Family f2;
    public static Family f3;
    public static Family f4;
    public static Family f5;


    @BeforeAll

    // public Individual(String id, String name, String gender, Date birthday, int age, 
    // Boolean is_alive, Date death_date, ArrayList<String> childof, ArrayList<String> spouse)
    public static void init() {
        try {
            ArrayList<String> spouse1 = new ArrayList<String>();
            spouse1.add("I7");
            ArrayList<String> spouse2 = new ArrayList<String>();
            spouse2.add("I8");
            ArrayList<String> spouse3 = new ArrayList<String>();
            spouse3.add("I2");
            ArrayList<String> spouse4 = new ArrayList<String>();
            spouse4.add("I6");
            ArrayList<String> spouse5 = new ArrayList<String>();
            spouse5.add("I1");
            ArrayList<String> spouse6 = new ArrayList<String>();
            spouse6.add("I4");
            ArrayList<String> spouse7 = new ArrayList<String>();
            spouse7.add("I5");
            ArrayList<String> spouse8 = new ArrayList<String>();
            spouse8.add("I3");
            ArrayList<String> spouse9 = new ArrayList<String>();
            spouse9.add("I9");
            ArrayList<String> spouse10 = new ArrayList<String>();
            spouse10.add("I10");

            i1 = new Individual("I1", "individual1", "Male", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                                21, true, null, null, spouse6);                 
            i2 = new Individual("I2", "individual2", "Female", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/1970"), 
                                51, true, null, null, spouse4);
            i3 = new Individual("I3", "individual3", "Female", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                                21, true, null, null, spouse7);
            i4 = new Individual("I4", "individual4", "Female", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/1975"), 
                                45, true, null, null, spouse5);
            i5 = new Individual("I5", "individual5", "Male", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/1975"), 
                                45, true, null, null, spouse8);
            i6 = new Individual("I6", "individual6", "Female", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/1975"), 
                                45, true, null, null, spouse3);
            i7 = new Individual("I7", "individual7", "Male", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/1945"), 
                                75, true, null, null, spouse2);
            i8 = new Individual("I8", "individual8", "Female", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/1945"), 
                                75, true, null, null, spouse1);
            i9 = new Individual("I9", "individual9", "Female", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                                21, true, null, null, spouse10);
            i10 = new Individual("I10", "individual10", "Male", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                                21, true, null, null, spouse9);

            ArrayList<String> f1Children = new ArrayList<>();
            f1Children.add("I2"); //non incest but kids engage in it
            f1Children.add("I4"); //incest
            f1Children.add("I5"); //incest
            ArrayList<String> f2Children = new ArrayList<>();
            f2Children.add("I1");
            f2Children.add("I3");            
            //Highest Family
            f1 = new Family("F1", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/1965"), 
                            null, "I7", "individual7", "I8", "individual8", f1Children);

            // Family who's children marry their aunt or uncle
            f2 = new Family("F2", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/1990"), 
                            null, "I2", "individual2", "I6", "individual6", f2Children);
                        
            // Son who marries Aunt
            f3 = new Family("F3", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                            null, "I1", "individual1", "I4", "individual4", null);
            //Daughter who marries Uncle
            f4 = new Family("F4", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
            null, "I5", "individual5", "I3", "individual3", null);

            f5 = new Family("F5", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
            null, "I10", "individual10", "I9", "individual9", null);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() {
        GEDCOM.individuals.add(i1);
        GEDCOM.individuals.add(i2);
        GEDCOM.individuals.add(i3);
        GEDCOM.individuals.add(i4);
        GEDCOM.individuals.add(i5);
        GEDCOM.individuals.add(i6);
        GEDCOM.individuals.add(i7);
        GEDCOM.individuals.add(i8);
        GEDCOM.individuals.add(i9);
        GEDCOM.individuals.add(i10);

        GEDCOM.families.add(f1);
        GEDCOM.families.add(f2);
        GEDCOM.families.add(f3);


        assertTrue(i1.isUncleOrAunt(i4));

        GEDCOM.individuals.removeAll(GEDCOM.individuals);
        GEDCOM.families.removeAll(GEDCOM.families);

    }

    @Test
    void test2() {
        GEDCOM.individuals.add(i1);
        GEDCOM.individuals.add(i2);
        GEDCOM.individuals.add(i3);
        GEDCOM.individuals.add(i4);
        GEDCOM.individuals.add(i5);
        GEDCOM.individuals.add(i6);
        GEDCOM.individuals.add(i7);
        GEDCOM.individuals.add(i8);
        GEDCOM.individuals.add(i9);
        GEDCOM.individuals.add(i10);

        GEDCOM.families.add(f1);
        GEDCOM.families.add(f2);
        GEDCOM.families.add(f4);

        assertTrue(i5.isUncleOrAunt(i3));

        GEDCOM.individuals.removeAll(GEDCOM.individuals);
        GEDCOM.families.removeAll(GEDCOM.families);

    }

    @Test
    void test3() {
        GEDCOM.individuals.add(i1);
        GEDCOM.individuals.add(i2);
        GEDCOM.individuals.add(i3);
        GEDCOM.individuals.add(i4);
        GEDCOM.individuals.add(i5);
        GEDCOM.individuals.add(i6);
        GEDCOM.individuals.add(i7);
        GEDCOM.individuals.add(i8);
        GEDCOM.individuals.add(i9);
        GEDCOM.individuals.add(i10);

        GEDCOM.families.add(f1);
        GEDCOM.families.add(f2);
        GEDCOM.families.add(f5);

        assertFalse(i10.isUncleOrAunt(i9));

        GEDCOM.individuals.removeAll(GEDCOM.individuals);
        GEDCOM.families.removeAll(GEDCOM.families);
    }



}
