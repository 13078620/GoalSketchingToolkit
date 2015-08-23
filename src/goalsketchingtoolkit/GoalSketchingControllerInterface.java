/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

import java.awt.Point;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.xml.transform.TransformerException;

/**
 * Describes any class that implements a strategy for the goal sketching view.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
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
     * Saves a goal graph.
     *
     * @param root the root goal.
     * @param fileName the file path.
     * @throws javax.xml.parsers.ParserConfigurationException in the event of a
     * serious configuration error.
     * @throws javax.xml.transform.TransformerException if an exceptional
     * condition occurred during the transformation process.
     */
    void saveGraph(Goal root, String fileName) throws ParserConfigurationException, TransformerException;

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
     * Adds a GOP to a given goal.
     *
     * @param statement the statement of the description node.
     */
    void addDescriptionNode(String statement);

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

    /**
     * Adds a goal judgement to the currently selected annotation.
     *
     * @param cfr1 the rating for the refine confidence factor rating.
     * @param scale the rating for the value significance factor rating.
     * @param cfr2 the rating for the engage confidence factor rating.
     */
    void addGoalJudgement(String cfr1, String cfr2, int scale);

    /**
     * Removes an annotation.
     */
    void deleteAnnotation();

    /**
     * Handles events for editing the leaf judgement from an annotation.
     */
    void editLeafJudgement();

    /**
     * Adds a leaf judgement to the currently selected annotation.
     *
     * @param cfr the rating for the refine confidence factor rating.
     * @param scale the rating for the value significance factor rating.
     */
    void addLeafJudgement(String cfr, int scale);

    /**
     * Handles events for editing the assumption judgement from an annotation.
     */
    void editAssumptionJudgement();

    /**
     * Adds an assumption judgement to the currently selected annotation.
     *
     * @param cf the rating for the refine confidence factor rating.
     */
    void addAssumptionJudgement(String cf);

    /**
     * Handles events for editing a twin goal of an entailment.
     */
    void editTwinGoal();

    /**
     * Returns a combo box with the IDs of the current goals if they have one.
     *
     * @return a combo box containing the IDs of the goals which have an ID.
     */
    JComboBox getGoalsCombobox();

    /**
     * Creates a twin goal of the goal with the given ID.
     *
     * @param goalID the ID of the goal to twin.
     */
    void addTwin(String goalID);

    /**
     * Removes a twin goal.
     */
    void deleteTwin();

}
