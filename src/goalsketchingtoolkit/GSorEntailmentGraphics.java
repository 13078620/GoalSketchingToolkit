/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import static goalsketchingtoolkit.GSentailmentGraphics.CIRCLE_WIDTH;
import java.awt.BasicStroke;
import java.awt.Color;
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

    private Ellipse2D.Double selectedCircle;

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

        this.circle1 = new Ellipse2D.Double(toX - CIRCLE_WIDTH / 2, toY, CIRCLE_WIDTH, CIRCLE_HEIGHT);
        this.circle2 = new Ellipse2D.Double(toX2 - CIRCLE_WIDTH / 2, toY, CIRCLE_WIDTH, CIRCLE_HEIGHT);

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
        this.selectedCircle.x = x;
        this.selectedCircle.y = y;
    }

    /**
     * Sets the location of the second circular section of the entailment.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    /*public void setSecondCircleLocation(int x, int y) {
        this.circle2.x = x;
        this.circle2.y = y;
    }*/

    /**
     * Returns the goal sketching node of this goal sketching graphics.
     *
     * @return the goal sketching node.
     */
    @Override
    public GSnode getGSnode() {
        return super.getGSnode();
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
        if(this.circle1.contains(x, y)) {
            selectedCircle = circle1;
        }
        if (this.circle2.contains(x, y)) {
            selectedCircle = circle2;
        }
        return this.circle1.contains(x, y) || this.circle2.contains(x, y);
    }

    /**
     * Returns the selected circle.
     *
     * @return a circle which contains the x and y coordinates.
     */
    public Ellipse2D.Double getSelectedCircle() {
        return selectedCircle;
    }

    /**
     * Sets whether this goal sketching graphics is selected or not.
     *
     * @param selected a boolean value to denote if this goal sketching graphics
     * is selected or not.
     */
    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
    }

    /**
     * Returns if this goal sketching graphics is selected.
     *
     * @return true if this goal sketching graphics is selected, false
     * otherwise.
     */
    @Override
    public boolean isSelected() {
        return super.isSelected();
    }

    /**
     * Draws the goal sketching node.
     *
     * @param g2 the graphics instance.
     */
    @Override
    public void draw(Graphics2D g2) {

        if (isSelected()) {
            super.setStrokeColor(Color.RED);
        } else {
            super.setStrokeColor(Color.BLACK);
        }

        float dash1[] = {10.0f};
        BasicStroke dashed = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
        g2.setStroke(dashed);

        int lineStartX = 0;
        int lineStartY = 0;
        if (getGSnode() != null) {
            ORentailment oe = (ORentailment) getGSnode();
            if (oe.hasParent) {
                Goal goal = (Goal) oe.getParent();
                GSnodeGraphics g = goal.getGraphicalProperties();
                lineStartX = g.getX() + g.getWidth() / 2;
                lineStartY = g.getY() + g.getHeight();
            } else {
                lineStartX = super.getX();
                lineStartY = super.getY();
            }
        }

        //int lineLength = getLength();
        //int lineStartX = super.getX();
        //int lineStartY = super.getY();
        int lineToX = (int) circle1.x + CIRCLE_WIDTH / 2;
        int lineToY = (int) circle1.y + CIRCLE_WIDTH / 2;

        double[] refinementIntersection = getRefinementIntersection(lineStartX, lineStartY, lineToX, lineToY);

        lineToX = (int) refinementIntersection[0];
        lineToY = (int) refinementIntersection[1];

        g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

        int lineToX2 = (int) circle2.x + CIRCLE_WIDTH / 2;
        int lineToY2 = (int) circle2.y + CIRCLE_WIDTH / 2;

        double[] refinementIntersection2 = getRefinementIntersection(lineStartX, lineStartY, lineToX2, lineToY2);

        lineToX2 = (int) refinementIntersection2[0];
        lineToY2 = (int) refinementIntersection2[1];

        g2.drawLine(lineStartX, lineStartY, lineToX2, lineToY2);

        g2.setStroke(super.getStroke());
        int ovalDiameter = CIRCLE_WIDTH;
        int ovalX = (int) circle1.x;
        int ovalY = (int) circle1.y;
        int ovalX2 = (int) circle2.x;
        int ovalY2 = (int) circle2.y;

        g2.drawOval(ovalX, ovalY, ovalDiameter, ovalDiameter);
        g2.drawOval(ovalX2, ovalY2, ovalDiameter, ovalDiameter);

    }

}
