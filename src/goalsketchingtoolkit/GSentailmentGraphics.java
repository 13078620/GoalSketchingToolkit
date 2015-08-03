/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 * This class contains information about the graphical location and proportions
 * of a goal sketching entailment.
 *
 * @author Chris Berryman.
 */
public class GSentailmentGraphics extends GSgraphics implements Drawable {

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
     * The Ellipse 2D object which describes the circular section of the
     * entailment.
     */
    private Ellipse2D.Double circle;
    /**
     * The width of the circular section of the entailment.
     */
    public final static int CIRCLE_WIDTH = 30;
    /**
     * The height of the circular section of the entailment.
     */
    public final static int CIRCLE_HEIGHT = 30;

    /**
     * Constructs a goal sketching entailment graphics object without
     * initialising attributes.
     */
    public GSentailmentGraphics() {
        super();
    }

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

        this.circle = new Ellipse2D.Double(x - CIRCLE_WIDTH / 2, toY, CIRCLE_WIDTH, CIRCLE_HEIGHT);
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

    /**
     * Returns the circular section of the entailment.
     *
     * @return the circular section of the entailment as an Ellipse 2D object.
     */
    public Ellipse2D.Double getCircle() {
        return this.circle;
    }

    /**
     * Sets the circular section of the entailment.
     *
     * @param x the x position of the circular section.
     */
    public void setCircle(int x) {
        this.circle = new Ellipse2D.Double(x - CIRCLE_WIDTH / 2, toY, CIRCLE_WIDTH, CIRCLE_HEIGHT);
    }

    /**
     * Sets the location of the circular section of the entailment.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    public void setCircleLocation(int x, int y) {
        this.circle.x = x;
        this.circle.y = y;
    }

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
        return this.circle.contains(x, y);
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
     * Returns the intersection of the refinement of this goal sketching
     * entailment graphics instance.
     *
     * @param x1 the start x position of the line.
     * @param y1 the start y position of the line.
     * @param x2 the end x position of the line.
     * @param y2 the end y position of the line.
     * @return an array with the coordinates of the intersection.
     */
    public double[] getRefinementIntersection(double x1, double y1, double x2, double y2) {

        double off_x = x1 - x2;
        double off_y = y1 - y2;
        double R = 10;

        double ls = off_x * off_x + off_y * off_y;

        double scale = R / Math.sqrt(ls);
        double res_x = off_x * scale + x2;
        double res_y = off_y * scale + y2;
        double[] intersectionArray = {res_x, res_y};

        return intersectionArray;

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
            ANDentailment ae = (ANDentailment) getGSnode();
            if (ae.hasParent) {
                Goal goal = (Goal) ae.getParent();
                GSnodeGraphics g = goal.getGraphicalProperties();
                lineStartX = g.getX() + g.getWidth() / 2;
                lineStartY = g.getY() + g.getHeight();
            } else {
                lineStartX = super.getX();
                lineStartY = super.getY();
            }
        }

        //int lineToX = getToX();
        int lineToX = (int) circle.x + CIRCLE_WIDTH / 2;
        int lineToY = (int) circle.y + CIRCLE_WIDTH / 2;

        double[] refinementIntersection = getRefinementIntersection(lineStartX, lineStartY, lineToX, lineToY);

        lineToX = (int) refinementIntersection[0];
        lineToY = (int) refinementIntersection[1];

        g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

        g2.setStroke(super.getStroke());
        int ovalDiameter = CIRCLE_WIDTH;
        int ovalX = (int) this.getCircle().x;
        int ovalY = (int) this.getCircle().y;

        g2.drawOval(ovalX, ovalY, ovalDiameter, ovalDiameter);

    }

}
