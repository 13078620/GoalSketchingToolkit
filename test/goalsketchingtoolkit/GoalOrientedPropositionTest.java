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
public class GoalOrientedPropositionTest extends TestCase {

    Goal g;
    OperationalizingProducts ops;
    GoalOrientedProposition gop;
    Annotation a;
    ArrayList<GSnode> c;

    public GoalOrientedPropositionTest(String testName) {
        super(testName);
    }

    @Override
    public void setUp() {
        g = new Goal();
        gop = new GoalOrientedProposition("Test gop");
        ops = new OperationalizingProducts();
        a = new Annotation();
        c = new ArrayList();
    }

    @Override
    public void tearDown() {
        g = null;
        gop = null;
        ops = null;
        a = null;
        c = null;
    }

    /**
     * Test of addChild method, of class GoalOrientedProposition.
     */
    public void testAddChild() {
        System.out.println("addChild");
        setUp();

        try {
            gop.addChild(ops);
        } catch (Exception e) {
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
        }

        tearDown();
        setUp();

        gop.addChild(a);

        assertEquals(1, gop.getChildren().size());

        tearDown();
        setUp();

        try {
            gop.addChild(g);
        } catch (Exception e) {
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
        }

    }

    /**
     * Test of removeChild method, of class GoalOrientedProposition.
     */
    public void testRemoveChild() {
        System.out.println("removeChild");
        setUp();

        gop.addChild(a);
        assertEquals(1, gop.getChildren().size());
        assertTrue(gop.hasChildren);
        gop.removeChild(a);
        assertTrue(gop.getChildren().isEmpty());
        assertFalse(gop.hasChildren);

    }

    /**
     * Test of setChildren method, of class GoalOrientedProposition.
     */
    public void testSetChildren() {
        System.out.println("setChildren");

        setUp();

        c.add(g);
        c.add(ops);
        c.add(a);

        try {
            gop.setChildren(c);
        } catch (Exception e) {
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
        }

        tearDown();
        setUp();
        c.add(a);
        c.add(new Annotation());
        c.add(new Annotation());
        
        gop.setChildren(c);

        assertEquals(3, gop.getChildren().size());

    }

    /**
     * Test of setPrefix method, of class GoalOrientedProposition.
     */
    public void testSetPrefix() {
        System.out.println("setPrefix");
        tearDown();
        setUp();
        g.addChild(ops);
        g.addChild(gop);
        
        try {
            gop.setPrefix(GoalType.ASSUMPTION);
        } catch (Exception e) {
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
        }
        
        gop.setPrefix(GoalType.BEHAVIOUR);;
        assertEquals("/b/", gop.getPrefix());
        
    }

}
