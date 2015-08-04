/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 * This class checks if the goal sketching node added to an entailment is
 * supported by the rules of goal sketching, depending on the current state of
 * the entailment. Factors to consider are if the parent goal of the entailment
 * has an assumption goal oriented proposition and the goal to be added to the
 * entailment has a goal type other than an assumption.
 *
 * @author Chris Berryman.
 */
public class ANDentailmentLogic implements GoalSketchingLogic {

    /**
     * The AND entailment which the logic is performed on.
     */
    private final ANDentailment entailment;

    /**
     * The current list of children of the AND entailment of interest.
     */
    //private final ArrayList<GSnode> goals;
    /**
     * Constructs an AND entailment logic object initialised with the entailment
     * in question.
     *
     * @param entailment the entailment to add the goal sketching node to.
     */
    public ANDentailmentLogic(ANDentailment entailment) {
        this.entailment = entailment;
        //goals = entailment.getChildren();
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
        String nodeToAddClassString = nodeToAdd.getClass().toString();
        if (nodeToAddClassString.contains("Goal")) {
            Goal g = (Goal) nodeToAdd;
            if (g.hasGop()) {
                GoalOrientedProposition gop = g.getProposition();
                if (gop.hasPrefix()) {
                    if (entailment.parentGoalPropositionIsAssumption() && !gop.isAssumption()) {
                        throw new UnsupportedOperationException("Can only add assumption goals as children "
                                + "to a assumption goals");
                    }
                }
            } else {
                
                if(entailment.entailsAssumption()) {
                    g.setRefinedFromAssumption(true);
                }
            }
        } else if (nodeToAddClassString.contains("Twin")) {

            Twin t = (Twin) nodeToAdd;
            Goal original = t.getOriginal();
            Goal parent = (Goal) entailment.getParent();
            String goalType;

            if (original == parent) {
                throw new UnsupportedOperationException("Cannot refine an original goal with it's twin");
            }

            if (original.hasGop()) {
                GoalOrientedProposition gop = original.getProposition();
                if (gop.hasPrefix()) {
                    goalType = gop.getPrefix();
                    if (entailment.parentGoalPropositionIsAssumption() && !goalType.equalsIgnoreCase("/a/")) {
                        throw new UnsupportedOperationException("Can only add assumption goals as children "
                                + "to a assumption goals");
                    }
                }
            }
        } else {
            throw new UnsupportedOperationException("Can only add goals as children "
                    + "to an entailment");
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
