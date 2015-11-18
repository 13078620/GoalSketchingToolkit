/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

import java.util.Observable;
import java.util.ArrayList;

/**
 * A goal graph model maintains data produced by goal sketching and enables the
 * registration of observer objects.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
 */
public class GoalGraphModel extends Observable implements GoalGraphModelInterface {

    private Goal rootGoal;
    private ArrayList<GSnode> goalSketchingNodes = new ArrayList<>();

    /**
     * Sets the root goal for this goal graph model.
     *
     * @param root the root goal to set.
     */
    @Override
    public void addRootGoal(Goal root) {

        this.rootGoal = root;
        this.goalSketchingNodes.add(root);
        notifyView();

    }

    /**
     * Returns the root goal from this graph model.
     *
     * @return the root goal.
     */
    @Override
    public Goal getRootGoal() {
        return this.rootGoal;
    }

    /**
     * Adds a goal sketching node to the list.
     *
     * @param n the goal sketching node to add.
     */
    @Override
    public void addToGSnodes(GSnode n) {
        this.goalSketchingNodes.add(n);
        notifyView();
    }

    /**
     * Removes a goal sketching node from the list.
     *
     * @param n the goal sketching node to remove.
     */
    @Override
    public void removeFromGSnodes(GSnode n) {
        this.goalSketchingNodes.remove(n);
        notifyView();
    }

    /**
     * Returns the list of goal sketching nodes.
     *
     * @return the goal sketching nodes.
     */
    @Override
    public ArrayList<GSnode> getGSnodes() {
        return goalSketchingNodes;
    }

    /**
     * Notifies the view of a change in state.
     */
    @Override
    public void notifyView() {
        setChanged();
        notifyObservers();
    }

    /**
     * Resets the list of goal sketching nodes.
     */
    @Override
    public void reset() {
        goalSketchingNodes = new ArrayList<>();
        System.out.println(goalSketchingNodes.size());
    }

    /**
     * Returns a goal with a given ID.
     *
     * @param ID the ID of the goal to get.
     * @return the goal which matches the ID.
     */
    @Override
    public Goal getGoal(String ID) {

        Goal g = null;

        for (GSnode n : goalSketchingNodes) {
            String nodeType = n.getClass().toString();
            if (nodeType.contains("Goal") && !nodeType.contains("OrientedProposition")) {
                Goal goal = (Goal) n;
                if (goal.getId() != null) {
                    if (goal.getId().equalsIgnoreCase(ID)) {
                        g = goal;
                    }
                }
            }
        }
        return g;
    }
}
