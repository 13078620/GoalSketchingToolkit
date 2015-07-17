/*
 * Oxford Brookes University, 2015.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 *
 * @author Chris Berryman
 */
public abstract class GSnode {

    /**
     * A reference to this Goal Sketching Node's parent Goal Sketching Node.
     */
    protected GSnode parent;
    
    /**
     *
     */
    protected boolean hasChildren;
    protected boolean hasParent;

    /**
     * Determines whether a Goal Sketching Node is a composite or a leaf,
     * composite subclasses override this method and return a reference to
     * themselves if they are composite.
     *
     * @return a null reference for leaf nodes, a reference to a composite
     * otherwise.
     */
    public GSnode getCompositeGSnode() {
        return null;
    }

    /**
     * Adds a child node to a composite.
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
     * Removes a child from a composite.
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
        throw new UnsupportedOperationException();
        //return parent;
    }

    /**
     * Sets this Goal Sketching Node's parent.
     *
     * @param node the parent Goal Sketching Node of this Goal Sketching Node.
     */
    public void setParent(GSnode node) {
        throw new UnsupportedOperationException();
        //parent = node;
        //hasParent = true;
    }

    /**
     * Returns this Goal Sketching Node's children if it is a composite,
     * otherwise an UnsupportedOperationException is thrown.
     *
     * @return this Goal Sketching Node's children.
     */
    public  ArrayList<GSnode> getChildren(){
         throw new UnsupportedOperationException();  
    }

    /**
     * Sets this Goal Sketching Node's children if it is a composite, otherwise
     * an UnsupportedOperationException is thrown.
     *
     * @param children
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
        throw new UnsupportedOperationException();
        //return hasChildren;
    }

    /**
     * Returns a boolean to denote whether this Goal Sketching Node is a child
     * or not.
     *
     * @return true if this Goal Sketching Node is a child, false otherwise.
     */
    public boolean isChild() {
        throw new UnsupportedOperationException();
        //return hasParent;
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

}
