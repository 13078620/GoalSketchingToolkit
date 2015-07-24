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
 * @author Chris
 */
public class ANDentailmentTest extends TestCase {

    Goal g;
    ANDentailment ae;
    ORentailment oe;
    GoalOrientedProposition gop;
    OperationalizingProducts ops;
    AssumptionTermination at;
    ArrayList<GSnode> c;

    public ANDentailmentTest(String testName) {
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
     * Test of addChild method, of class ANDentailment.
     */
    public void testAddChild() {
        System.out.println("addChild");
        setUp();
        try {
            ae.addChild(ops);
        } catch (Exception e) {
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
        }

        tearDown();
        setUp();

        ae.addChild(g);
        ae.addChild(new Goal());
        ae.addChild(new Goal());

        assertEquals(3, ae.getChildren().size());

        tearDown();
        setUp();
        
        GoalOrientedProposition gop1 = new GoalOrientedProposition(GoalType.ASSUMPTION, "Test", "testAgain");
        GoalOrientedProposition gop2 = new GoalOrientedProposition(GoalType.BEHAVIOUR, "foo", "bar");
        gop.setPrefix(GoalType.ASSUMPTION);
        
        Goal g2 = new Goal();
        Goal g3 = new Goal();
        
        g.addChild(gop);
        g2.addChild(gop1);
        g3.addChild(gop2);
        
        g.addChild(ae);
        
        ae.addChild(g2);
        
        assertEquals(1, ae.getChildren().size());
        
        try {
            ae.addChild(g3);
        } catch (Exception e) {            
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
            assertTrue(e.getMessage().equalsIgnoreCase("Can only add assumption goals as children "
                                + "to a assumption goals"));
        }
        

    }

    /**
     * Test of removeChild method, of class ANDentailment.
     */
    public void testRemoveChild() {
        System.out.println("removeChild");
        Goal g2 = new Goal();
        ae.addChild(g2);
        assertEquals(1, ae.getChildren().size());
        ae.removeChild(g2);
        assertEquals(0, ae.getChildren().size());

    }

    /**
     * Test of setChildren method, of class ANDentailment.
     */
    public void testSetChildren() {
        System.out.println("setChildren");
        c.add(g);
        c.add(at);
        setUp();
        try {
            ae.setChildren(c);
        } catch (Exception e) {
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
        }

        tearDown();
        setUp();

        c.add(g);
        c.add(new Goal());

        ae.setChildren(c);

        assertTrue(ae.isParent());
    }

}
