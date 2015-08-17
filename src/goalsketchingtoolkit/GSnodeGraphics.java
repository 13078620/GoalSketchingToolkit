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
import java.awt.geom.Ellipse2D;

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
     * The width of the circular section of an assumption termination.
     */
    public final static int TERMINATION_WIDTH = 30;

    /**
     * The start width of an annotation.
     */
    public final static int ANNOTATION_START_WIDTH = 100;

    /**
     * The start height of an annotation.
     */
    public final static int ANNOTATION_START_HEIGHT = 50;

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
     * Returns the goal sketching node of this goal sketching graphics.
     *
     * @return the goal sketching node.
     */
    @Override
    public GSnode getGSnode() {
        return super.getGSnode();
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

        FontMetrics fm = g2.getFontMetrics();

        int x = getX();
        int y = getY();
        //int width = this.width;
        //int height = this.height;
        String text = "";

        if (isSelected()) {
            super.setStrokeColor(Color.RED);
        } else {
            super.setStrokeColor(Color.BLACK);
        }

        g2.setColor(super.getStrokeColor());
        g2.setStroke(new BasicStroke(2));

        GSnode node = super.getGSnode();
        String nodeType = node.getClass().toString();

        if (nodeType.contains("Goal")) {

            Goal goal = (Goal) node;

            if (goal.isChild()) {

                GSnode parent = goal.getParent();
                String entailmentType = parent.getClass().toString();

                if (entailmentType.contains("ANDentailment")) {

                    ANDentailment parentEntailment = (ANDentailment) parent;
                    GSentailmentGraphics egs = parentEntailment.getGraphicalProperties();

                    int lineStartX = x + width / 2;
                    int lineStartY = y + height / 2;                   
                    int lineToX = (int) egs.getCircle().x + GSentailmentGraphics.CIRCLE_WIDTH / 2;
                    int lineToY = (int) egs.getCircle().y + GSentailmentGraphics.CIRCLE_WIDTH / 2;

                    double[] refinementIntersection = egs.getRefinementIntersection(lineStartX, lineStartY, lineToX, lineToY);

                    lineToX = (int) refinementIntersection[0];
                    lineToY = (int) refinementIntersection[1];

                    g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

                    g2.drawRect(x, y, width, height);
                    g2.setColor(Color.WHITE);
                    g2.fillRect(x, y, width, height);
                    g2.setColor(Color.BLACK);

                } else if (entailmentType.contains("ORentailment")) {

                    ORentailment parentEntailment = (ORentailment) parent;
                    GSorEntailmentGraphics egs = parentEntailment.getGraphicalProperties();
                    ArrayList orEntailmentChildren = parentEntailment.getChildren();

                    if (goal.equals(orEntailmentChildren.get(0))) {

                        int lineStartX = x + width / 2;
                        int lineStartY = y + height / 2;
                        int lineToX = (int) egs.getCircle().x + GSentailmentGraphics.CIRCLE_WIDTH / 2;
                        int lineToY = (int) egs.getCircle().y + GSentailmentGraphics.CIRCLE_WIDTH / 2;

                        double[] refinementIntersection = egs.getRefinementIntersection(lineStartX, lineStartY, lineToX, lineToY);

                        lineToX = (int) refinementIntersection[0];
                        lineToY = (int) refinementIntersection[1];

                        g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

                        g2.drawRect(x, y, width, height);
                        g2.setColor(Color.WHITE);
                        g2.fillRect(x, y, width, height);
                        g2.setColor(Color.BLACK);

                    } else if (goal.equals(orEntailmentChildren.get(1))) {

                        int lineStartX = x + width / 2;
                        int lineStartY = y + height / 2;
                        int lineToX = (int) egs.getSecondCircle().x + GSentailmentGraphics.CIRCLE_WIDTH / 2;
                        int lineToY = (int) egs.getSecondCircle().y + GSentailmentGraphics.CIRCLE_WIDTH / 2;

                        double[] refinementIntersection = egs.getRefinementIntersection(lineStartX, lineStartY, lineToX, lineToY);

                        lineToX = (int) refinementIntersection[0];
                        lineToY = (int) refinementIntersection[1];

                        g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

                        g2.drawRect(x, y, width, height);
                        g2.setColor(Color.WHITE);
                        g2.fillRect(x, y, width, height);
                        g2.setColor(Color.BLACK);
                    }

                }

                String parentEntailmentType = goal.getParent().getClass().toString();

                if (parentEntailmentType.contains("ANDentailment")) {
                    ANDentailment ae = (ANDentailment) goal.getParent();
                    GSentailmentGraphics g = ae.getGraphicalProperties();
                    ArrayList<GSnode> childNodes = ae.getChildren();

                    if (childNodes != null && !childNodes.isEmpty()) {
                        GSnodeGraphics gs = (GSnodeGraphics) childNodes.get(childNodes.size() - 1).getGraphicalProperties();
                        if (gs == this) {
                            g.fillOval(g2);
                        }
                    }

                } else if (parentEntailmentType.contains("ORentailment")) {
                    ORentailment oe = (ORentailment) goal.getParent();
                    GSorEntailmentGraphics g = oe.getGraphicalProperties();
                    ArrayList<GSnode> childNodes = oe.getChildren();

                    if (childNodes != null && !childNodes.isEmpty()) {

                        if (childNodes.size() == 2) {

                            GSnodeGraphics gs1 = (GSnodeGraphics) childNodes.get(0).getGraphicalProperties();
                            GSnodeGraphics gs2 = (GSnodeGraphics) childNodes.get(childNodes.size() - 1).getGraphicalProperties();
                            if (gs1 == this) {
                                g.fillFirstOval(g2);
                            } else if (gs2 == this) {
                                g.fillSecondOval(g2);
                            }

                        } else if (childNodes.size() == 1) {
                            GSnodeGraphics gs1 = (GSnodeGraphics) childNodes.get(0).getGraphicalProperties();
                            if (gs1 == this) {
                                g.fillFirstOval(g2);
                            }
                        }
                    }
                }

            } else {
                g2.drawRect(x, y, width, height);
                g2.setColor(Color.WHITE);
                g2.fillRect(x, y, width, height);
                g2.setColor(Color.BLACK);
            }

            drawGoal(g2);

        } else if (nodeType.contains("OperationalizingProducts")) {

            OperationalizingProducts ops = (OperationalizingProducts) node;

            g2.drawOval(x, y, width, height);
            Goal parentGoal = (Goal) ops.getParent();
            GSnodeGraphics g = parentGoal.getGraphicalProperties();

            int lineStartX = x + width / 2;
            int lineStartY = y + height / 2;
            int lineToX = (int) g.getX() + g.getWidth() / 2;
            int lineToY = (int) g.getY() + g.getHeight() / 2;

            g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

            g2.setColor(Color.WHITE);
            g2.fillOval(x, y, width, height);

            g.draw(g2);

            if (ops.getProducts().size() >= 1) {
                ArrayList<String> ps = ops.getProducts();
                text = "";

                for (int i = 0; i < ps.size(); i++) {
                    if (ps.indexOf(ps.get(i)) != ps.size() - 1) {
                        text += ps.get(i) + ", ";
                    } else {
                        text += ps.get(i);
                    }
                }

            }
            int lineSep = 3;
            int stringWidth = fm.stringWidth(text);
            int strHeight = fm.getHeight();
            int strLength = text.length();
            if (getWidth() > 50 && getHeight() > 25) {

                int charPerLine = (int) (strLength * width / (double) stringWidth);

                if (charPerLine >= strLength) {
                    g2.drawString(" " + text, x + 10, y + strHeight + 10);
                } else {
                    int lines = strLength / charPerLine;
                    int skip = 0;
                    for (int i = 1; i <= lines; i++) {
                        String sTemp = text.substring(skip, skip + charPerLine - 1);
                        if (!text.substring(skip + charPerLine - 1, skip + charPerLine).equals(" ") && !text.substring(skip + charPerLine - 2, skip + charPerLine - 1).equals(" ")) {

                            sTemp += "-";
                        }
                        g2.drawString(" " + sTemp.trim() + " ", x + 10, y + i * strHeight + (i - 1) * lineSep + 10);
                        skip += charPerLine - 1;
                    }
                    g2.drawString(" " + text.substring(skip, strLength).trim() + " ", x + 10, y + (lines + 1) * strHeight + (lines) * lineSep + 10);

                }
            }

        } else if (nodeType.contains("AssumptionTermination")) {

            AssumptionTermination ats = (AssumptionTermination) node;
            Goal parentGoal = (Goal) ats.getParent();
            GSnodeGraphics g = parentGoal.getGraphicalProperties();

            Ellipse2D.Double circle = new Ellipse2D.Double(x, y, width, height);
            g2.draw(circle);

            int lineStartX = x + width / 2;
            int lineStartY = y + height / 2;
            int lineToX = (int) g.getX() + g.getWidth() / 2;
            int lineToY = (int) g.getY() + g.getHeight() / 2;

            g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

            g2.setColor(Color.WHITE);
            g2.fillOval(x, y, width, height);

            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(4));
            g2.drawLine(lineStartX, y + 5, lineStartX, y + 17);
            g2.drawLine(lineStartX, y + 23, lineStartX, y + 25);

            g.draw(g2);

        } else if (nodeType.contains("Annotation")) {

            Annotation a = (Annotation) node;
            GoalOrientedProposition gop = (GoalOrientedProposition) a.getParent();
            Goal parentGoal = (Goal) gop.getParent();
            GSnodeGraphics g = parentGoal.getGraphicalProperties();
            String judgementType = "";

            float dash1[] = {10.0f};
            BasicStroke dashed = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
            g2.setStroke(dashed);

            if (a.getJudgement() != null) {

                g2.drawLine(g.getX() + g.getWidth() / 2, g.getY() + g.getHeight() / 2, x + width / 2, y + height / 2);

                g2.setColor(Color.WHITE);
                g2.fillRect(x, y, width, height);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(2));

                judgementType = a.getJudgement().getClass().toString();

                if (judgementType.contains("GoalJudgement")) {

                    g2.drawLine(x, y, x, y + height);
                    g2.drawLine(x + width, y, x + width, y + height);

                    GoalJudgement j = (GoalJudgement) a.getJudgement();
                    ConfidenceFactorRating cfr1 = j.getRefineConfidenceFactorRating();
                    ConfidenceFactorRating cfr2 = j.getRefineConfidenceFactorRating();
                    SignificanceFactorRating sfr = j.getSignificanceFactorRating();
                    String text1 = cfr1.getKey() + ": " + cfr1.getValue();
                    String text2 = cfr2.getKey() + ": " + cfr1.getValue();
                    String text3 = sfr.getKey() + ": " + sfr.getValue();
                    g2.drawString(" " + text1, x, y + 15);
                    g2.drawString(" " + text2, x, y + 30);
                    g2.drawString(" " + text3, x, y + 45);

                } else if (judgementType.contains("LeafJudgement")) {

                    g2.drawLine(x, y, x, y + 2 * (height / 3));
                    g2.drawLine(x + width, y, x + width, y + 2 * (height / 3));

                    LeafJudgement j = (LeafJudgement) a.getJudgement();
                    ConfidenceFactorRating cfr1 = j.getConfidenceFactorRating();
                    SignificanceFactorRating sfr = j.getSignificanceFactorRating();
                    String text1 = cfr1.getKey() + ": " + cfr1.getValue();
                    String text2 = sfr.getKey() + ": " + sfr.getValue();
                    g2.drawString(" " + text1, x, y + 12);
                    g2.drawString(" " + text2, x, y + 27);

                } else if (judgementType.contains("AssumptionJudgement")) {

                    g2.drawLine(x, y, x, y + height / 3);
                    g2.drawLine(x + width, y, x + width, y + height / 3);

                    AssumptionJudgement j = (AssumptionJudgement) a.getJudgement();
                    ConfidenceFactorRating cfr1 = j.getConfidenceFactorRating();
                    String text1 = cfr1.getKey() + ": " + cfr1.getValue();
                    g2.drawString(" " + text1, x, y + 12);

                }

            } else {
                g2.drawLine(g.getX() + g.getWidth() / 2, g.getY() + g.getHeight() / 2, x + width / 2, y + height / 2);
                g2.setColor(Color.WHITE);
                g2.fillRect(x, y, width, height);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(2));
                g2.drawLine(x, y, x, y + height);
                g2.drawLine(x + width, y, x + width, y + height);
            }

            ArrayList<GSnode> childNodes = gop.getChildren();

            if (childNodes != null && !childNodes.isEmpty()) {
                GSnodeGraphics gs = (GSnodeGraphics) childNodes.get(childNodes.size() - 1).getGraphicalProperties();
                if (gs == this) {
                    g.drawGoal(g2);
                }
            }
        } else if (nodeType.contains("Twin")) {

            Twin twin = (Twin) node;

            if (twin.isChild()) {

                GSnode parent = twin.getParent();
                String entailmentType = parent.getClass().toString();

                if (entailmentType.contains("ANDentailment")) {

                    ANDentailment parentEntailment = (ANDentailment) parent;
                    GSentailmentGraphics egs = parentEntailment.getGraphicalProperties();

                    int lineStartX = x + width / 2;
                    int lineStartY = y + height / 2;
                    int lineToX = (int) egs.getCircle().x + GSentailmentGraphics.CIRCLE_WIDTH / 2;
                    int lineToY = (int) egs.getCircle().y + GSentailmentGraphics.CIRCLE_WIDTH / 2;

                    double[] refinementIntersection = egs.getRefinementIntersection(lineStartX, lineStartY, lineToX, lineToY);

                    lineToX = (int) refinementIntersection[0];
                    lineToY = (int) refinementIntersection[1];

                    g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

                    g2.drawRect(x, y, width, height);
                    g2.setColor(Color.WHITE);
                    g2.fillRect(x, y, width, height);
                    g2.setColor(Color.BLACK);

                } else if (entailmentType.contains("ORentailment")) {

                    ORentailment parentEntailment = (ORentailment) parent;
                    GSorEntailmentGraphics egs = parentEntailment.getGraphicalProperties();
                    ArrayList orEntailmentChildren = parentEntailment.getChildren();

                    if (twin.equals(orEntailmentChildren.get(0))) {

                        int lineStartX = x + width / 2;
                        int lineStartY = y + height / 2;
                        int lineToX = (int) egs.getCircle().x + GSentailmentGraphics.CIRCLE_WIDTH / 2;
                        int lineToY = (int) egs.getCircle().y + GSentailmentGraphics.CIRCLE_WIDTH / 2;

                        double[] refinementIntersection = egs.getRefinementIntersection(lineStartX, lineStartY, lineToX, lineToY);

                        lineToX = (int) refinementIntersection[0];
                        lineToY = (int) refinementIntersection[1];

                        g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

                        g2.drawRect(x, y, width, height);
                        g2.setColor(Color.WHITE);
                        g2.fillRect(x, y, width, height);
                        g2.setColor(Color.BLACK);

                    } else if (twin.equals(orEntailmentChildren.get(1))) {

                        int lineStartX = x + width / 2;
                        int lineStartY = y + height / 2;
                        int lineToX = (int) egs.getSecondCircle().x + GSentailmentGraphics.CIRCLE_WIDTH / 2;
                        int lineToY = (int) egs.getSecondCircle().y + GSentailmentGraphics.CIRCLE_WIDTH / 2;

                        double[] refinementIntersection = egs.getRefinementIntersection(lineStartX, lineStartY, lineToX, lineToY);

                        lineToX = (int) refinementIntersection[0];
                        lineToY = (int) refinementIntersection[1];

                        g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

                        g2.drawRect(x, y, width, height);
                        g2.setColor(Color.WHITE);
                        g2.fillRect(x, y, width, height);
                        g2.setColor(Color.BLACK);
                    }

                }

                String parentEntailmentType = twin.getParent().getClass().toString();

                if (parentEntailmentType.contains("ANDentailment")) {
                    ANDentailment ae = (ANDentailment) twin.getParent();
                    GSentailmentGraphics g = ae.getGraphicalProperties();
                    ArrayList<GSnode> childNodes = ae.getChildren();

                    if (childNodes != null && !childNodes.isEmpty()) {
                        GSnodeGraphics gs = (GSnodeGraphics) childNodes.get(childNodes.size() - 1).getGraphicalProperties();
                        if (gs == this) {
                            g.fillOval(g2);
                        }
                    }

                } else if (parentEntailmentType.contains("ORentailment")) {
                    ORentailment oe = (ORentailment) twin.getParent();
                    GSorEntailmentGraphics g = oe.getGraphicalProperties();
                    ArrayList<GSnode> childNodes = oe.getChildren();

                    if (childNodes != null && !childNodes.isEmpty()) {

                        if (childNodes.size() == 2) {

                            GSnodeGraphics gs1 = (GSnodeGraphics) childNodes.get(0).getGraphicalProperties();
                            GSnodeGraphics gs2 = (GSnodeGraphics) childNodes.get(childNodes.size() - 1).getGraphicalProperties();
                            if (gs1 == this) {
                                g.fillFirstOval(g2);
                            } else if (gs2 == this) {
                                g.fillSecondOval(g2);
                            }

                        } else if (childNodes.size() == 1) {
                            GSnodeGraphics gs1 = (GSnodeGraphics) childNodes.get(0).getGraphicalProperties();
                            if (gs1 == this) {
                                g.fillFirstOval(g2);
                            }
                        }
                    }
                }

            } else {
                g2.drawRect(x, y, width, height);
                g2.setColor(Color.WHITE);
                g2.fillRect(x, y, width, height);
                g2.setColor(Color.BLACK);
            }

            drawTwin(g2);

        }

    }

    public void drawGoal(Graphics2D g2) {

        GSnode node = super.getGSnode();
        String nodeType = node.getClass().toString();
        FontMetrics fm = g2.getFontMetrics();
        int x = getX();
        int y = getY();

        if (nodeType.contains("Goal")) {

            Goal goal = (Goal) node;
            String text = "";

            g2.drawRect(x, y, width, height);
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, width, height);
            g2.setColor(Color.BLACK);

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

                if (width > 50 && height > 25) {

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

            } else if (goal.getId() != null) {
                text += "   (" + goal.getId() + ")";
                g2.drawString(text, x, y + fm.getHeight());
            }

        }

    }

    public void drawTwin(Graphics2D g2) {

        GSnode node = super.getGSnode();
        String nodeType = node.getClass().toString();
        FontMetrics fm = g2.getFontMetrics();
        int x = getX();
        int y = getY();

        if (nodeType.contains("Twin")) {

            Twin twin = (Twin) node;
            Goal original = twin.getOriginal();
            String text = "";

            g2.drawRect(x, y, width, height);
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, width, height);
            g2.setColor(Color.BLACK);

            if (original.hasGop()) {

                GoalOrientedProposition gop = twin.getProposition();

                text += gop.getStatement() + "    " + gop.getPrefix();

                if (twin.getID() != null) {
                    text += "   (twin " + twin.getID() + ")";
                }

                int lineSep = 3;
                int stringWidth = fm.stringWidth(text);
                int strHeight = fm.getHeight();
                int strLength = text.length();

                if (width > 50 && height > 25) {

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

            } else if (twin.getID() != null) {
                text += "   (twin " + twin.getID() + ")";
                g2.drawString(text, x, y + fm.getHeight());
            }

        }

    }

}
