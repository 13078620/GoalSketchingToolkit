/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * This class contains information about the graphical location and proportions
 * of a goal sketching entailment.
 *
 * @author Chris Berryman.
 */
public class GSentailmentGraphics extends GSgraphics {

    /**
     * The to x coordinate of the goal sketching entailment.
     */
    private int toX;
    /**
     * The to y coordinate of the goal sketching entailment.
     */
    private int toY;
    /**
     * The length of the goal sketching entailment.
     */
    private int length;

    /**
     * Constructs a goal sketching entailment graphics object for a goal
     * sketching entailment.
     *
     * @param x the x position of the goal sketching entailment.
     * @param y the y position of the goal sketching entailment.
     * @param toX the to x position of the goal sketching entailment.
     * @param toY the to y position of the goal sketching entailment.
     * @param length the length of the goal sketching entailment.
     */
    public GSentailmentGraphics(int x, int y, int toX, int toY, int length) {

        super(x, y);
        this.toX = toX;
        this.toY = toY;
        this.length = length;

    }

    /**
     * Returns the to x position of this goal sketching entailment.
     *
     * @return the to x position.
     */
    public int getToX() {
        return toX;
    }

    /**
     * Sets the to x position of this goal sketching entailment.
     *
     * @param toX the new to x position.
     */
    public void setToX(int toX) {
        this.toX = toX;
    }

    /**
     * Returns the to y position of this goal sketching entailment.
     *
     * @return the to y position.
     */
    public int getToY() {
        return toY;
    }

    /**
     * Sets the to y position of this goal sketching entailment.
     *
     * @param toY the new to y position.
     */
    public void setToY(int toY) {
        this.toY = toY;
    }

    /**
     * Returns the length of this goal sketching entailment.
     *
     * @return the length.
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the length of this goal sketching entailment.
     *
     * @param length the new length.
     */
    public void setLength(int length) {
        this.length = length;
    }

}
