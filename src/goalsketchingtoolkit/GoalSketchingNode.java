/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.awt.Color;
import java.awt.BasicStroke;

/**
 * Represents the top level goal sketching node from which more specific nodes
 * inherit from.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class GoalSketchingNode {

    /**
     * The x coordinate of the goal sketching node.
     */
    private double x;

    /**
     * The y coordinate of the goal sketching node.
     */
    private double y;

    /**
     * The width of the goal sketching node.
     */
    private int width;

    /**
     * The height of the goal sketching node.
     */
    private int height;

    /**
     * The colour for the stroke of this goal sketching node.
     */
    private Color strokeColor = Color.BLACK;
        
    /**
     * The stroke for this goal sketching node.
     */
    private BasicStroke stroke = new BasicStroke(2);
    
    
    private boolean selected;

    public GoalSketchingNode() {

    }

    /**
     * Constructs a goal sketching node with specified values for the starting x
     * and y positions and width.
     *
     * @param x the starting x position of the goal sketching node.
     * @param y the starting y position of the goal sketching node.
     * @param width the width of the goal sketching node.
     * @param height the height of the goal sketching node.
     */
    public GoalSketchingNode(double x, double y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    /**
     * Sets the x coordinate of this goal sketching node.
     *
     *
     * @param x the value of the x coordinate.
     */
    public void setX(double x) {

        this.x = x;

    }

    /**
     * Sets the y coordinate of this goal sketching node.
     *
     *
     * @param y the value of the y coordinate.
     */
    public void setY(double y) {

        this.y = y;

    }

    /**
     * Returns the x coordinate of this goal sketching node.
     *
     * @return the x coordinate.
     */
    public double getX() {

        return x;

    }

    /**
     * Returns the y coordinate of this goal sketching node.
     *
     * @return the y coordinate.
     */
    public double getY() {

        return y;

    }

    /**
     * Sets the width value of this goal sketching node.
     *
     *
     * @param width the value to set.
     */
    public void setWidth(int width) {

        this.width = width;

    }

    /**
     * Sets the height value of this goal sketching node.
     *
     *
     * @param height the height value to set.
     */
    public void setHeight(int height) {

        this.height = height;

    }    
    
    /**
     * Returns the width of this goal sketching node.
     *
     * @return the width.
     */
    public int getWidth() {

        return width;

    }

    /**
     * Returns the height of this goal sketching node.
     *
     * @return the height.
     */
    public int getHeight() {

        return height;

    }

    /**
     * Sets the stroke colour of this goal sketching node.
     *
     * @param color the specified colour for the stroke.
     */
    public void setStrokeColor(Color color) {

        this.strokeColor = color;

    }

    /**
     * Sets the line width of the stroke for this goal sketching node.
     *
     * @param lineWidth the specified line width of the stroke.
     */
    public void setStrokeWidth(int lineWidth) {

        this.stroke = new BasicStroke(lineWidth);

    }

    /**
     * Returns the stroke colour of this goal sketching node.
     *
     * @return the stroke colour.
     */
    public Color getStrokeColor() {

        return this.strokeColor;

    }

    /**
     * Returns the stroke of this goal sketching node.
     *
     * @return the stroke.
     */
    public BasicStroke getStroke() {

        return this.stroke;

    }

    public boolean contains(int X, int Y) {

        int w = this.width;
        int h = this.height;

        if ((w | h) < 0) {
            // At least one of the dimensions is negative...
            return false;
        }
        // Note: if either dimension is zero, tests below must return false...
        double x = this.x;
        double y = this.y;

        if (X < x || Y < y) {
            return false;
        }
        w += x;
        h += y;
        //    overflow || intersect
        return ((w < x || w > X)
                && (h < y || h > Y));

    }

    public void setLocation(int x, int y) {

        this.x = x;
        this.y = y;

    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public boolean isSelected() {
        return selected;
    }

}
