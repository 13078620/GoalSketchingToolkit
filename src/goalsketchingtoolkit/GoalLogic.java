/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 * This class checks if the goal sketching node added to a goal is supported by
 * the rules of goal sketching, depending on the current state of the goal.
 *
 * @author Chris Berryman.
 */
public class GoalLogic implements GoalSketchingLogic {

    /**
     * The goal which the logic is performed on.
     */
    private final Goal goal;

    /**
     * The current list of children of the goal of interest.
     */
    private final ArrayList<GSnode> children;

    /**
     * Constructs a goal logic object initialised with the goal in question and
     * it's list of children.
     *
     * @param goal the goal to add the goal sketching node to.
     */
    public GoalLogic(Goal goal) {
        this.goal = goal;
        children = goal.getChildren();
    }

    /**
     * Returns true if the addition of the node to add to the goal conforms to
     * the rules of goal sketching, false otherwise.
     *
     * @return true if the node to add is a valid node, false otherwise.
     */
    @Override
    public boolean isCorrect(GSnode nodeToAdd) {

        boolean correct = false;

        for (Object o : children) {

            correct = o.getClass() != nodeToAdd.getClass();

            if (!correct) {
                throw new UnsupportedOperationException("This goal already has"
                        + ": "
                        + nodeToAdd.getClass().toString());
            }
            /*if (o.getClass() == nodeToAdd.getClass()) {
             throw new UnsupportedOperationException("This goal already has"
             + ": "
             + nodeToAdd.getClass().toString());
             }*/
        }

        if (goal.isEntailed() && (nodeToAdd.getClass()
                .toString().contains("ANDentailment")
                || nodeToAdd.getClass().toString().contains("ORentailment"))) {
            throw new UnsupportedOperationException("This goal is already entailed");

        } else if (nodeToAdd.getClass()
                .toString().contains("ANDentailment")
                || nodeToAdd.getClass().toString().contains("ORentailment")) {
            goal.setIsEntailed(true);
            correct = true;
        } else if (goal.isEntailed()
                && (nodeToAdd.getClass().toString().contains("OperationalizingProducts")
                || nodeToAdd.getClass().toString().contains("AssumptionTermination"))) {
            throw new UnsupportedOperationException("This goal is already entailed"
                    + ", cannot add system element");
        } else if ((goal.isOperationalized() || goal.isTerminated()) && (nodeToAdd.getClass()
                .toString().contains("ANDentailment")
                || nodeToAdd.getClass().toString().contains("ORentailment"))) {
            throw new UnsupportedOperationException("This goal is already "
                    + "operationalised"
                    + ", cannot add entailment");
        } else if (nodeToAdd.getClass()
                .toString().contains("OperationalizingProducts")) {
            if (goal.hasGop()) {
                GoalOrientedProposition gop = goal.getProposition();
                if (gop.hasPrefix()) {
                    if (gop.getPrefix().equalsIgnoreCase("/a/")) {
                        throw new UnsupportedOperationException("Cannot add "
                                + "operationalizing products to assumptions.");
                    } else {
                        goal.setIsOperationalized(true);
                        correct = true;
                    }
                } else {
                    goal.setIsOperationalized(true);
                    correct = true;
                }
            } else {
                goal.setIsOperationalized(true);
                correct = true;
            }
        } else if (nodeToAdd.getClass()
                .toString().contains("AssumptionTermination")) {
            /*if (getProposition() != null) {
             GoalOrientedProposition gop = (GoalOrientedProposition) node;
             if (gop.getPrefix() != null) {
             if (!gop.getPrefix().equalsIgnoreCase("/a/")) {
             throw new UnsupportedOperationException("Assumption "
             + "terminations can only be added to goals with"
             + " assumption"
             + " goal oriented propositions");
             }
             }
             }*/
            goal.setIsTerminated(true);
            correct = true;
        } else if (nodeToAdd.getClass()
                .toString().contains("GoalOrientedProposition")) {
            GoalOrientedProposition gop = (GoalOrientedProposition) nodeToAdd;
            if (gop.hasPrefix()) {
                if (goal.isOperationalized() && gop.getPrefix().equalsIgnoreCase("/a/")) {
                    throw new UnsupportedOperationException("Cannot add an "
                            + "assumption goal oriented proposition"
                            + " to this goal because it is already "
                            + "operationalised");
                } else if (goal.isRootGoal() && !gop.getPrefix().equalsIgnoreCase("/m/")) { //else if (isAwayGoal()
                    throw new UnsupportedOperationException("Only motivation type"
                            + " propositions can be added to the root goal");
                } else {
                    goal.setHasGop(true);
                    correct = true;
                }
            } else {
                goal.setHasGop(true);
                correct = true;
            }
        } else if (nodeToAdd.getClass()
                .toString().contains("Annotation")) {
            throw new UnsupportedOperationException("Annotations can only be added"
                    + " to a goal's goal oriented proposition");
        } else {
            throw new UnsupportedOperationException("Cannot add goal to a goal, "
                    + "goals are added to a semantic entailment only");
        }

        return correct;

    }
}
