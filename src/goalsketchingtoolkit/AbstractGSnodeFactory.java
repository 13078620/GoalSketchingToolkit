/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

/**
 *
 * @author Chris Berryman.
 */
public abstract class AbstractGSnodeFactory {

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
    public abstract Goal createGoal(int x, int y, int width, int height, String ID, GoalOrientedProposition gop);

    /**
     * Returns a new GOP.
     *
     * @param prefix the goal type.
     * @param statement the statement.
     * @return a new GOP initialised with the given arguments.
     */
    public abstract GoalOrientedProposition createGOP(GoalType prefix, String statement);

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
    public abstract ANDentailment createANDentailment(int x, int y, int toX, int toY, int length);

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
    public abstract ORentailment createORentailment(int x, int y, int toX, int toY, int length, int toX2, int toY2, int length2);

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
    public abstract OperationalizingProducts createOperationalizingProducts(int x, int y, int width, int height, String product);

    /**
     * Returns a new Assumption Termination.
     *
     * @param x the x position of the goal sketching node.
     * @param y the y position of the goal sketching node.
     * @param width the width of the goal sketching node.
     * @param height the height of the goal sketching node.
     * @return a new initialised with the given arguments.
     */
    public abstract AssumptionTermination createAssumptionTermination(int x, int y, int width, int height);

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
    public abstract Annotation createAnnotation(int x, int y, int width, int height, Judgement judgement);

}
