/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.Observable;
import java.util.ArrayList;

/**
 *
 * @author Chris Berryman.
 */
public class GoalGraphModel extends Observable {

    private Goal rootGoal;
    private ArrayList<GSnode> goalSketchingNodes = new ArrayList<>();

    /**
     * Sets the root goal for this goal graph model.
     *
     * @param root the root goal to set.
     */
    public void addRootGoal(Goal root) {

    }

    /**
     * Adds an AND entailment to a goal.
     *
     * @param parent the goal to add the entailment to.
     * @param entailment the entailment.
     */
    public void addAndEntailment(Goal parent, ANDentailment entailment) {

    }

    /**
     * Adds an OR entailment to a goal.
     *
     * @param parent the goal to add the entailment to.
     * @param entailment the entailment.
     */
    public void addOrEntailment(Goal parent, ORentailment entailment) {

    }

    /**
     * Adds a leaf goal to a given parent.
     *
     * @param parent the parent to add the leaf to.
     * @param leaf the leaf goal to add.
     */
    public void addLeafGoal(Goal parent, Goal leaf) {

    }

    /**
     * Adds an Operationalizing Products to a given leaf goal.
     *
     * @param parent the leaf goal to add the Operationalizing Products to.
     * @param ops the Operationalizing Products to add.
     */
    public void addOperationalizingProducts(Goal parent, OperationalizingProducts ops) {

    }

    /**
     * Adds an Product to a given Operationalizing Products.
     *
     * @param ops the Operationalizing Products to add the product to.
     * @param product the product to add.
     */
    public void addProduct(OperationalizingProducts ops, String product) {

    }

    /**
     * Removes a goal.
     *
     * @param child the goal to remove.
     */
    public void removeGoal(Goal child) {

    }

    /**
     * Removes an Operationalizing Products.
     *
     * @param ops the Operationalizing Products to remove.
     */
    public void removeOperationalisingProducts(OperationalizingProducts ops) {

    }

    /**
     * Deletes a given goal's ID.
     *
     * @param goal the goal to deleter the ID from.
     */
    public void deleteGoalID(Goal goal) {

    }

    /**
     * Deletes a GOP from a given goal.
     *
     * @param goal the goal to delete the GOP from.
     */
    public void deleteGoalOrientedProposition(Goal goal) {

    }

    /**
     * Adds annotation to the GOP of a goal.
     *
     * @param goal the goal which has the GOP.
     * @param annotation the annotation to add.
     */
    public void addAnnotation(Goal goal, Annotation annotation) {

    }

    /**
     * Adds a goal sketching node to the list.
     *
     * @param n the goal sketching node to add.
     */
    public void addToGSnodes(GSnode n) {

    }

    /**
     * Removes a goal sketching node to the list.
     *
     * @param i the goal sketching node to remove.
     */
    public void removeFromGSnodes(int i) {

    }

    /**
     * Returns the list of goal sketching nodes.
     *
     * @return the goal sketching nodes.
     */
    public ArrayList getGSnodes() {
        return goalSketchingNodes;
    }

}
