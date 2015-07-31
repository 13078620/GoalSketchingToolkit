/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 *
 * @author Chris Berryman.
 */
public class GSnodeFactory  {

    /**
     * Returns a new goal.
     *
     * @param x the x position of the goal sketching node.
     * @param y the y position of the goal sketching node.
     * @param width the width of the goal sketching node.
     * @param height the height of the goal sketching node.
     * @param ID the ID for the goal.
     * @param gop the GOP for the goal.
     * @return a new goal initialised with the given arguments.
     */
    //@Override
    public Goal createGoal(int x, int y, int width, int height, String ID, GoalOrientedProposition gop) {

        GSnodeGraphics g = new GSnodeGraphics(x, y, width, height);
        Goal goal = new Goal(ID, gop, g);
        return goal;

    }
    
    public Goal createGoal(int x, int y, int width, int height) {

        GSnodeGraphics g = new GSnodeGraphics(x, y, width, height);
        Goal goal = new Goal();
        goal.setGraphicalProperties(g);
        return goal;

    }

    /**
     * Returns a new GOP.
     *
     * @param prefix the goal type.
     * @param statement the statement.
     * @return a new GOP initialised with the given arguments.
     */
    //@Override
    public GoalOrientedProposition createGOP(GoalType prefix, String statement) {

        GoalOrientedProposition gop = new GoalOrientedProposition(prefix, statement);
        return gop;

    }

    /**
     * Returns a new AND entailment.
     *
     * @param x the x position of the goal sketching node.
     * @param y the y position of the goal sketching node.
     * @param toX the to x position of the goal sketching node.
     * @param toY the to y position of the goal sketching node.
     * @param length the length of the goal sketching node.
     * @return a new initialised with the given arguments.
     */
    //@Override
    public ANDentailment createANDentailment(int x, int y, int toX, int toY, int length) {

        GSentailmentGraphics g = new GSentailmentGraphics(x, y, toX, toY, length);
        ANDentailment ae =  new ANDentailment(g);
        g.setGSnode(ae);
        return ae;
    }

    /**
     * Returns a new OR entailment.
     *
     * @param x the x position of the goal sketching node.
     * @param y the y position of the goal sketching node.
     * @param toX the to x position of the goal sketching node.
     * @param toY the to y position of the goal sketching node.
     * @param length the length of the goal sketching node.
     * @param toX2 the second to x position of the goal sketching node
     * @param toY2 the second to y position of the goal sketching node
     * @param length2 the length of the second part of the entailment.
     * @return a new initialised with the given arguments.
     */
    //@Override
    public ORentailment createORentailment(int x, int y, int toX, int toY, int length, int toX2, int toY2, int length2) {
        
        GSorEntailmentGraphics g = new GSorEntailmentGraphics(x, y, toX, toY, length, toX2, toY2, length2);
        ORentailment oe =  new ORentailment(g);
        return oe;
        
    }

    /**
     * Returns a new Operationalizing Products.
     *
     * @param x the x position of the goal sketching node.
     * @param y the y position of the goal sketching node.
     * @param width the width of the goal sketching node.
     * @param height the height of the goal sketching node.
     * @param product an agent.
     * @return a new initialised with the given arguments.
     */
    //@Override
    public OperationalizingProducts createOperationalizingProducts(int x, int y, int width, int height, String product) {
        
        GSnodeGraphics g = new GSnodeGraphics(x, y, width, height);
        OperationalizingProducts ops = new OperationalizingProducts();
        ops.setGraphicalProperties(g);
        ops.addProduct(product);
        return ops;
        
    }

    /**
     * Returns a new Assumption Termination.
     *
     * @param x the x position of the goal sketching node.
     * @param y the y position of the goal sketching node.
     * @param width the width of the goal sketching node.
     * @param height the height of the goal sketching node.
     * @return a new initialised with the given arguments.
     */
    //@Override
    public AssumptionTermination createAssumptionTermination(int x, int y, int width, int height) {
        
        GSnodeGraphics g = new GSnodeGraphics(x, y, width, height);
        AssumptionTermination at = new AssumptionTermination();
        at.setGraphicalProperties(g);
        return at;
        
    }

    /**
     * Returns a new Annotation.
     *
     * @param x the x position of the goal sketching node.
     * @param y the y position of the goal sketching node.
     * @param width the width of the goal sketching node.
     * @param height the height of the goal sketching node.
     * @param judgement the judgement for the proposition the annotation is
     * associated with.
     * @return a new initialised with the given arguments.
     */
    //@Override
    public Annotation createAnnotation(int x, int y, int width, int height, Judgement judgement) {
        
        GSnodeGraphics g = new GSnodeGraphics(x, y, width, height);
        Annotation a = new Annotation(judgement);
        a.setGraphicalProperties(g);
        return a;           
        
    }

}
