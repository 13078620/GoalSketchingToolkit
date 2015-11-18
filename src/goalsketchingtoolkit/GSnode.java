/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 * This class is an interface in the form of an abstract class for both
 * composite and leaf goal sketching objects. Subclasses must override
 * operations that they support. For example, a composite should override the
 * add child operation and a leaf should inherit the default operation defined
 * in this abstract class.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
 */
public abstract class GSnode {

    /**
     * A reference to this Goal Sketching Node's parent Goal Sketching Node.
     */
    protected GSnode parent;
    
    /**
     * Flags whether a goal sketching node has children or not.
     */
    protected boolean hasChildren;
    
    /**
     * Flags whether a goal sketching node has a parent or not.
     */
    protected boolean hasParent;

    /**
     * Determines whether a Goal Sketching Node is a composite or a leaf,
     * composite subclasses override this method and return a reference to
     * themselves if they are composite.
     *
     * @return a null reference for leaf nodes, a reference to a composite
     * otherwise.
     *
     */
    public GSnode getCompositeGSnode() {
        return null;
    }

    /**
     * Adds a child node to a composite, composite classes should provide their
     * own implementation, leaf classes should inherit this default
     * implementation.
     *
     * @param node the node to add.
     */
    public void addChild(GSnode node) {

        //if (getClass() == node.getClass() || getCompositeGSnode() == null) {
        throw new UnsupportedOperationException();
        // } else {
        //       node.setParent(this);
        //       hasChildren = true;
        //    }

    }

    /**
     * Removes a child from a composite, composite classes should provide their
     * own implementation, leaf classes should inherit this default
     * implementation.
     *
     * @param node the node to remove.
     */
    public void removeChild(GSnode node) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns this Goal Sketching Node's parent node.
     *
     * @return the parent node of this Goal Sketching Node.
     */
    public GSnode getParent() {
        //throw new UnsupportedOperationException();
        return parent;
    }

    /**
     * Sets this Goal Sketching Node's parent.
     *
     * @param node the parent Goal Sketching Node of this Goal Sketching Node.
     */
    public void setParent(GSnode node) {
        //throw new UnsupportedOperationException();
        parent = node;
        hasParent = true;
    }

    /**
     * Returns a composite goal sketching node's children, composite classes
     * should provide their own implementation, leaf classes should inherit this
     * default implementation.
     *
     * @return this Goal Sketching Node's children.
     */
    public ArrayList<GSnode> getChildren() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets a composite goal sketching node's children, composite classes should
     * provide their own implementation, leaf classes should inherit this
     * default implementation.
     *
     * @param children the children to add.
     */
    public void setChildren(ArrayList<GSnode> children) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a boolean to denote whether this Goal Sketching Node is a parent
     * or not.
     *
     * @return true if this Goal Sketching Node is a parent, false otherwise.
     */
    public boolean isParent() {
        //throw new UnsupportedOperationException();
        return hasChildren;
    }

    /**
     * Returns a boolean to denote whether this Goal Sketching Node is a child
     * or not.
     *
     * @return true if this Goal Sketching Node is a child, false otherwise.
     */
    public boolean isChild() {
        //throw new UnsupportedOperationException();
        return hasParent;
    }

    /**
     * Returns this Goal Sketching Node's graphical properties.
     *
     * @return the graphical properties of this Goal Sketching Node.
     */
    public GSgraphics getGraphicalProperties() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets this Goal Sketching Node's graphical properties.
     *
     * @param graphicalProperties this Goal Sketching Node's graphical
     * properties.
     */
    public void setGraphicalProperties(GSgraphics graphicalProperties) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a boolean to denote whether a goal sketching node has graphical
     * properties or not.
     *
     * @return true if this goal sketching node has graphical properties, false
     * otherwise.
     */
    public boolean hasGraphics() {
        throw new UnsupportedOperationException();
    }

}
