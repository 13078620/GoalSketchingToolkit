/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import junit.framework.TestCase;
import java.util.ArrayList;

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

        try {
            g.addChild(new ANDentailment());
        } catch (Exception e) {
            assertTrue((e).getClass().toString().contains("UnsupportedOperationException"));
            assertTrue((e).getMessage().contains("This goal already has"));
        }

        try {
            g.addChild(new ORentailment());
        } catch (Exception e) {
            assertTrue((e).getClass().toString().contains("UnsupportedOperationException"));
            assertTrue((e).getMessage().equalsIgnoreCase("This goal is already entailed"));
        }

        try {
            g.addChild(new GoalOrientedProposition("Test"));
        } catch (Exception e) {
            assertTrue((e).getClass().toString().contains("UnsupportedOperationException"));
            assertTrue((e).getMessage().contains("This goal already has:"));
        }

        try {
            g.addChild(ops);
        } catch (Exception e) {
            assertTrue((e).getClass().toString().contains("UnsupportedOperationException"));
            assertTrue((e).getMessage().contains("This goal is already entailed"));
        }

        try {
            g.addChild(at);
        } catch (Exception e) {
            assertTrue((e).getClass().toString().contains("UnsupportedOperationException"));
            assertTrue((e).getMessage().contains("This goal is already entailed"));
        }

        tearDown();
        setUp();

        GoalOrientedProposition prop = new GoalOrientedProposition("TestGOP");
        prop.setPrefix(GoalType.ASSUMPTION);
        g.addChild(prop);

        try {
            g.addChild(ops);
        } catch (Exception e) {
            assertTrue((e).getClass().toString().contains("UnsupportedOperationException"));
            assertTrue((e).getMessage().contains("Cannot add "
                    + "operationalizing products to assumptions"));
        }

        tearDown();
        setUp();

        g.addChild(ops);

        try {
            g.addChild(prop);
        } catch (Exception e) {
            assertTrue((e).getClass().toString().contains("UnsupportedOperationException"));
            assertTrue((e).getMessage().contains("Cannot add an "
                    + "assumption goal oriented proposition"
                    + " to this goal because it is already "
                    + "operationalised"));
        }

        tearDown();
        setUp();

        g.addChild(at);
        GoalOrientedProposition prop2 = new GoalOrientedProposition("TestGOP2");
        prop.setPrefix(GoalType.BEHAVIOUR);
        g.addChild(prop2);
        assertEquals(2, g.getChildren().size());

        tearDown();
        setUp();

        gop.setPrefix(GoalType.BEHAVIOUR);
        g.addChild(gop);
        g.addChild(at);
        g.addChild(ops);
        assertEquals(3, g.getChildren().size());

        tearDown();
        setUp();

        g.addChild(prop2);
        g.addChild(ops);
        prop2.setPrefix(GoalType.BEHAVIOUR);

        assertEquals(2, g.getChildren().size());

        tearDown();
        setUp();

        g.setIsRootGoal(true);
        gop.setPrefix(GoalType.OBSTACLE);

        try {
            g.addChild(gop);
        } catch (Exception e) {
            assertTrue((e).getClass().toString().contains("UnsupportedOperationException"));
        }
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
        //tearDown();
    }

    /**
     * Test of adding operationalizing products and assumption termination to
     * goal.
     */
    public void testCreateGoalWithOperationalizingProducts() {
        System.out.println("GoalTest: test adding operationalizing products to a goal");
        tearDown();
        setUp();
        g.addChild(gop);
        g.addChild(ops);
        g.addChild(at);
        assertEquals(3, g.getChildren().size());
    }

    /**
     * Test of adding other goal types to assumptions. //use in entailment test
     */
    /*public void testAddOtherGOPtypeToAssumption() {
     System.out.println("GoalTest: test adding other goal types to assumptions");
     tearDown();
     setUp();
     GoalOrientedProposition gop2 = new GoalOrientedProposition("Test gop2");
     gop.setPrefix(GoalType.ASSUMPTION);
     gop2.setPrefix(GoalType.BEHAVIOUR);
     g.addChild(gop);
     g.addChild(ops);
     g.addChild(at);
     assertEquals(3, g.getChildren().size());
     }*/
    /**
     * Test of removeChild method, of class Goal.
     */
    public void testRemoveChild() {
        System.out.println("GoalTest: test method 2 - removeChild()");
        tearDown();
        setUp();
        g.addChild(ae);
        g.addChild(gop);
        g.removeChild(gop);
        assertEquals(1, g.getChildren().size());
        assertFalse(g.hasGop());
        tearDown();
        setUp();
        g.addChild(gop);
        g.addChild(ops);
        g.addChild(at);
        assertTrue(g.hasGop());
        assertTrue(g.isOperationalized());
        assertTrue(g.isTerminated());
        g.removeChild(gop);
        assertFalse(g.hasGop());
        g.removeChild(ops);
        assertFalse(g.isOperationalized());
        g.removeChild(at);
        assertFalse(g.isTerminated());

    }

    /**
     * Test of get proposition method, of class goal.
     */
    public void testGetProposition() {

        setUp();
        g.addChild(gop);
        GoalOrientedProposition retrievedGop = g.getProposition();

        assertFalse(retrievedGop == null);

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

    public void testSetChildren() {
        setUp();
        ArrayList<GSnode> c = new ArrayList();
        c.add(g);
        c.add(oe);
        try {
            g.setChildren(c);
        } catch (Exception e) {
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
        }
    }

    /**
     * Test of setIsRootNode method, of class Goal.
     */
    public void testSetIsRootGoal() {
        System.out.println("GoalTest: test method 4 - setIsRootGoal()");
        g.setIsRootGoal(true);
        assertTrue(g.isRootGoal());

        tearDown();
        setUp();

        gop.setPrefix(GoalType.OBSTACLE);

        try {
            g.addChild(gop);
        } catch (Exception e) {
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
        }

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
     * Test of isRootNode method, of class Goal.
     */
    public void testHasGraphics() {
        System.out.println("GoalTest: test method 6 - hasGraphics()");
        
        assertFalse(g.hasGraphics());        
        g.setGraphicalProperties(new GSnodeGraphics(2,1,10,20));
        assertTrue(g.hasGraphics()); 
        
    }
    
    /**
     * Test of equals method, of class Goal.
     */
    /*public void testEquals() {
     System.out.println("GoalTest: test method 6 - equals()");
     Goal g2 = new Goal();

     g.setID("GA");
     g2.setID("GA1");
     assertFalse(g.equals(g2));

     Goal g3 = new Goal();
     g3.setID("GA1");

     assertTrue(g2.equals(g3));

     }*/
}
