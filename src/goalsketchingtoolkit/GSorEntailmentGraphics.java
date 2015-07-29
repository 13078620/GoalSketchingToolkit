/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import static goalsketchingtoolkit.GSentailmentGraphics.CIRCLE_WIDTH;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * This class contains information about the graphical location and proportions
 * of an OR goal sketching entailment.
 *
 * @author Chris Berryman.
 */
public class GSorEntailmentGraphics extends GSentailmentGraphics implements Drawable {

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
     * The Ellipse 2D object which describes the first circular section of the
     * entailment.
     */
    private Ellipse2D.Double circle1;
    /**
     * The Ellipse 2D object which describes the first circular section of the
     * entailment.
     */
    private Ellipse2D.Double circle2;

    /**
     * Constructs a goal sketching or entailment graphics object without
     * initialising attributes.
     */
    public GSorEntailmentGraphics() {
        super();
    }

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

        setCircle(x);

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

    /**
     * Returns the circular section of the entailment.
     *
     * @return the circular section of the entailment as an Ellipse 2D object.
     */
    @Override
    public Ellipse2D.Double getCircle() {
        return this.circle1;
    }

    /**
     * Returns the second circular section of the entailment.
     *
     * @return the second circular section of the entailment as an Ellipse 2D
     * object.
     */
    public Ellipse2D.Double getSecondCircle() {
        return this.circle2;
    }

    /**
     * Sets the first circular section of the entailment.
     *
     * @param x the x position of the circular section.
     */
    @Override
    public void setCircle(int x) {
        this.circle1 = new Ellipse2D.Double(x - 100, super.getToY(), CIRCLE_WIDTH, CIRCLE_HEIGHT);
    }

    /**
     * Sets the second circular section of the entailment.
     *
     * @param x the x position of the circular section.
     */
    public void setSecondCircle(int x) {
        this.circle2 = new Ellipse2D.Double(x + 100, toY2, CIRCLE_WIDTH, CIRCLE_HEIGHT);
    }

    /**
     * Sets the location of the circular section of the entailment.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    @Override
    public void setCircleLocation(int x, int y) {
        this.circle1.x = x;
        this.circle1.y = y;
    }

    /**
     * Sets the location of the second circular section of the entailment.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public void setSecondCircleLocation(int x, int y) {
        this.circle2.x = x;
        this.circle2.y = y;
    }

    /**
     * Returns a boolean to denote whether or not a point is within the circular
     * section of the entailment.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @return true if the point is within the circular section of the
     * entailment, false otherwise.
     */
    @Override
    public boolean contains(int x, int y) {
        return this.circle1.contains(x, y) || this.circle2.contains(x, y);
    }

    /**
     * Returns a boolean to denote whether or not a point is within the second
     * circular section of the entailment.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @return true if the point is within the second circular section of the
     * entailment, false otherwise.
     */
    /*public boolean secondEntailmentCircleContains(int x, int y) {
        return this.circle2.contains(x, y);
    }*/

    /**
     * Draws the goal sketching node.
     *
     * @param g2 the graphics instance.
     */
    @Override
    public void draw(Graphics2D g2) {

        float dash1[] = {10.0f};
        BasicStroke dashed = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
        g2.setStroke(dashed);

        //int lineLength = getLength();
        int lineStartX = super.getX();
        int lineStartY = super.getY();
        int lineToX = getToX();
        int lineToY = getToY();
        int lineToX2 = getToX2();
        int lineToY2 = getToY2();

        g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);
        g2.drawLine(lineStartX, lineStartY, lineToX2, lineToY2);

        g2.setStroke(super.getStroke());
        int ovalDiameter = CIRCLE_WIDTH;
        int ovalX = (int) this.getCircle().x;
        int ovalY = (int) this.getCircle().y;
        int ovalX2 = (int) this.getSecondCircle().x;
        int ovalY2 = (int) this.getSecondCircle().y;

        g2.drawOval(ovalX, ovalY, ovalDiameter, ovalDiameter);
        g2.drawOval(ovalX2, ovalY2, ovalDiameter, ovalDiameter);

    }

}
