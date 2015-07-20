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

        if (!nodeToAdd.getClass().toString().contains("Goal")) {
            throw new UnsupportedOperationException("Can only add goals as children "
                    + "to an entailment");
        } else {
            /*Goal goalToAdd = (Goal) node;
             for(Object o : goals) {
             Goal g = (Goal) o;
             if (g.equals(goalToAdd)) {
             throw new IllegalArgumentException("Goal with the ID: " + goalToAdd.getId() + " already exists");
             }
             }*/
            Goal g = (Goal) nodeToAdd;
            String goalType = "";
            if (g.hasGop()) {
                GoalOrientedProposition gop = g.getProposition();
                if (gop.hasPrefix()) {
                    goalType = g.getProposition().getPrefix();
                    if (entailment.parentGoalPropositionIsAssumption() && !goalType.equalsIgnoreCase("/a/")) {
                        throw new UnsupportedOperationException("Can only add assumption goals as children "
                                + "to a assumption goals");

                    }
                }
            }
        }
        return correct;
    }
}
