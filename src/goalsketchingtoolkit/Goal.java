/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 *
 * @author Chris
 */
public class Goal extends GSnode {

    /**
     * Denotes whether this goal is the root goal or not.
     */
    private boolean isRoot;
    /**
     * Denotes whether this goal is entailed or not.
     */
    private boolean entailed;
    /**
     * Denotes whether this goal is operationalized or not.
     */
    private boolean operationalized;
    /**
     * Denotes whether this goal has a goal oriented proposition or not.
     */
    private boolean hasGop;
    /**
     * The ID of this Goal.
     */
    private String id;
    /**
     * The fit acceptance test of this goal if it has one.
     */
    private String fit;
    /**
     * The children of this goal which can contain either an and 
     * entailment/an or entailment/operationalizing products/an 
     * assumption termination and a goal oriented proposition.
     */
    private ArrayList<GSnode> children;
    /**
     * The graphical properties of this goal.
     */
    private GSnodeGraphics graphicalProperties;

    /**
     * Constructs a Goal and initialises it's array list of children.
     */
    public Goal() {
        children = new ArrayList();
    }

    /**
     * Constructs a Goal, initialises it's array list of children and sets the
     * ID, fit acceptance test and graphical properties.
     *
     * @param id the ID of this Goal.
     * @param graphicalProperties the graphical properties of this goal.
     */
    public Goal(String id, GSnodeGraphics graphicalProperties) {
        this.id = id;
        children = new ArrayList();
    }

    /**
     * Constructs a Goal, initialises it's array list of children and sets the
     * ID, fit acceptance test and graphical properties.
     *
     * @param id the ID of this Goal.
     * @param gop the goal oriented proposition of this goal.
     * @param gp the graphical properties of this goal.
     */
    public Goal(String id, GoalOrientedProposition gop, GSnodeGraphics gp) {
        this.id = id;
        children = new ArrayList();
        children.add(gop);
    }
    
    /**
     * Returns a reference to this goal because it is a composite.
     *
     * @see GSnode#getCompositeGSnode() getCompositeGSnode.
     * @return a reference to this goal.
     */
    @Override
    public GSnode getCompositeGSnode() {
        return this;
    }

    /**
     * Adds a child node to this goal.
     *
     * @throws IllegalArgumentException()if this goal is already has a goal
     * oriented proposition, is semantically entailed or operationalized.
     * @param node the node to add.
     */
    @Override
    public void addChild(GSnode node) {

        if (getClass() == node.getClass()) {
            throw new IllegalArgumentException();
        }

        if (entailed || operationalized || hasGop) {
            throw new IllegalArgumentException();
        }

        if (node.getClass()
                .toString().contains("AndEntailment")
                || node.getClass().toString().contains("OrEntailment")) {
            entailed = true;
        }

        if (node.getClass()
                .toString().contains("OperationalizingProducts")) {
            operationalized = true;
        }

        if (node.getClass()
                .toString().contains("GoalOrientedProposition")) {
            hasGop = true;
        }

        children.add(node);

    }

    /**
     * Removes a child from a goal.
     *
     * @param node the node to remove.
     */
    @Override
    public void removeChild(GSnode node) {

        if (node.getClass()
                .toString().contains("AndEntailment")
                || node.getClass().toString().contains("OrEntailment")) {
            entailed = false;
        }

        if (node.getClass()
                .toString().contains("OperationalizingProducts")) {
            operationalized = false;
        }

        if (node.getClass()
                .toString().contains("GoalOrientedProposition")) {
            hasGop = false;
        }

        children.remove(node);
        if (children.isEmpty()) {
            hasChildren = false;
        }
    }

    /**
     * Returns this goal's parent node.
     *
     * @return the parent node of this goal.
     */
    @Override
    public GSnode getParent() {
        return parent;
    }

    /**
     * Sets this goal's parent.
     *
     * @param node the parent Goal Sketching Node of this goal.
     */
    @Override
    public void setParent(GSnode node) {
        parent = node;
        hasParent = true;
    }

    /**
     * Returns this goal's children.
     *
     * @return this goal's children.
     */
    @Override
    public ArrayList<GSnode> getChildren() {
        return children;
    }

    /**
     * Sets this goal's children.
     *
     * @param children the children of this goal.
     */
    @Override
    public void setChildren(ArrayList<GSnode> children) {
        this.children = children;
    }

    /**
     * Returns a boolean to denote whether goal is a parent or not.
     *
     * @return true if this goal is a parent, false otherwise.
     */
    @Override
    public boolean isParent() {
        return hasChildren;
    }

    /**
     * Returns a boolean to denote whether this goal is a child or not.
     *
     * @return true if this goal is a child, false otherwise.
     */
    @Override
    public boolean isChild() {
        return hasParent;
    }

    /**
     * Sets this Goal as the root node with a true boolean value if that is the
     * case.
     *
     * @param isRoot the boolean value which denote if this Goal is the root
     * node or not.
     */
    public void setIsRootNode(boolean isRoot) {
        this.isRoot = isRoot;
    }

    /**
     * Returns a boolean to denote whether this Goal is the root node or not.
     *
     * @return true if this Goal is the root node, false otherwise.
     */
    public boolean isRootNode() {
        return isRoot;
    }

    /**
     * Returns this goal's goal oriented proposition.
     *
     * @throws NullPointerException() if the goal does not have a
     * @return this goal's goal oriented proposition.
     */
    public GoalOrientedProposition getProposition() {

        GoalOrientedProposition gop = null;
        for (Object o : children) {
            if (o.getClass() == gop.getClass()) {
                gop = (GoalOrientedProposition) o;
            }
        }
        return gop;
    }

    /**
     * Sets the id of this goal.
     *
     * @param id the id.
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Returns the id of this goal.
     *
     * @return the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the fit acceptance test of this goal.
     *
     * @param fit the fit acceptance test.
     */
    public void setFit(String fit) {

        this.fit = fit;

    }

    /**
     * Returns the fit acceptance test of this goal.
     *
     * @return the fit acceptance test.
     */
    public String getFit() {

        return fit;

    }

    /**
     * Checks whether two goals are equal.
     *
     * The result is true if and only if the argument is not null and is a goal
     * object that has the same ID as this goal.
     *
     * @param otherObject the Object to compare with this goal.
     * @return true if the objects are equal; false otherwise.
     */
    @Override
    public boolean equals(Object otherObject) {

        if (otherObject == null) {
            return false;
        }
        if (getClass() != otherObject.getClass()) {
            return false;
        }
        Goal other = (Goal) otherObject;
        return getId().equals(other.getId());
    }

    /**
     * Returns this Goal's graphical properties.
     *
     * @return the graphical properties of this Goal.
     */
    @Override
    public GSnodeGraphics getGraphicalProperties() {
        return graphicalProperties;
    }

    /**
     * Sets this Goal's graphical properties.
     *
     * @param graphicalProperties this Goal's graphical properties.
     */
    @Override
    public void setGraphicalProperties(GSgraphics graphicalProperties) {
        this.graphicalProperties = (GSnodeGraphics) graphicalProperties;
    }
}
