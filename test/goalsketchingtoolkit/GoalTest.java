/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import junit.framework.TestCase;

/**
 *
 * @author Chris
 */
public class GoalTest extends TestCase {

    Goal g;
    ANDentailment ae;
    ORentailment oe;
    GoalOrientedProposition gop;
    OperationalizingProducts ops;
    AssumptionTermination at;

    public GoalTest(String testName) {
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
    }

    @Override
    public void tearDown() {
        g = null;
        ae = null;
        oe = null;
        gop = null;
        ops = null;
        at = null;
    }

    /**
     * Test of addChild method, of class Goal.
     */
    public void testAddChild() {
        System.out.println("GoalTest: test method 1 - addChild()");
        setUp();
        g.addChild(ae);
        assertEquals(1, g.getChildren().size());
        g.addChild(gop);
        assertEquals(2, g.getChildren().size());

    }

    /**
     * Test of adding a goal object to another.
     */
    public void testAddGoalToGoal() {
        System.out.println("GoalTest: test adding a goal to a goal");
        setUp();
        try {
            g.addChild(new Goal());
        } catch (Exception e) {
            assertTrue((e).getClass().toString().contains("UnsupportedOperationException"));
            assertTrue((e).getMessage().equalsIgnoreCase("Cannot add goal to a goal, "
                    + "goals are added to a semantic entailment only"));
        }
    }

    /**
     * Test of removeChild method, of class Goal.
     */
    public void testRemoveChild() {
        System.out.println("GoalTest: test method 2 - removeChild()");
        setUp();
        g.addChild(ae);
        g.addChild(gop);
        g.removeChild(gop);
        assertEquals(1, g.getChildren().size());

    }

    /**
     * Test of setParent method, of class Goal.
     */
    public void testSetParent() {
        System.out.println("GoalTest: test method 3 - setParent()");
        setUp();
        g.addChild(ops);
        assertTrue(g.isParent());

    }

    /**
     * Test of setIsRootNode method, of class Goal.
     */
    public void testSetIsRootGoal() {
        System.out.println("GoalTest: test method 4 - setIsRootGoal()");
        g.setIsRootGoal(true);
        assertTrue(g.isRootGoal());
    }

    /**
     * Test of isRootNode method, of class Goal.
     */
    public void testIsRootGoal() {
        System.out.println("GoalTest: test method 5 - isRootNode()");
        g.setIsRootGoal(true);
        assertTrue(g.isRootGoal());
    }

    /**
     * Test of equals method, of class Goal.
     */
    public void testEquals() {
        System.out.println("GoalTest: test method 6 - equals()");
        Goal g2 = new Goal();

        g.setID("GA");
        g2.setID("GA1");
        assertFalse(g.equals(g2));

        Goal g3 = new Goal();
        g3.setID("GA1");

        assertTrue(g2.equals(g3));

    }

}
