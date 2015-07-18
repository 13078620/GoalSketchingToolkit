/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 * This class consists of operations associated with the composition of a goal.
 * A goal can have a semantic entailment or operationalizing products or an
 * assumption termination and a goal oriented proposition as it's children.
 *
 * @author Chris Berryman.
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
     * Denotes whether this goal has an assumption termination or not.
     */
    private boolean terminated;
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
     * The children of this goal which can contain either an and entailment/an
     * or entailment/operationalizing products/an assumption termination and a
     * goal oriented proposition.
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
     * Adds a child node to this goal which can be a goal oriented proposition,
     * semantic entailment, operationalizing products or assumption termination.
     *
     * @throws UnsupportedOperationException()if this goal is already has a goal
     * oriented proposition, is semantically entailed or operationalized.
     * @param node the node to add.
     */
    @Override
    public void addChild(GSnode node) {

        //if (getClass() == node.getClass()) {
        //    throw new UnsupportedOperationException();
        //}
        for (Object o : children) {
            if (o.getClass() == node.getClass()) {
                throw new UnsupportedOperationException("This goal already has"
                        + ": "
                        + node.getClass().toString());
            }
        }

        if (entailed && (node.getClass()
                .toString().contains("ANDentailment")
                || node.getClass().toString().contains("ORentailment"))) {
            throw new UnsupportedOperationException("This goal is already entailed");
        } else if (node.getClass()
                .toString().contains("ANDentailment")
                || node.getClass().toString().contains("ORentailment")) {
            entailed = true;
        } else if (entailed
                && (node.getClass().toString().contains("OperationalizingProducts"))
                || node.getClass().toString().contains("AssumptionTermination")) {
            throw new UnsupportedOperationException("This goal is already entailed"
                    + ", cannot add system element");
        } else if ((operationalized || terminated) && (node.getClass()
                .toString().contains("ANDentailment")
                || node.getClass().toString().contains("ORentailment"))) {
            throw new UnsupportedOperationException("This goal is already "
                    + "operationalised"
                    + ", cannot add entailment");
        } else if (node.getClass()
                .toString().contains("OperationalizingProducts")) {
            operationalized = true;
        } else if (node.getClass()
                .toString().contains("AssumptionTermination")) {
            /*if (getProposition() != null) {
                GoalOrientedProposition gop = (GoalOrientedProposition) node;
                if (gop.getPrefix() != null) {
                    if (!gop.getPrefix().equalsIgnoreCase("/a/")) {
                        throw new UnsupportedOperationException("Assumption "
                                + "terminations can only be added to goals with"
                                + " assumption"
                                + " goal oriented propositions");
                    }
                }
            }*/
            terminated = true;
        } else if (node.getClass()
                .toString().contains("GoalOrientedProposition")) {
            GoalOrientedProposition gop = (GoalOrientedProposition) node;
            if (gop.hasPrefix()) {
                if (operationalized && gop.getPrefix().equalsIgnoreCase("/a/")) {
                    throw new UnsupportedOperationException("Cannot add an "
                            + "assumption goal oriented proposition"
                            + " to this goal because it is already "
                            + "operationalised");
                } else if (isRoot && !gop.getPrefix().equalsIgnoreCase("/m/")) {
                    throw new UnsupportedOperationException("Only motivation type"
                            + " propositions can be added to the root goal");
                } else {
                    hasGop = true;
                }
            }
        } else if (node.getClass()
                .toString().contains("Annotation")) {
            throw new UnsupportedOperationException("Annotations can only be added"
                    + " to a goal's goal oriented proposition");
        } else {
            throw new UnsupportedOperationException("Cannot add goal to a goal, "
                    + "goals are added to a semantic entailment only");
        }

        children.add(node);
        hasChildren = true;

    }

    /**
     * Removes a child from a goal.
     *
     * @param node the node to remove.
     */
    @Override
    public void removeChild(GSnode node
    ) {

        if (node.getClass()
                .toString().contains("ANDentailment")
                || node.getClass().toString().contains("ORentailment")) {
            entailed = false;
        }

        if (node.getClass()
                .toString().contains("OperationalizingProducts")) {
            operationalized = false;
        }

        if (node.getClass()
                .toString().contains("AssumptionTermination")) {
            terminated = false;
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
     * Sets this goal's parent.
     *
     * @throws UnsupportedOperationException() if this goal sketching node is
     * the root node.
     * @param node the parent Goal Sketching Node of this goal.
     */
    @Override
    public void setParent(GSnode node) {
        if (isRootGoal()) {
            throw new UnsupportedOperationException();
        } else {
            super.setParent(node);
        }
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
    public void setChildren(ArrayList<GSnode> children
    ) {
        this.children = children;
    }

    /**
     * Sets this Goal as the root node with a true boolean value if that is the
     * case.
     *
     * @param isRoot the boolean value which denote if this Goal is the root
     * node or not.
     */
    public void setIsRootGoal(boolean isRoot) {
        this.isRoot = isRoot;
    }

    /**
     * Returns a boolean to denote whether this Goal is the root node or not.
     *
     * @return true if this Goal is the root node, false otherwise.
     */
    public boolean isRootGoal() {
        return isRoot;
    }

    /**
     * Returns a boolean to denote whether this Goal is operationalized or not.
     *
     * @return true if this Goal is operationalized, false otherwise.
     */
    public boolean isOperationalized() {
        return operationalized;
    }

    /**
     * Returns a boolean to denote whether this Goal is terminated or not.
     *
     * @return true if this Goal is terminated, false otherwise.
     */
    public boolean isTerminated() {
        return terminated;
    }

    /**
     * Returns a boolean to denote whether this Goal is entailed or not.
     *
     * @return true if this Goal is entailed, false otherwise.
     */
    public boolean isEntailed() {
        return entailed;
    }
    
     /**
     * Returns a boolean to denote whether this goal has a goal oriented 
     * proposition or not.
     *
     * @return true if this goal has a goal oriented proposition, 
     * false otherwise.
     */
    public boolean hasGop() {
        return hasGop;
    }

    /**
     * Returns this goal's goal oriented proposition.
     *
     * @throws NullPointerException() if the goal does not have a goal oriented
     * proposition.
     * @return this goal's goal oriented proposition.
     */
    public GoalOrientedProposition getProposition() {

        GoalOrientedProposition gop = null;
        for (Object o : children) {
            if (o.getClass().toString().contains("GoalOrientedProposition")) {
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
