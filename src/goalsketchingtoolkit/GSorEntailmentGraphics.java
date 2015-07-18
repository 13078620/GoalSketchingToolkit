/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * This class contains information about the graphical location and proportions
 * of an OR goal sketching entailment.
 *
 * @author Chris Berryman.
 */
public class GSorEntailmentGraphics extends GSentailmentGraphics {

    /**
     * The second to x coordinate of the goal sketching entailment.
     */
    private int toX2;
    /**
     * The second to y coordinate of the goal sketching entailment.
     */
    private int toY2;
    /**
     * The second length of the goal sketching entailment.
     */
    private int length2;

    /**
     * Constructs goal sketching or entailment graphics object for an or goal
     * sketching entailment.
     *
     * @param x the x position of the goal sketching entailment.
     * @param y the y position of the goal sketching entailment.
     * @param toX the to x position of the goal sketching entailment.
     * @param toY the to y position of the goal sketching entailment.
     * @param length the length of the goal sketching entailment.
     * @param toX2 the second to x position of the goal sketching entailment.
     * @param toY2 the second to y position of the goal sketching entailment.
     * @param length2 the second length of the goal sketching entailment.
     */
    public GSorEntailmentGraphics(int x, int y, int toX, int toY, int length, int toX2, int toY2, int length2) {

        super(x, y, toX, toY, length);
        this.toX2 = toX2;
        this.toY2 = toY2;
        this.length2 = length2;

    }

    /**
     * Returns the second to x position of this goal sketching entailment.
     *
     * @return the second to x position.
     */
    public int getToX2() {
        return toX2;
    }

    /**
     * Sets the second to x position of this goal sketching entailment.
     *
     * @param toX2 the new second to x position.
     */
    public void setToX2(int toX2) {
        this.toX2 = toX2;
    }

    /**
     * Returns the second to y position of this goal sketching entailment.
     *
     * @return the second to y position.
     */
    public int getToY2() {
        return toY2;
    }

    /**
     * Sets the second to y position of this goal sketching entailment.
     *
     * @param toY2 the new second to y position.
     */
    public void setToY2(int toY2) {
        this.toY2 = toY2;
    }

    /**
     * Returns the second length of this goal sketching entailment.
     *
     * @return the second length.
     */
    public int getLength2() {
        return length2;
    }

    /**
     * Sets the second length of this goal sketching entailment.
     *
     * @param length2 the new second length.
     */
    public void setLength2(int length2) {
        this.length2 = length2;
    }

}
