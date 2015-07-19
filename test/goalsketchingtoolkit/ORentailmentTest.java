/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;
import junit.framework.TestCase;

/**
 *
 * @author Chris Berryman.
 */
public class ORentailmentTest extends TestCase {

    Goal g;
    ANDentailment ae;
    ORentailment oe;
    GoalOrientedProposition gop;
    OperationalizingProducts ops;
    AssumptionTermination at;
    ArrayList<GSnode> c;

    public ORentailmentTest(String testName) {
        super(testName);
    }

    /**
     *
     */
    @Override
    public void setUp() {
        g = new Goal();
        ae = new ANDentailment();
        oe = new ORentailment();
        gop = new GoalOrientedProposition("Test gop");
        ops = new OperationalizingProducts();
        at = new AssumptionTermination();
        c = new ArrayList();
    }

    @Override
    public void tearDown() {
        g = null;
        ae = null;
        oe = null;
        gop = null;
        ops = null;
        at = null;
        c = null;
    }

    /**
     * Test of addChild method, of class ORentailment.
     */
    public void testAddChild() {
        System.out.println("addChild");
        setUp();
        try {
            oe.addChild(ops);
        } catch (Exception e) {
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
        }

        tearDown();
        setUp();

        oe.addChild(g);
        oe.addChild(new Goal());
        assertEquals(2, oe.getChildren().size());

        try {
            oe.addChild(new Goal());
        } catch (Exception e) {
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
        }

    }

    /**
     * Test of setChildren method, of class ORentailment.
     */
    public void testSetChildren() {
        System.out.println("setChildren");
        setUp();
        c.add(g);
        c.add(at);
        try {
            oe.setChildren(c);
        } catch (Exception e) {
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
        }

        tearDown();
        setUp();

        c.add(g);
        c.add(new Goal());

        oe.setChildren(c);

        assertTrue(oe.isParent());

        tearDown();
        setUp();
        
        c.add(g);
        c.add(new Goal());
        c.add(new Goal());
        try {
            oe.setChildren(c);
        } catch (Exception e) {
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
        }

    }


}
