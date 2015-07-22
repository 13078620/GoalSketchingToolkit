/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * This class contains information about the graphical location of a goal
 * sketching node.
 *
 * @author Chris Berryman.
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

}
