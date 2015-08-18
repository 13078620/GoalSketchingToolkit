/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 * This class checks if the goal sketching node added to an OR entailment is
 * supported by the rules of goal sketching, depending on the current state of
 * the OR entailment. Factors to consider are if the parent goal of the OR
 * entailment has an assumption goal oriented proposition and the goal to be
 * added to the OR entailment has a goal type other than an assumption.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
 */
public class ORentailmentLogic implements GoalSketchingLogic {

    /**
     * The OR entailment which the logic is performed on.
     */
    private final ORentailment entailment;

    /**
     * The current list of children of the OR entailment of interest.
     */
    private final ArrayList<GSnode> goals;

    /**
     * Constructs an OR entailment logic object initialised with the entailment
     * in question and it's list of children.
     *
     * @param entailment the entailment to add the goal sketching node to.
     */
    public ORentailmentLogic(ORentailment entailment) {
        this.entailment = entailment;
        goals = entailment.getChildren();
    }

    /**
     * Returns true if the addition of the node to add to the AND entailment
     * conforms to the rules of goal sketching, false otherwise.
     *
     * @return true if the node to add is a valid node, false otherwise.
     */
    @Override
    public boolean isCorrect(GSnode nodeToAdd) {

        boolean correct = true;

        if (goals.size() >= ORentailment.MAXIMUM_GOALS) {
            throw new UnsupportedOperationException("Can only add a maximum of"
                    + " two goals to an OR entailment");
        }

        return correct;
    }

    /**
     * Returns whether or not the and entailment for this and entailment
     * graphics instance entails an assumption or not.
     *
     * @return true if the and entailment entails an assumption, false
     * otherwise.
     */
    public boolean isAssumptionEntailment() {

        boolean entailsAssumption = false;

        if (this.entailment.isChild()) {
            Goal g = (Goal) entailment.getParent();
            if (g.hasGop()) {
                GoalOrientedProposition gop = g.getProposition();
                if (gop.isAssumption()) {
                    entailsAssumption = true;
                }
            } else {
                if (g.isRefinedFromAssumption()) {
                    entailsAssumption = true;
                }
            }
        }
        return entailsAssumption;
    }

}
