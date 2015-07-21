/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class consists of operations associated with semantic entailment which
 * uses conjunction. an AND entailment can have zero to many goals as it's
 * children.
 *
 * @author Chris Berryman.
 */
public class ANDentailment extends GSnode {

    /**
     * A list of goals for this AND entailment.
     */
    protected ArrayList<GSnode> goals;
    /**
     * The logic for this AND entailment.
     */
    private GoalSketchingLogic logic;
    /**
     * The graphical properties object for this AND entailment.
     */
    private GSentailmentGraphics graphicalProperties;

    /**
     * Constructs an AND entailment and initialises it's list of goals and logic.
     */
    public ANDentailment() {
        goals = new ArrayList();
        logic = new ANDentailmentLogic(this);
    }

    /**
     * Constructs an AND entailment and initialises it's list of goals, logic
     * and graphical properties.
     *
     * @param graphicalProperties the graphical properties of this AND
     * entailment.
     */
    public ANDentailment(GSentailmentGraphics graphicalProperties) {

        goals = new ArrayList();
        logic = new ANDentailmentLogic(this);
        this.graphicalProperties = graphicalProperties;
    }

    /**
     * Returns a reference to this AND entailment because it is a composite.
     *
     * @see GSnode#getCompositeGSnode() getCompositeGSnode.
     * @return a reference to this AND entailment.
     */
    @Override
    public GSnode getCompositeGSnode() {
        return this;
    }

    /**
     * Adds a child node to this AND entailment which can be of the goal type.
     *
     * @throws UnsupportedOperationException() if the node added is anything
     * other than a Goal.
     * @param node the goal to add.
     */
    @Override
    public void addChild(GSnode node) {

        if (logic.isCorrect(node)) {
            goals.add(node);
            hasChildren = true;
            node.hasParent = true;
            node.setParent(this);
        }
    }

    /**
     * Removes a goal from a AND entailment.
     *
     * @param node the goal to remove.
     */
    @Override
    public void removeChild(GSnode node) {
        goals.remove(node);
        if (goals.isEmpty()) {
            hasChildren = false;
        }
    }

    /**
     * Returns the goals of this AND entailment.
     *
     * @return the goals.
     */
    @Override
    public ArrayList<GSnode> getChildren() {
        return goals;
    }

    /**
     * Sets this AND entailment's children.
     *
     * @throws UnsupportedOperationException() if the size of the list of goals
     * contains a goal sketching node other than a goal.
     * @param children the goals of this AND entailment.
     */
    @Override
    public void setChildren(ArrayList<GSnode> children) {

        for (GSnode c : children) {            
            addChild(c);
            /*if (!c.getClass().toString().contains("Goal")) {
                throw new UnsupportedOperationException("Can only add goals as children "
                        + "to AND entailment: "
                        + c.getClass().toString()
                        + " cannot be added");
            }*/
        }

        //goals = children;
        //hasChildren = true;

    }

    /**
     * Returns a boolean to denote whether this AND entailment's parent goal's
     * goal oriented proposition is an assumption or not.
     *
     * @return true if this AND entailment's parent goal's goal oriented
     * proposition is an assumption, false otherwise.
     */
    public boolean parentGoalPropositionIsAssumption() {

        boolean parentIsAssumption = false;
        if (isChild()) {
            Goal g = (Goal) getParent();
            parentIsAssumption = g.getProposition().isAssumption();
        }

        return parentIsAssumption;

    }

    /**
     * Returns this AND entailment's graphical properties.
     *
     * @return the graphical properties of this AND entailment.
     */
    @Override
    public GSentailmentGraphics getGraphicalProperties() {
        return graphicalProperties;
    }

    /**
     * Sets this AND entailment's graphical properties.
     *
     * @param graphicalProperties this AND entailment's graphical properties.
     */
    @Override
    public void setGraphicalProperties(GSgraphics graphicalProperties) {
        this.graphicalProperties = (GSentailmentGraphics) graphicalProperties;
    }

    /**
     * Creates an iterator which encapsulates the children of this AND
     * entailment.
     *
     * @return an iterator instance.
     */
    public Iterator createIterator() {
        return goals.iterator();
    }
    
    /**
     * Returns a boolean to denote whether an AND entailment has graphical
     * properties or not.
     *
     * @return true if this AND entailment has graphical properties, false
     * otherwise.
     */
    @Override
    public boolean hasGraphics() {
        return this.graphicalProperties != null;
    }

}
