/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 * This class consists of operations associated with a goal oriented
 * proposition. A goal oriented proposition can have zero or more annotations as
 * it's children and contains relevant information about the type of goal it is
 * associated with, it's statement and context.
 *
 * @author Chris Berryman.
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
     * Constructs a goal oriented proposition with a statement only for the
     * purposes of description nodes.
     *
     * @param statement the statement (any alphanumeric string up to 255
     * characters).
     */
    public GoalOrientedProposition(String statement) {
        this.statement = statement;
    }

    /**
     * Constructs a goal oriented proposition.
     *
     * @param prefix the goal type of this goal oriented proposition.
     * @param statement the statement (any alphanumeric string up to 255
     * characters) of this goal oriented proposition..
     * @param context the context for this goal oriented proposition.
     */
    public GoalOrientedProposition(GoalType prefix, String statement, String context) {

        this.goaltype = prefix;
        this.statement = statement;
        this.context = context;
    }

    /**
     * Adds an annotation to this goal oriented proposition.
     *
     * @throws UnsupportedOperationException() if the node to add is a type
     * other than an annotation.
     * @param node the annotation to add.
     */
    @Override
    public void addChild(GSnode node) {

        if (getClass() == node.getClass()) {
            throw new IllegalArgumentException();
        }

        if (!node.getClass().toString().contains("Annotation")) {
            throw new UnsupportedOperationException();
        } else {
            annotations.add(node);
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
     * Returns this goal oriented proposition's parent node.
     *
     * @return the parent node of this goal oriented proposition.
     */
    /*@Override
     public GSnode getParent() {
     return parent;
     }*/
    /**
     * Sets this goal oriented proposition's parent.
     *
     * @param node the parent Goal Sketching Node of this goal oriented
     * proposition.
     */
    /*@Override
     public void setParent(GSnode node) {
     parent = node;
     hasParent = true;
     }*/
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
     * @throws UnsupportedOperationException() if any node in the list is not
     * the annotation type.
     * @param children the children of this oriented proposition.
     */
    @Override
    public void setChildren(ArrayList<GSnode> children) {
        for (Object o : children) {
            if (!o.getClass().toString().contains("Annotation")) {
                throw new IllegalArgumentException();
            }
        }
        this.annotations = children;
    }

    /**
     * Returns a boolean to denote whether this goal oriented proposition is a
     * parent or not.
     *
     * @return true if this goal oriented proposition is a parent, false
     * otherwise.
     */
    /*@Override
     public boolean isParent() {
     return hasChildren;
     }*/
    /**
     * Returns a boolean to denote whether this goal oriented proposition is a
     * child or not.
     *
     * @return true if this goal oriented proposition is a child, false
     * otherwise.
     */
    /*@Override
     public boolean isChild() {
     return hasParent;
     }*/
    /**
     * Returns this goal oriented proposition's prefix (goal type).
     *
     * @return the prefix.
     */
    public String getPrefix() {
        return goaltype.prefix;
    }

    /**
     * Sets the prefix (goal type) for this goal oriented proposition.
     *
     * @param goaltype the prefix .
     */
    public void setPrefix(GoalType goaltype) {
        this.goaltype = goaltype;
        hasPrefix = true;
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

}
