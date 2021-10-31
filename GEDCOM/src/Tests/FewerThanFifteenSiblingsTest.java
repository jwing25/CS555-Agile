package Tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import GEDCOM.GEDCOM;
import GEDCOM.Individual;
import GEDCOM.Family;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FewerThanFifteenSiblingsTest {
    public static Family f1;
    public static Family f2;
    public static Family f3;
    public static Family f4;
    public static Family f5;
    public static Family f6;

    @BeforeAll
    public static void init() {
        try {
            // 5 children
            f1 = new Family("F1", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                            null, "I1", "individual1", "I2", "individual2", new ArrayList<String>());
            for (int i = 0; i < 5; i++) {
                f1.addChild("I" + Integer.toString(i));
            }

            // Null list
            f2 = new Family("F2", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                            null, "I3", "individual1", "I4", "individual2", null);

            // Empty list
            f3 = new Family("F2", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
                            null, "I1", "individual1", "I4", "individual2", new ArrayList<String>());
                        
            // 14 Children
            f4 = new Family("1", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
            null, "I1", "individual1", "I4", "individual2", new ArrayList<String>());
            for (int i = 0; i < 14; i++) {
                f4.addChild("I" + Integer.toString(i));
            }

            // 15 Children
            f5 = new Family("1", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
            null, "I1", "individual1", "I4", "individual2", new ArrayList<String>());
            for (int i = 0; i < 15; i++) {
                f5.addChild("I" + Integer.toString(i));
            }

            // 20 Children
            f6 = new Family("1", new SimpleDateFormat("MM/dd/yyyy").parse("1/1/2000"), 
            null, "I1", "individual1", "I4", "individual2", new ArrayList<String>());
            for (int i = 0; i < 20; i++) {
                f6.addChild("I" + Integer.toString(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test1() {
        assertTrue(f1.fewerThanFifteenSiblings());
    }

    @Test
    void test2() {
        assertTrue(f2.fewerThanFifteenSiblings());
    }

    @Test
    void test3() {
        assertTrue(f3.fewerThanFifteenSiblings());
    }

    @Test
    void test4() {
        assertTrue(f4.fewerThanFifteenSiblings());
    }

    @Test
    void test5() {
        assertFalse(f5.fewerThanFifteenSiblings());
    }

    @Test
    void test6() {
        assertFalse(f6.fewerThanFifteenSiblings());
    }

}
