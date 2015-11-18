/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

import java.util.ArrayList;
import java.util.Observer;

/**
 * Describes any class that maintains the data associated with the production of
 * goal refinement diagrams.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
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

    /**
     * Adds a goal sketching node to the list.
     *
     * @param n the goal sketching node to add.
     */
    void addToGSnodes(GSnode n);

    /**
     * Removes a goal sketching node from the list.
     *
     * @param n the goal sketching node to remove.
     */
    void removeFromGSnodes(GSnode n);

    /**
     * Returns the list of goal sketching nodes.
     *
     * @return the goal sketching nodes.
     */
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
