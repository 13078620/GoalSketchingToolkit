/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University 
 * - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly 
 * prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, 
 * September 2015
 */
package goalsketchingtoolkit;

/**
 * This class assists with instantiating objects used in goal sketching by
 * creating their associated graphics objects as well as the object in question.
 *
 * @author Chris Berryman  - Oxford Brookes University - 2015.
 */
public class GSnodeFactory {

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

    /**
     * Returns a new goal.
     *
     * @param x the x position of the goal sketching node.
     * @param y the y position of the goal sketching node.
     * @param width the width of the goal sketching node.
     * @param height the height of the goal sketching node.
     * @return a new goal initialised with the given arguments.
     */
    public Goal createGoal(int x, int y, int width, int height) {

        GSnodeGraphics g = new GSnodeGraphics(x, y, width, height);
        Goal goal = new Goal();
        goal.setGraphicalProperties(g);
        g.setGSnode(goal);
        return goal;

    }

    public Twin createTwin(int x, int y, int width, int height, Goal goal) {

        GSnodeGraphics g = new GSnodeGraphics(x, y, width, height);
        Twin twin = new Twin(goal);
        goal.addChild(twin);
        twin.setGraphicalProperties(g);
        g.setGSnode(twin);
        return twin;

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
     * @return a new AND entailment initialised with the given arguments.
     */
    //@Override
    public ANDentailment createANDentailment(int x, int y, int toX, int toY, int length) {

        GSentailmentGraphics g = new GSentailmentGraphics(x, y, toX, toY, length);
        ANDentailment ae = new ANDentailment(g);
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
     * @return a new OR entailment initialised with the given arguments.
     */
    //@Override
    public ORentailment createORentailment(int x, int y, int toX, int toY, int length, int toX2, int toY2, int length2) {

        GSorEntailmentGraphics g = new GSorEntailmentGraphics(x, y, toX, toY, length, toX2, toY2, length2);
        ORentailment oe = new ORentailment(g);
        g.setGSnode(oe);
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
     * @return a new operationalizing products initialised with the given
     * arguments.
     */
    //@Override
    public OperationalizingProducts createOperationalizingProducts(int x, int y, int width, int height, String product) {

        GSnodeGraphics g = new GSnodeGraphics(x, y, width, height);
        OperationalizingProducts ops = new OperationalizingProducts();
        ops.setGraphicalProperties(g);
        ops.addProduct(product);
        g.setGSnode(ops);
        return ops;

    }

    /**
     * Returns a new Operationalizing Products.
     *
     * @param x the x position of the goal sketching node.
     * @param y the y position of the goal sketching node.
     * @param width the width of the goal sketching node.
     * @param height the height of the goal sketching node.
     * @return a new operationalizing products initialised with the given
     * arguments.
     */
    //@Override
    public OperationalizingProducts createOperationalizingProducts(int x, int y, int width, int height) {

        GSnodeGraphics g = new GSnodeGraphics(x, y, width, height);
        OperationalizingProducts ops = new OperationalizingProducts();
        ops.setGraphicalProperties(g);
        g.setGSnode(ops);
        return ops;

    }

    /**
     * Returns a new Assumption Termination.
     *
     * @param x the x position of the goal sketching node.
     * @param y the y position of the goal sketching node.
     * @return a new assumption termination initialised with the given
     * arguments.
     */
    //@Override
    public AssumptionTermination createAssumptionTermination(int x, int y) {

        GSnodeGraphics g = new GSnodeGraphics(x, y, GSnodeGraphics.TERMINATION_WIDTH, GSnodeGraphics.TERMINATION_WIDTH);
        AssumptionTermination at = new AssumptionTermination();
        at.setGraphicalProperties(g);
        g.setGSnode(at);
        return at;

    }

    /**
     * Returns a new Annotation.
     *
     * @param x the x position of the goal sketching node.
     * @param y the y position of the goal sketching node.
     * @param width the width of the goal sketching node.
     * @param height the height of the goal sketching node.
     * @return a new annotation initialised with the given arguments.
     */
    //@Override
    public Annotation createAnnotation(int x, int y, int width, int height) {

        GSnodeGraphics g = new GSnodeGraphics(x, y, width, height);
        Annotation a = new Annotation();
        a.setGraphicalProperties(g);
        g.setGSnode(a);
        return a;

    }

    /**
     * Returns a new goal judgement.
     *
     * @param cfr1 the refine confidence factor rating.
     * @param cfr2 the engage confidence factor rating.
     * @param scale the value significance factor rating.
     * @return a new goal judgement initialised with the given confidence and
     * significance factor ratings.
     */
    public GoalJudgement createGoalJudgement(String cfr1, String cfr2, int scale) {

        ConfidenceFactor cf = ConfidenceFactor.Refine;
        ConfidenceFactor cf2 = ConfidenceFactor.Engage;
        SignificanceFactor sfr = SignificanceFactor.Value;

        GSordinalScale os = GSordinalScale.NONE;
        GSordinalScale os2 = GSordinalScale.NONE;

        if (cfr1.equalsIgnoreCase("none")) {
            os = GSordinalScale.NONE;
        } else if (cfr1.equalsIgnoreCase("low")) {
            os = GSordinalScale.LOW;
        } else if (cfr1.equalsIgnoreCase("medium")) {
            os = GSordinalScale.MEDIUM;
        } else if (cfr1.equalsIgnoreCase("high")) {
            os = GSordinalScale.HIGH;
        }

        if (cfr2.equalsIgnoreCase("none")) {
            os2 = GSordinalScale.NONE;
        } else if (cfr1.equalsIgnoreCase("low")) {
            os2 = GSordinalScale.LOW;
        } else if (cfr1.equalsIgnoreCase("medium")) {
            os2 = GSordinalScale.MEDIUM;
        } else if (cfr1.equalsIgnoreCase("high")) {
            os2 = GSordinalScale.HIGH;
        }

        ConfidenceFactorRating refineCfr = new ConfidenceFactorRating(cf, os);
        ConfidenceFactorRating engageCfr = new ConfidenceFactorRating(cf2, os2);
        SignificanceFactorRating valueSfr = new SignificanceFactorRating(sfr, scale);

        return new GoalJudgement(refineCfr, engageCfr, valueSfr);

    }

    public LeafJudgement createLeafJudgement(String cfr, int scale) {

        ConfidenceFactor cf = ConfidenceFactor.Achieve;
        SignificanceFactor sf = SignificanceFactor.Cost;

        GSordinalScale os = GSordinalScale.NONE;

        if (cfr.equalsIgnoreCase("none")) {
            os = GSordinalScale.NONE;
        } else if (cfr.equalsIgnoreCase("low")) {
            os = GSordinalScale.LOW;
        } else if (cfr.equalsIgnoreCase("medium")) {
            os = GSordinalScale.MEDIUM;
        } else if (cfr.equalsIgnoreCase("high")) {
            os = GSordinalScale.HIGH;
        }

        ConfidenceFactorRating achieveCfr = new ConfidenceFactorRating(cf, os);
        SignificanceFactorRating costSfr = new SignificanceFactorRating(sf, scale);

        return new LeafJudgement(achieveCfr, costSfr);

    }

    public AssumptionJudgement createAssumptionJudgement(String cfr) {

        ConfidenceFactor cf = ConfidenceFactor.Assume;

        GSordinalScale os = GSordinalScale.NONE;

        if (cfr.equalsIgnoreCase("none")) {
            os = GSordinalScale.NONE;
        } else if (cfr.equalsIgnoreCase("low")) {
            os = GSordinalScale.LOW;
        } else if (cfr.equalsIgnoreCase("medium")) {
            os = GSordinalScale.MEDIUM;
        } else if (cfr.equalsIgnoreCase("high")) {
            os = GSordinalScale.HIGH;
        }

        ConfidenceFactorRating assumeCfr = new ConfidenceFactorRating(cf, os);

        return new AssumptionJudgement(assumeCfr);

    }

}
