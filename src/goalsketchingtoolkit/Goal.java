/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University 
 * - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly 
 * prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, 
 * September 2015
 */
package goalsketchingtoolkit;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class consists of operations associated with the composition of a goal.
 * A goal can have a semantic entailment or operationalizing products or an
 * assumption termination and a goal oriented proposition as it's children.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
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
     * Denotes whether this goal has a twin or not.
     */
    private boolean hasTwin;

    /**
     * The ID of this Goal.
     */
    private String id;

    /**
     * The fit acceptance test of this goal if it has one.
     */
    private String fit;

    /**
     * The children of this goal which can contain either an and entailment | an
     * or entailment | operationalizing products | an assumption termination and
     * a goal oriented proposition.
     */
    private ArrayList<GSnode> children;

    /**
     * The logic for this goal.
     */
    private GoalSketchingLogic logic;
    
    /**
     * The graphical properties of this goal.
     */
    private GSnodeGraphics graphicalProperties;
    
    /**
     * Flags whether this goal was refined from an assumption
     */
    private boolean refinedFromAssumption;

    /**
     * Constructs a Goal and initialises it's array list of children.
     */
    public Goal() {
        children = new ArrayList();
        this.logic = new GoalLogic(this);
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
        this.logic = new GoalLogic(this);
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
        this.logic = new GoalLogic(this);
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
     * @param node the node to add.
     */
    @Override
    public void addChild(GSnode node) {

        if (logic.isCorrect(node)) {
            children.add(node);
            hasChildren = true;
            node.hasParent = true;
            node.setParent(this);
        }

        if (this.hasTwin) {
            updateTwinGop();
        }

    }

    /**
     * Removes a child from a goal.
     *
     * @param node the node to remove.
     */
    @Override
    public void removeChild(GSnode node) {

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

        if (getTwins().isEmpty()) {
            hasTwin = false;
        }

        if (children.isEmpty()) {
            hasChildren = false;
        }
    }

    /**
     * Sets this goal's parent.
     *
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
    public void setChildren(ArrayList<GSnode> children) {
        for (GSnode c : children) {
            addChild(c);
        }
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
     * Sets this Goal as operationalized with a true boolean value if that is
     * the case.
     *
     * @param operationalized the boolean value to denote if this Goal
     * operationalized or not.
     */
    public void setIsOperationalized(boolean operationalized) {
        this.operationalized = operationalized;
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
     * Sets this Goal as terminated with a true boolean value if that is the
     * case.
     *
     * @param terminated the boolean value to denote if this Goal terminated or
     * not.
     */
    public void setIsTerminated(boolean terminated) {
        this.terminated = terminated;
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
     * Sets this Goal as entailed with a true boolean value if that is the case.
     *
     * @param entailed the boolean value to denote if this Goal entailed or not.
     */
    public void setIsEntailed(boolean entailed) {
        this.entailed = entailed;
    }

    /**
     * Returns a boolean to denote whether this goal has a goal oriented
     * proposition or not.
     *
     * @return true if this goal has a goal oriented proposition, false
     * otherwise.
     */
    public boolean hasGop() {
        return hasGop;
    }

    /**
     * Sets this Goal as having a goal oriented proposition with a true boolean
     * value if that is the case.
     *
     * @param hasGop the boolean value to denote if this goal has a goal
     * oriented proposition or not.
     */
    public void setHasGop(boolean hasGop) {
        this.hasGop = hasGop;
    }

    /**
     * Returns a boolean to denote whether this goal has a twin or not.
     *
     * @return true if this goal has a twin, false otherwise.
     */
    public boolean hasTwin() {
        return hasTwin;
    }

    /**
     * Sets this Goal as having a twin if that is the case.
     *
     * @param hasTwin the boolean value to denote if this goal has a twin or
     * not.
     */
    public void setHasTwin(boolean hasTwin) {
        this.hasTwin = hasTwin;
    }

    /**
     * Returns this goal's goal oriented proposition.
     *
     * @return this goal's goal oriented proposition.
     */
    public GoalOrientedProposition getProposition() {

        GoalOrientedProposition gop = null;

        Iterator iterator = createIterator();
        while (iterator.hasNext()) {
            GSnode n = (GSnode) iterator.next();
            if (n instanceof GoalOrientedProposition) {
                gop = (GoalOrientedProposition) n;
            }
        }
        return gop;
    }

    /**
     * Returns this goal's entailment as a goal sketching node if it has one. .
     *
     * @return this goal's entailment.
     */
    public GSnode getEntailment() {

        GSnode entailment = null;

        Iterator iterator = createIterator();
        while (iterator.hasNext()) {
            GSnode n = (GSnode) iterator.next();
            if (n instanceof ANDentailment || n instanceof ORentailment) {
                entailment = n;
            }
        }
        return entailment;
    }

    /**
     * Returns this goal's operationalizing products if it has one.
     *
     * @return this goal's operationalizing products.
     */
    public OperationalizingProducts getOperationalizingProducts() {

        OperationalizingProducts ops = null;

        Iterator iterator = createIterator();
        while (iterator.hasNext()) {
            GSnode n = (GSnode) iterator.next();
            if (n instanceof OperationalizingProducts) {
                ops = (OperationalizingProducts) n;
            }
        }
        return ops;
    }

    /**
     * Returns this goal's assumption termination if it has one.
     *
     * @return this goal's assumption termination.
     */
    public AssumptionTermination getAssumptionTermination() {

        AssumptionTermination at = null;
        Iterator iterator = createIterator();
        while (iterator.hasNext()) {
            GSnode n = (GSnode) iterator.next();
            if (n instanceof AssumptionTermination) {
                at = (AssumptionTermination) n;
            }
        }
        return at;
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
     * Sets the logic for this goal.
     *
     * @param logic the logic to add.
     */
    public void setGoalLogic(GoalSketchingLogic logic) {
        this.logic = logic;
    }

    /**
     * Returns the logic for this goal.
     *
     * @return the logic.
     */
    public GoalSketchingLogic getLogic() {
        return logic;
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
        graphicalProperties.setGSnode(this);
        this.graphicalProperties = (GSnodeGraphics) graphicalProperties;
    }

    /**
     * Creates an iterator which encapsulates this goal's children.
     *
     * @return an iterator instance.
     */
    public Iterator createIterator() {
        return children.iterator();
    }

    /**
     * Returns a boolean to denote whether a goal has graphical properties or
     * not.
     *
     * @return true if this goal has graphical properties, false otherwise.
     */
    @Override
    public boolean hasGraphics() {
        return this.graphicalProperties != null;
    }

    /**
     * Returns this goal's list of twins.
     *
     *
     * @return the list of twins.
     */
    public ArrayList<GSnode> getTwins() {

        ArrayList<GSnode> twins = new ArrayList();
        Iterator iterator = createIterator();
        while (iterator.hasNext()) {

            GSnode n = (GSnode) iterator.next();
            if (n instanceof Twin) {

                twins.add(n);
            }
        }
        return twins;
    }

    /**
     * Returns the flag which denotes whether this goal descends from an
     * assumption.
     *
     * @return true if this goal descends from an assumption, false otherwise.
     */
    public boolean isRefinedFromAssumption() {
        return refinedFromAssumption;
    }

    /**
     * Sets the flag which denotes whether this goal descends from an
     * assumption.
     *
     * @param refinedFromAssumption the boolean value which denotes if this goal
     * descends from an assumption.
     */
    public void setRefinedFromAssumption(boolean refinedFromAssumption) {
        this.refinedFromAssumption = refinedFromAssumption;
    }

    /**
     * Updates the goal oriented proposition for twins of this goal if it has
     * any.
     */
    public void updateTwinGop() {

        for (GSnode n : getTwins()) {
            Twin t = (Twin) n;
            t.updateGOP();
        }
    }
}
