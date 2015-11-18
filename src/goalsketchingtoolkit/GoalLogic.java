/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 * This class checks if the goal sketching node added to a goal is supported by
 * the rules of goal sketching, depending on the current state of the goal.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
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
        String nodeToAddClassString = nodeToAdd.getClass().toString();

        for (Object o : children) {

            correct = o.getClass() != nodeToAdd.getClass() || nodeToAdd.getClass().toString().contains("Twin");

            if (!correct) {
                throw new UnsupportedOperationException("This goal already has"
                        + ": "
                        + nodeToAdd.getClass().toString());
            } else {
                correct = true;
            }
        }

        if (goal.isEntailed() && (nodeToAddClassString.contains("ANDentailment")
                || nodeToAddClassString.contains("ORentailment"))) {
            throw new UnsupportedOperationException("This goal is already entailed");
        } else if (nodeToAddClassString.contains("ANDentailment")) {
            ANDentailment ae = (ANDentailment) nodeToAdd;
            if (goal.isRefinedFromAssumption()) {
                ae.setEntailsAssumption(true);
            }
            goal.setIsEntailed(true);
            correct = true;
        } else if (nodeToAddClassString.contains("ORentailment")) {
            ORentailment oe = (ORentailment) nodeToAdd;
            if (goal.isRefinedFromAssumption()) {
                oe.setEntailsAssumption(true);
            }
            goal.setIsEntailed(true);
            correct = true;
        } else if (goal.isEntailed()
                && (nodeToAddClassString.contains("OperationalizingProducts")
                || nodeToAddClassString.contains("AssumptionTermination"))) {
            throw new UnsupportedOperationException("This goal is already entailed"
                    + ", cannot add system element");
        } else if ((goal.isOperationalized() || goal.isTerminated()) && (nodeToAddClassString.contains("ANDentailment")
                || nodeToAddClassString.contains("ORentailment"))) {
            throw new UnsupportedOperationException("This goal is already "
                    + "operationalised"
                    + ", cannot add entailment");
        } else if (nodeToAddClassString.contains("OperationalizingProducts")) {

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
            } else if (goal.isRefinedFromAssumption()) {
                throw new UnsupportedOperationException("Parent goal decends"
                        + "from an assumption, cannot add"
                        + "operationalizing products to assumptions.");

            } else {
                goal.setIsOperationalized(true);
                correct = true;
            }
        } else if (nodeToAddClassString.contains("AssumptionTermination")) {

            ArrayList<GSnode> children = goal.getChildren();

            for (GSnode n : children) {
                String nodeType = n.getClass().toString();
                if (nodeType.contains("Goal") && !nodeType.contains("OrientedProposition")) {
                    throw new UnsupportedOperationException("Cannot add assumption"
                            + " terminations to parent goals.");
                }
            }
            goal.setIsTerminated(true);
            correct = true;
        } else if (nodeToAddClassString.contains("GoalOrientedProposition")) {
            GoalOrientedProposition gop = (GoalOrientedProposition) nodeToAdd;

            if (gop.hasPrefix()) {

                if (goal.isRootGoal() && !gop.getPrefix().equalsIgnoreCase("/m/")) { //else if (isAwayGoal()
                    throw new UnsupportedOperationException("Only motivation type"
                            + " propositions can be added to the root goal");
                } else if (goal.isOperationalized() && gop.getPrefix().equalsIgnoreCase("/a/")) {
                    throw new UnsupportedOperationException("Cannot add an "
                            + "assumption goal oriented proposition"
                            + " to this goal because it is already "
                            + "operationalised");
                } else if (gop.isAssumption()) {
                    if (goal.getEntailment() != null) {

                        checkDecendentNotAssumption(goal);

                    }
                    goal.setRefinedFromAssumption(true);
                    goal.setHasGop(true);
                    correct = true;
                } else if (!gop.isAssumption()) {
                    if (goal.isRefinedFromAssumption()) {
                        throw new UnsupportedOperationException("Cannot add a "
                                + gop.getPrefix()
                                + " type GOP to this goal because it decends "
                                + "from an assumption");
                    } else {
                        goal.setHasGop(true);
                        correct = true;
                    }
                } else if (!goal.isRootGoal()) {
                    Goal parentGoal = (Goal) goal.getParent().getParent();
                    if (parentGoal.hasGop()) {
                        GoalOrientedProposition parentGoalGop = parentGoal.getProposition();
                        if (parentGoalGop.hasPrefix()) {
                            if (parentGoalGop.isAssumption() && !gop.isAssumption()) {
                                throw new UnsupportedOperationException("The goal this proposition belongs "
                                        + "to's parent goal's proposition is an assumption, this"
                                        + "proposition cannot have a goal type other than assumption");
                            } else {
                                goal.setHasGop(true);
                                correct = true;
                            }
                        }
                    }
                } else {
                    goal.setHasGop(true);
                    correct = true;
                }
            } else {
                goal.setHasGop(true);
                correct = true;
            }
        } else if (nodeToAddClassString.contains("Annotation")) {
            throw new UnsupportedOperationException("Annotations can only be added"
                    + " to a goal's goal oriented proposition");
        } else if (nodeToAddClassString.contains("Twin")) {
            goal.setHasTwin(true);
            correct = true;
        } else {
            throw new UnsupportedOperationException("Cannot add goal to a goal, "
                    + "unless it's a twin "
                    + "goals are added to a semantic entailment only");
        }

        return correct;

    }

    /**
     * Checks whether the goal in question is the ancestor of goals which are
     * not assumptions or are operationalised to guard against applying an
     * assumption GOP to the said goal if that is the case.
     *
     * @param goal the goal to check the descendents of to ensure they are
     * assumptions if the GOP to add is an assumption.
     */
    public void checkDecendentNotAssumption(Goal goal) {

        String entailmentType = goal.getEntailment().getClass().toString();
        if (entailmentType.contains("ANDentailment")) {

            ANDentailment ae = (ANDentailment) goal.getEntailment();
            ArrayList<GSnode> goals = ae.getChildren();

            for (GSnode n : goals) {
                String type = n.getClass().toString();
                if (type.contains("Goal")) {
                    Goal g = (Goal) n;

                    if (g.hasGop() && g.getProposition().hasPrefix()) {
                        GoalOrientedProposition prop = g.getProposition();
                        if (!prop.isAssumption()) {
                            throw new UnsupportedOperationException("Cannot add an "
                                    + "assumption "
                                    + " type GOP to this goal because it's descendent(s) "
                                    + " are not assumptions");
                        }
                    } else if (g.isOperationalized()) {
                        throw new UnsupportedOperationException("Cannot add an "
                                + "assumption "
                                + " type GOP to this goal because it's descendent(s) "
                                + " are operationalized");
                    }

                    if (g.getEntailment() != null) {
                        checkDecendentNotAssumption(g);
                    }

                } else if (type.contains("Twin")) {
                    Twin t = (Twin) n;
                    Goal original = t.getOriginal();
                    if (original.hasGop() && original.getProposition().hasPrefix()) {
                        GoalOrientedProposition prop = original.getProposition();
                        if (!prop.isAssumption()) {
                            throw new UnsupportedOperationException("Cannot add an "
                                    + "assumption "
                                    + " type GOP to this goal because it's descendent(s) "
                                    + " are not assumptions");
                        }
                    }

                }

                ae.setEntailsAssumption(true);

            }

        } else if (entailmentType.contains("ORentailment")) {

            ORentailment oe = (ORentailment) goal.getEntailment();
            oe.setEntailsAssumption(true);

            ArrayList<GSnode> goals = oe.getChildren();

            for (GSnode n : goals) {
                Goal g = (Goal) n;

                if (g.hasGop() && g.getProposition().hasPrefix()) {
                    GoalOrientedProposition prop = g.getProposition();
                    if (!prop.isAssumption()) {
                        throw new UnsupportedOperationException("Cannot add an "
                                + "assumption "
                                + " type GOP to this goal because it's descendent(s) "
                                + " are not assumptions");
                    }
                } else if (g.isOperationalized()) {
                    throw new UnsupportedOperationException("Cannot add an "
                            + "assumption "
                            + " type GOP to this goal because it's descendent(s) "
                            + " are operationalized");
                }

                if (g.getEntailment() != null) {
                    checkDecendentNotAssumption(g);
                }
            }

            oe.setEntailsAssumption(true);

        }

    }

}
