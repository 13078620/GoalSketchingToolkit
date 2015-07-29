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
public interface GoalSketchingControllerInterface {

    /**
     * Loads a goal graph.
     *
     * @param file the file path.
     */
    void loadGraph(String file);

    /**
     * Recursively adds all goal sketching nodes from a root goal to the list in
     * the model.
     *
     * @param goal the root goal and then its subsequent children.
     */
    void drawGraphFromRoot(Goal goal);

    /**
     * Saves a goal graph.
     * @param root the root goal.
     * @param fileName the file path.
     */
    void saveGraph(Goal root, String fileName);

    /**
     * resets the model and view.
     */
    void reset();

    /**
     * Sets the root goal for the goal graph model.
     *
     * @param root the root goal to set.
     */
    void addRootGoal(Goal root);

    /**
     * Adds an AND entailment to a goal.
     *
     * @param parent the goal to add the entailment to.
     * @param entailment the entailment.
     */
    void addAndEntailment(Goal parent, ANDentailment entailment);

    /**
     * Adds an OR entailment to a goal.
     *
     * @param parent the goal to add the entailment to.
     * @param entailment the entailment.
     */
    void addOrEntailment(Goal parent, ORentailment entailment);

    /**
     * Adds a leaf goal to a given parent.
     *
     * @param parent the parent to add the leaf to.
     * @param leaf the leaf goal to add.
     */
    void addLeafGoal(Goal parent, Goal leaf);

    /**
     * Adds an Operationalizing Products to a given leaf goal.
     *
     * @param parent the leaf goal to add the Operationalizing Products to.
     * @param ops the Operationalizing Products to add.
     */
    void addOperationalizingProducts(Goal parent, OperationalizingProducts ops);

    /**
     * Adds an Product to a given Operationalizing Products.
     *
     * @param ops the Operationalizing Products to add the product to.
     * @param product the product to add.
     */
    void addProduct(OperationalizingProducts ops, String product);

    /**
     * Removes a goal.
     *
     * @param child the goal to remove.
     */
    void removeGoal(Goal child);

    /**
     * Removes an Operationalizing Products.
     *
     * @param ops the Operationalizing Products to remove.
     */
    void removeOperationalisingProducts(OperationalizingProducts ops);

    /**
     * Deletes a given goal's ID.
     *
     * @param goal the goal to deleter the ID from.
     */
    void deleteGoalID(Goal goal);

    /**
     * Deletes a GOP from a given goal.
     *
     * @param goal the goal to delete the GOP from.
     */
    void deleteGoalOrientedProposition(Goal goal);

    /**
     * Adds annotation to the GOP of a goal.
     *
     * @param goal the goal which has the GOP.
     * @param annotation the annotation to add.
     */
    void addAnnotation(Goal goal, Annotation annotation);

}
