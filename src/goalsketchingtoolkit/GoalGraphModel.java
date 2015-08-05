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
    public ArrayList getGSnodes() {
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

        for (int i = goalSketchingNodes.size() - 1; i >= 0; i--) {
            goalSketchingNodes.remove(i);
        }
    }

}
