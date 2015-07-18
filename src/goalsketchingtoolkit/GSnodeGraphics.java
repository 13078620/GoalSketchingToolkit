/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.awt.Graphics2D;

/**
 * This class contains information about the graphical location and proportions
 * of a goal sketching node.
 * @author Chris Berryman
 */
public class GSnodeGraphics extends GSgraphics {

    /**
     * The width of the goal sketching node.
     */
    private int width;

    /**
     * The height of the goal sketching node.
     */
    private int height;

    /**
     * Constructs a goal sketching node graphics object object for a goal
     * sketching node.
     *
     * @param x the x position of the goal sketching node.
     * @param y the y position of the goal sketching node.
     * @param width the width of the goal sketching node.
     * @param height the height of the goal sketching node.
     */
    public GSnodeGraphics(int x, int y, int width, int height) {

        super(x, y);
        this.width = width;
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
     * Sets the width value of this goal sketching node.
     *
     *
     * @param width the value to set.
     */
    public void setWidth(int width) {

        this.width = width;

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
     * Sets the height value of this goal sketching node.
     *
     *
     * @param height the height value to set.
     */
    public void setHeight(int height) {

        this.height = height;

    }

    /**
     * Draws the goal sketching node.
     *
     * @param gsnode the GSnode to draw.
     * @param g2 the graphics instance.
     */
    public void draw(GSnode gsnode, Graphics2D g2) {

    }

}
