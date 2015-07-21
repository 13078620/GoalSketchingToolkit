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
    
    ANDentailment ae;
    
    ConfidenceFactorRating cfr;
    ConfidenceFactorRating cfr2;
    ConfidenceFactorRating cfr3;
    SignificanceFactorRating sfr;
    SignificanceFactorRating sfr2;
    GoalJudgement gj;
    LeafJudgement lj;
    
    public GoalOrientedPropositionTest(String testName) {
        super(testName);
    }
    
    @Override
    public void setUp() {
        g = new Goal();
        gop = new GoalOrientedProposition("Test gop");
        ops = new OperationalizingProducts();
        
        ae = new ANDentailment();
        
        cfr = new ConfidenceFactorRating(ConfidenceFactor.Refine, GSordinalScale.HIGH);
        cfr2 = new ConfidenceFactorRating(ConfidenceFactor.Engage, GSordinalScale.HIGH);
        sfr = new SignificanceFactorRating(SignificanceFactor.Value, 100);
        gj = new GoalJudgement(cfr, cfr2, sfr);
        
        cfr3 = new ConfidenceFactorRating(ConfidenceFactor.Achieve, GSordinalScale.HIGH);
        sfr2 = new SignificanceFactorRating(SignificanceFactor.Cost, 100);
        lj = new LeafJudgement(cfr3, sfr2);
        
        a = new Annotation(gj);
        c = new ArrayList();
    }
    
    @Override
    public void tearDown() {
        g = null;
        gop = null;
        ops = null;
        
        ae = null;
        
        cfr = null;
        cfr2 = null;
        sfr = null;
        gj = null;
        lj = null;
        
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
        
        tearDown();
        setUp();
        
        g.addChild(ae);
        g.addChild(gop);
        a.setJudgement(lj);
        
        assertTrue(gop.isChild());
        assertTrue(g.isEntailed());
        
        try {
            gop.addChild(a);
        } catch (Exception e) {
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
            assertTrue(e.getMessage().contains("Only annotations with goal judgements can be added to GOPs with parent goals"));
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
        c.add(new Annotation(gj));
        c.add(new Annotation(gj));
        
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
        
        GoalOrientedProposition gop1 = new GoalOrientedProposition(GoalType.ASSUMPTION, "Test", "testAgain");
        GoalOrientedProposition gop2 = new GoalOrientedProposition(GoalType.ASSUMPTION, "foo", "bar");
        
        Goal g2 = new Goal();
        Goal g3 = new Goal();
        
        g.addChild(ops);
        g.addChild(gop);
        
        try {
            gop.setPrefix(GoalType.ASSUMPTION);
        } catch (Exception e) {
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
        }
        
        gop.setPrefix(GoalType.BEHAVIOUR);;
        assertEquals("/b/", gop.getPrefix());
        
        tearDown();
        setUp();
        
        gop.setPrefix(GoalType.ASSUMPTION);
        
        g.addChild(ae);
        ae.addChild(g2);
        ae.addChild(g3);
        g.addChild(gop);
        g2.addChild(gop1);
        g3.addChild(gop2);
        
        try {
            gop1.setPrefix(GoalType.BEHAVIOUR);
        } catch (Exception e) {
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
        }
        
        tearDown();
        setUp();
        
        g.setIsRootGoal(true);
        gop.setPrefix(GoalType.MOTIVATION);
        g.addChild(gop);
        
        try {
            gop.setPrefix(GoalType.BEHAVIOUR);
        } catch (Exception e) {            
            assertTrue(e.getClass().toString().contains("UnsupportedOperationException"));
            assertTrue(e.getMessage().equalsIgnoreCase("The goal this proposition belongs to is "
                    + "the root goal, cannot set goal type as anything other than motivation"));
        }

            //assertTrue(gop.getPrefix().equalsIgnoreCase("/b/"));
    }
    
}
