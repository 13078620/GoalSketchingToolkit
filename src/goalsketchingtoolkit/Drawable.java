/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.awt.Graphics2D;

/**
 * Describes any class that can draw a goal sketching node.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public interface Drawable {

    /**
     * Draws a drawable object.
     *
     * @param g2 the graphics context.
     */
    public void draw(Graphics2D g2);

    /**
     * Denotes whether a point is within a goal sketching node or not.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @return true if the point is within the goal sketching node, false
     * otherwise.
     */
    public boolean contains(int x, int y);

    /**
     * Returns the goal sketching node of this goal sketching graphics.
     *
     * @return the goal sketching node.
     */
    public GSnode getGSnode();

    /**
     * Sets whether this drawable is selected or not.
     *
     * @param selected a boolean value to denote if this drawable is selected or
     * not.
     */
    public void setSelected(boolean selected);

    /**
     * Returns if this drawable is selected.
     *
     * @return true if this drawable is selected, false otherwise.
     */
    public boolean isSelected();

}
