/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

import java.util.Observer;

/**
 *
 * @author Chris Berryman.
 */
public interface GoalGraphModelInterface {

    /**
     * Sets the root goal for a goal graph model.
     *
     * @param root the root goal to set.
     */
    void addRootGoal(Goal root);

    /**
     * Returns the root goal from this graph model.
     *
     * @return the root goal.
     */
    public Goal getRootGoal();

    //void addAndEntailment(Goal parent, ANDentailment entailment);
    //void addOrEntailment(Goal parent, ORentailment entailment);
    //void addLeafGoal(Goal parent, Goal leaf);
    //void addLeafToANDentailment(ANDentailment parent, Goal leaf);
    //void addLeafToORentailment(ORentailment parent, Goal leaf);
    // void addOperationalizingProducts(Goal parent, OperationalizingProducts ops);
   // void addProduct(OperationalizingProducts ops, String product);
    //void removeGoal(Goal child);
    //void removeLeafGoal(Goal child);
    //void removeParentGoal(GSnode parent);
    // void removeOperationalisingProducts(Goal parent);
   // void deleteGoalID(Goal goal);
   // void deleteGoalOrientedProposition(Goal goal);
    //void addAnnotation(Goal goal, Annotation annotation);
    void addToGSnodes(GSnode n);

    /**
     * Removes a goal sketching node from the list.
     *
     * @param n the goal sketching node to remove.
     */
    void removeFromGSnodes(GSnode n);

    ArrayList getGSnodes();

    /**
     * Notifies the view of a change in state.
     */
    void notifyView();

    /**
     * Adds an observer to this model.
     *
     * @param view the observer to add.
     */
    void addObserver(Observer view);

    /**
     * Resets the list of goal sketching nodes.
     */
    void reset();

    /**
     * Returns a goal with a given ID.
     *
     * @param ID the ID of the goal to get.
     * @return the goal which matches the ID.
     */
    Goal getGoal(String ID);
}
