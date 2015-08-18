/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

import java.awt.BasicStroke;
import java.awt.Color;

/**
 * This class contains information about the graphical location of a goal
 * sketching node.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
 */
public class GSgraphics {

    /**
     * The x coordinate of the goal sketching node.
     */
    private int x;

    /**
     * The y coordinate of the goal sketching node.
     */
    private int y;

    /**
     * The goal sketching node this goal sketching graphics object belongs to.
     */
    private GSnode gsNode;

    /**
     * A boolean to denote if this goal sketching graphics is selected.
     */
    private boolean selected;

    /**
     * The stroke for this goal sketching graphics.
     */
    private BasicStroke stroke;

    /**
     * The colour for the stroke of this goal sketching graphics.
     */
    private Color strokeColor;

    /**
     * Constructs a goal sketching graphics object without initialising
     * attributes.
     */
    public GSgraphics() {

    }

    /**
     * Constructs a goal sketching graphics object for a goal sketching node.
     *
     * @param x the x position of the GSnode.
     * @param y the y position of the GSnode.
     */
    public GSgraphics(int x, int y) {

        this.x = x;
        this.y = y;
        this.stroke = new BasicStroke(2);
        this.strokeColor = Color.BLACK;

    }

    /**
     * Returns the x coordinate of this goal sketching graphics object.
     *
     * @return the x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x coordinate of this goal sketching graphics object.
     *
     *
     * @param x the value of the x coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y coordinate of this goal sketching graphics object.
     *
     * @return the y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y coordinate of this goal sketching graphics object.
     *
     *
     * @param y the value of the y coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets whether this goal sketching graphics is selected or not.
     *
     * @param selected a boolean value to denote if this goal sketching graphics
     * is selected or not.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Returns if this goal sketching graphics is selected.
     *
     * @return true if this goal sketching graphics is selected, false
     * otherwise.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Returns the goal sketching node of this goal sketching graphics.
     *
     * @return the goal sketching node.
     */
    public GSnode getGSnode() {
        return this.gsNode;
    }

    /**
     * Sets the goal sketching node for this goal sketching graphics.
     *
     * @param gsNode the goal sketching node.
     */
    public void setGSnode(GSnode gsNode) {
        this.gsNode = gsNode;
    }

    /**
     * Returns the stroke of this goal sketching graphics.
     *
     * @return the stroke.
     */
    public BasicStroke getStroke() {

        return this.stroke;

    }

    /**
     * Sets the line width of the stroke for this goal sketching graphics.
     *
     * @param lineWidth the specified line width of the stroke.
     */
    public void setStrokeWidth(int lineWidth) {

        this.stroke = new BasicStroke(lineWidth);

    }

    /**
     * Returns the stroke colour of this goal sketching graphics.
     *
     * @return the stroke colour.
     */
    public Color getStrokeColor() {

        return this.strokeColor;

    }

    /**
     * Sets the stroke colour of this goal sketching graphics.
     *
     * @param color the specified colour for the stroke.
     */
    public void setStrokeColor(Color color) {

        this.strokeColor = color;

    }

    /**
     * Sets the location of the goal sketching node this goal sketching graphics
     * points to.
     *
     * @param x the x location of this goal sketching node.
     * @param y the y location of this goal sketching node.
     */
    public void setLocation(int x, int y) {

        this.x = x;
        this.y = y;

    }

}
