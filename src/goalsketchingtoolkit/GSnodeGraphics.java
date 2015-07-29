/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;

/**
 * This class contains information about the graphical location and proportions
 * of a goal sketching node.
 *
 * @author Chris Berryman
 */
public class GSnodeGraphics extends GSgraphics implements Drawable {

    /**
     * The width of the goal sketching node.
     */
    private int width;

    /**
     * The height of the goal sketching node.
     */
    private int height;

    /**
     * Constructs a goal sketching node graphics object without initialising
     * attributes.
     */
    public GSnodeGraphics() {
        super();
    }

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
     * Checks if a point is within the bounds of this goal sketching node
     * graphics instance.
     *
     * @param X the x position of the point.
     * @param Y the y position of the point.
     * @return true if the point is within the bounds of the goal sketching node
     * graphics instance.
     */
    @Override
    public boolean contains(int X, int Y) {

        int w = this.width;
        int h = this.height;

        if ((w | h) < 0) {
            // At least one of the dimensions is negative...
            return false;
        }
        // Note: if either dimension is zero, tests below must return false...
        double x = super.getX();
        double y = super.getY();

        if (X < x || Y < y) {
            return false;
        }
        w += x;
        h += y;
        //    overflow || intersect
        return ((w < x || w > X)
                && (h < y || h > Y));

    }

    /**
     * Draws the goal sketching node.
     *
     * @param g2 the graphics instance.
     */
    @Override
    public void draw(Graphics2D g2) {

        FontMetrics fm = g2.getFontMetrics();

        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();
        String text = "";

        if (super.isSelected()) {
            super.setStrokeColor(Color.RED);
        } else {
            super.setStrokeColor(Color.BLACK);
        }

        g2.setColor(super.getStrokeColor());
        g2.setStroke(super.getStroke());

        GSnode node = super.getGSnode();
        String nodeType = node.getClass().toString();

        if (nodeType.contains("Goal")) {

            Goal goal = (Goal) node;

            if (goal.hasGop()) {
                GoalOrientedProposition gop = goal.getProposition();
                text += gop.getStatement() + "    " + gop.getPrefix();

                if (goal.getId() != null) {
                    text += "   (" + goal.getId() + ")";
                }

                int lineSep = 3;
                int stringWidth = fm.stringWidth(text);
                int strHeight = fm.getHeight();
                int strLength = text.length();

                if (getWidth() > 50 && getHeight() > 25) {

                    int charPerLine = (int) (strLength * width / (double) stringWidth);

                    if (charPerLine >= strLength) {
                        g2.drawString(" " + text, x, y + strHeight);
                    } else {
                        int lines = strLength / charPerLine;
                        int skip = 0;
                        for (int i = 1; i <= lines; i++) {
                            String sTemp = text.substring(skip, skip + charPerLine - 1);
                            if (!text.substring(skip + charPerLine - 1, skip + charPerLine).equals(" ") && !text.substring(skip + charPerLine - 2, skip + charPerLine - 1).equals(" ")) {

                                sTemp += "-";
                            }
                            g2.drawString(" " + sTemp.trim() + " ", x, y + i * strHeight + (i - 1) * lineSep);
                            skip += charPerLine - 1;
                        }
                        g2.drawString(" " + text.substring(skip, strLength).trim() + " ", x, y + (lines + 1) * strHeight + (lines) * lineSep);

                    }
                }

            }

            if (goal.isChild()) {

                GSnode parent = goal.getParent();
                String entailmentType = parent.getClass().toString();

                if (entailmentType.contains("ANDentailment")) {

                    ANDentailment parentEntailment = (ANDentailment) parent;
                    GSentailmentGraphics egs = parentEntailment.getGraphicalProperties();

                    int lineStartX = x + width / 2;
                    int lineStartY = y;
                    int lineToX = (int) egs.getCircle().x;
                    int lineToY = (int) egs.getCircle().y;

                    double[] refinementIntersection = egs.getRefinementIntersection(lineStartX, lineStartY, lineToX, lineToY);

                    lineToX = (int) refinementIntersection[0];
                    lineToY = (int) refinementIntersection[1];

                    g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

                } else if (entailmentType.contains("ORentailment")) {

                    ORentailment parentEntailment = (ORentailment) parent;
                    GSorEntailmentGraphics egs = parentEntailment.getGraphicalProperties();
                    ArrayList orEntailmentChildren = parentEntailment.getChildren();

                    if (goal.equals(orEntailmentChildren.get(0))) {

                        int lineStartX = x + width / 2;
                        int lineStartY = y;
                        int lineToX = (int) egs.getCircle().x;
                        int lineToY = (int) egs.getCircle().y;

                        double[] refinementIntersection = egs.getRefinementIntersection(lineStartX, lineStartY, lineToX, lineToY);

                        lineToX = (int) refinementIntersection[0];
                        lineToY = (int) refinementIntersection[1];

                        g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

                    } else if (goal.equals(orEntailmentChildren.get(1))) {

                        int lineStartX = x + width / 2;
                        int lineStartY = y;
                        int lineToX = (int) egs.getSecondCircle().x;
                        int lineToY = (int) egs.getSecondCircle().y;

                        double[] refinementIntersection = egs.getRefinementIntersection(lineStartX, lineStartY, lineToX, lineToY);

                        lineToX = (int) refinementIntersection[0];
                        lineToY = (int) refinementIntersection[1];
                        
                        g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);
                    }

                }

            }

            g2.drawRect(x, y, width, height);

        } else if (nodeType.contains("OperationalizingProducts")) {

        } else if (nodeType.contains("AssumptionTermination")) {

        } else if (nodeType.contains("Annotation")) {

        }

    }

}
