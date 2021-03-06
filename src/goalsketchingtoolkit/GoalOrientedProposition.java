/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 * This class consists of operations associated with a goal oriented
 * proposition. A goal oriented proposition can have zero or more annotations as
 * it's children and contains relevant information about the type of goal it is
 * associated with, it's prescriptive statement and context.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
 */
public class GoalOrientedProposition extends GSnode {

    /**
     * The statement of this goal oriented proposition.
     */
    private String statement;
    
    /**
     * The goal type of this goal oriented proposition.
     */
    private GoalType goaltype;
    
    /**
     * The context for this goal oriented proposition.
     */
    private String context;
    
    /**
     * Denotes whether a goal oriented proposition has a prefix or not.
     */
    private boolean hasPrefix;
    
    /**
     * The children of this goal oriented proposition which will only contain
     * annotations.
     */
    private ArrayList<GSnode> annotations;
    
    /**
     * The logic for this GOP.
     */
    private GoalSketchingLogic logic;

    /**
     * Constructs a goal oriented proposition with no initialised attributes.
     */
    public GoalOrientedProposition() {
        this.annotations = new ArrayList();
        logic = new GoalOrientedPropositionLogic(this);
    }

    /**
     * Constructs a goal oriented proposition with a statement only for the
     * purposes of description nodes.
     *
     * @param statement the statement (any alphanumeric string up to 255
     * characters).
     */
    public GoalOrientedProposition(String statement) {
        this.statement = statement;
        this.annotations = new ArrayList();
        logic = new GoalOrientedPropositionLogic(this);
    }

    /**
     * Constructs a goal oriented proposition.
     *
     * @param prefix the goal type of this goal oriented proposition.
     * @param statement the statement (any alphanumeric string up to 255
     * characters) of this goal oriented proposition.
     */
    public GoalOrientedProposition(GoalType prefix, String statement) {
        this.goaltype = prefix;
        //this.setPrefix(prefix);
        hasPrefix = true;
        this.statement = statement;
        this.annotations = new ArrayList();
        logic = new GoalOrientedPropositionLogic(this);
    }

    /**
     * Constructs a goal oriented proposition.
     *
     * @param prefix the goal type of this goal oriented proposition.
     * @param statement the statement (any alphanumeric string up to 255
     * characters) of this goal oriented proposition.
     * @param context the context for this goal oriented proposition.
     */
    public GoalOrientedProposition(GoalType prefix, String statement, String context) {

        this.goaltype = prefix;
        hasPrefix = true;
        this.statement = statement;
        this.context = context;
        this.annotations = new ArrayList();
        logic = new GoalOrientedPropositionLogic(this);
    }

    /**
     * Adds an annotation to this goal oriented proposition.
     *
     * @param node the annotation to add.
     */
    @Override
    public void addChild(GSnode node) {

        if (logic.isCorrect(node)) {
            annotations.add(node);
            hasChildren = true;
            node.hasParent = true;
            node.setParent(this);
        }

    }

    /**
     * Removes an annotation from this goal oriented proposition.
     *
     * @param node the annotation to remove.
     */
    @Override
    public void removeChild(GSnode node) {
        annotations.remove(node);
        if (annotations.isEmpty()) {
            hasChildren = false;
        }
    }
    
    /**
     * Returns this goal oriented proposition's children.
     *
     * @return this goal oriented proposition's children.
     */
    @Override
    public ArrayList<GSnode> getChildren() {
        return annotations;
    }

    /**
     * Sets this goal oriented proposition's children.
     *
     * @param children the children of this oriented proposition.
     */
    @Override
    public void setChildren(ArrayList<GSnode> children) {
        for (Object o : children) {
            if (!o.getClass().toString().contains("Annotation")) {
                throw new UnsupportedOperationException();
            }
        }
        this.annotations = children;
        hasChildren = true;
    }

    /**
     * Returns this goal oriented proposition's prefix (goal type).
     *
     * @return the prefix.
     */
    public String getPrefix() {
        return goaltype.prefix;
    }

    /**
     * Returns this goal oriented proposition's goal type (prefix).
     *
     * @return the goal type.
     */
    public GoalType getGoalType() {
        return goaltype;
    }

    /**
     * Sets the prefix (goal type) for this goal oriented proposition.
     *
     * @param goaltype the prefix .
     */
    public void setPrefix(GoalType goaltype) {

        if (isChild()) {
            
            Goal g = (Goal) getParent();
            if (g.isOperationalized() && goaltype.prefix.equalsIgnoreCase("/a/")) {
                throw new UnsupportedOperationException("The goal this proposition belongs to is "
                        + "operationalized, cannot set goal type as assumption");
            } else if (g.isRootGoal() && !goaltype.prefix.equalsIgnoreCase("/m/")) {
                throw new UnsupportedOperationException("The goal this proposition belongs to is "
                        + "the root goal, cannot set goal type as anything other than motivation");
            } else if (g.hasParent) {
                Goal parentGoal = (Goal) g.getParent().getParent();                
                if (parentGoal.hasGop()) {
                    GoalOrientedProposition parentGoalGOP = parentGoal.getProposition();
                    if (parentGoalGOP.hasPrefix) {
                        if (parentGoalGOP.isAssumption() && !goaltype.prefix.equalsIgnoreCase("/a/")) {
                            throw new UnsupportedOperationException("The goal this proposition belongs "
                                    + "to's parent goal's proposition is an assumption, this"
                                    + "proposition cannot have a goal type other than assumption");
                        } else {
                            this.goaltype = goaltype;
                            hasPrefix = true;
                        }
                    } else {
                        this.goaltype = goaltype;
                        hasPrefix = true;
                    }
                } else {
                    this.goaltype = goaltype;
                    hasPrefix = true;
                }
            } else {
                this.goaltype = goaltype;
                hasPrefix = true;
            }
        } else {
            this.goaltype = goaltype;
            hasPrefix = true;
        }

    }

    /**
     * Returns a boolean to denote whether this goal oriented proposition has a
     * prefix or not.
     *
     * @return true if this goal oriented proposition has a prefix, false
     * otherwise.
     */
    public boolean hasPrefix() {
        return hasPrefix;
    }

    /**
     * Returns a boolean to denote whether this goal oriented proposition is an
     * assumption or not.
     *
     * @return true if this goal oriented proposition is an assumption, false
     * otherwise.
     */
    public boolean isAssumption() {
        return goaltype.prefix.equalsIgnoreCase("/a/");
    }

    /**
     * Returns this goal oriented proposition's statement.
     *
     * @return the statement.
     */
    public String getStatement() {
        return statement;
    }

    /**
     * Sets the statement for this goal oriented proposition.
     *
     * @param statement statement.
     */
    public void setStatement(String statement) {

        this.statement = statement;

    }

    /**
     * Returns the context for this goal oriented proposition.
     *
     * @return the context.
     */
    public String getContext() {
        return context;
    }

    /**
     * Sets the context for this goal oriented proposition
     *
     * @param context the context.
     */
    public void setContext(String context) {
        this.context = context;
    }
    
    /**
     * Clears the annotations form a GOP.
     */
    public void deleteAnnotations() {
        for (int i = annotations.size() - 1; i >= 0; i--) {
            annotations.remove(i);
        }
        hasChildren = false;
    }

}
