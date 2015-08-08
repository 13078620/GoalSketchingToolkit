/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.awt.Point;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.MouseEvent;

/**
 *
 * @author Chris Berryman.
 */
public interface GoalSketchingControllerInterface {

    /**
     * Returns the selected goal sketching node.
     *
     * @return the currently selected goal sketching node.
     */
    GSnode getCurrentSelection();

    /**
     * Sets the selected goal sketching node.
     *
     * @param e the mouse event.
     */
    void setCurrentSelection(MouseEvent e);

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
     *
     * @param root the root goal.
     * @param fileName the file path.
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    void saveGraph(Goal root, String fileName) throws ParserConfigurationException;

    /**
     * resets the model and view.
     */
    void reset();

    /**
     * Sets the root goal for the goal graph model.
     *
     * @param x the x coordinate of the root goal.
     * @param y the y coordinate of the root goal.
     */
    void addRootGoal(int x, int y);

    /**
     * Adds an AND entailment to a goal.
     */
    void addAndEntailment();

    /**
     * Adds an OR entailment to a goal.
     */
    void addOREntailment();

    /**
     * Adds a leaf goal to a given parent.
     */
    void addLeafGoal();

    /**
     * Adds an Operationalizing Products to a given leaf goal.
     */
    void addOperationalizingProducts();

    /**
     * Adds an Product to a given Operationalizing Products.
     *
     * @param product the product to add.
     */
    void addProduct(String product);

    /**
     * Adds an assumption termination to a given leaf goal.
     */
    void addAssumptionTermination();

    /**
     * Removes a goal.
     */
    void deleteGoal();

    /**
     * Removes a goal.
     *
     * @param g the goal to remove.
     */
    void deleteGoal(Goal g);

    /**
     * Deletes a given goal's ID.
     *
     * @param goal the goal to deleter the ID from.
     */
    void deleteGoalID(Goal goal);

    /**
     * Adds a GOP to a given goal.
     *
     * @param prefix the goal type of the GOP.
     * @param statement the statement of the GOP.
     */
    void addGOP(GoalType prefix, String statement);

    /**
     * Deletes a GOP from a given goal.
     */
    void deleteGoalOrientedProposition();

    /**
     * Deletes operationalizing products from a given goal.
     */
    void deleteOperationalizingProducts();

    /**
     * Deletes an assumption termination products from a given goal.
     */
    void deleteAssumptionTermination();

    /**
     * Adds annotation to the GOP of a goal.
     */
    void addAnnotation();

    /**
     * Denotes whether or not the goal to add is the root node.
     *
     * @param rootNode true if the node to add is the root.
     */
    void setIsRootNode(boolean rootNode);

    /**
     * Denotes whether or not the goal to add is the root node. *
     *
     * @return true if the goal to add is the root node, false otherwise.
     */
    boolean getIsRootNode();

    /**
     * Returns a boolean to denote whether or not there are any goal sketching
     * nodes in the model.
     *
     * @return true if the model is empty, false otherwise.
     */
    boolean modelIsEmpty();

    /**
     * Enables or disables the menu items relevant to the current node of
     * interest.
     *
     * @param e the mouse event.
     */
    void configureContextualMenuItems(MouseEvent e);

    /**
     * Defines what happens when the mouse buttons are released.
     *
     * @param e the mouse event.
     */
    void configureMouseReleased(MouseEvent e);

    /**
     * Defines what happens when the mouse is dragged.
     *
     * @param e the mouse event.
     */
    void configureMouseDragged(MouseEvent e);

    /**
     * Defines what happens when the mouse is moved.
     *
     * @param e the mouse event.
     */
    void configureMouseMoved(MouseEvent e);

    /**
     * Make a larger Rectangle and check to see if the cursor is over it.
     *
     * @param p the location of the cursor.
     * @return true if the cursor is over the rectangle.
     */
    boolean isOverRect(Point p);

    /**
     * Make a smaller Rectangle and use it to locate the cursor relative to the
     * Rectangle centre.
     *
     * @param p the location of the cursor.
     * @return the rectangle's outcode.
     */
    int getOutcode(Point p);

    /**
     * Returns the starting x coordinate of the root goal.
     *
     * @return the starting x coordinate.
     */
    int getRootStartX();

    /**
     * Returns the starting y coordinate of the root goal.
     *
     * @return the starting y coordinate.
     */
    int getRootStartY();

    /**
     * Handles events for setting the ID of the currently selected goal.
     */
    void editGoalID();

    /**
     * Sets the ID of the currently selected goal.
     *
     * @param ID the ID to set.
     */
    void setGoalID(String ID);

    /**
     * Handles events for editing the GOP of the currently selected goal.
     */
    void editGOP();

    /**
     * Sets the goal type and statement for the GOP in question.
     *
     * @param prefix the goal type of the GOP.
     * @param statement th statement of the GOP.
     */
    void setGOP(String prefix, String statement);

    /**
     * Handles events for editing the products of the currently selected
     * operationalizing products.
     */
    void editProducts();
    
    /**
     * Handles events for editing the goal judgement from an annotation.
     */
    void editGoalJudgement();

}
