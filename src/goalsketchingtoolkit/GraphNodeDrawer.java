/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.awt.Graphics2D;
import java.awt.BasicStroke;

/*import javafx.scene.Scene;
 import javafx.scene.text.Text;
 import javafx.scene.text.*;
 import javafx.stage.Stage;
 import javafx.scene.shape.Rectangle;
 import javafx.beans.property.StringProperty;

 import javafx.geometry.VPos;*/
//import java.awt.TextArea;
//import javafx.scene.control.TextArea;
//import javafx.scene.paint.Color;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

import java.awt.Color;

/**
 * Describes a class that can draw a graph node.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class GraphNodeDrawer implements Drawable {

    private GraphNode graphNode;

    /**
     * Constructs a graph node drawer.
     *
     * @param graphNode the graph node to be drawn.
     */
    public GraphNodeDrawer(GraphNode graphNode) {

        this.graphNode = graphNode;

    }

    /**
     * Draws a graph node.
     *
     * @param g2 the graphics context.
     */
    @Override
    public void draw(Graphics2D g2) {

        //MAX_FONTSIZE
        //Font font = new Font("Courier new", Font.BOLD, 100);
        FontMetrics fm = g2.getFontMetrics();

        int x = (int) graphNode.getX();
        int y = (int) graphNode.getY();
        int width = graphNode.getWidth();
        int height = graphNode.getHeight();

        if (graphNode.isSelected()) {
            //System.out.println("selected:"+graphNode.getId());
            graphNode.setStrokeColor(Color.RED);
        } else {
            graphNode.setStrokeColor(Color.BLACK);
        }

        g2.setColor(graphNode.getStrokeColor());
        g2.setStroke(graphNode.getStroke());

        if (graphNode.getProposition() != null) {
            //System.out.println(graphNode.getProposition().getPrefix());
            String text = graphNode.getProposition().getStatement() + "    " + graphNode.getProposition().getPrefix();
            if (graphNode.getId() != null) {
                text += "   (" + graphNode.getId() + ")";
            }
            // resize string
            /*Rectangle2D fontRec = font.getStringBounds(text, g2.getFontMetrics().getFontRenderContext());
             while (fontRec.getWidth() >= width * 0.95f || fontRec.getHeight() >= height * 0.95f) {
             Font smallerFont = font.deriveFont((float) (font.getSize() - 2));
             font = smallerFont;
             g2.setFont(smallerFont);
             fontRec = smallerFont.getStringBounds(text, g2.getFontMetrics().getFontRenderContext());
             }

             // center string
             //FontMetrics fm = g2.getFontMetrics();
             float stringWidth = fm.stringWidth(text);
             int fontX = (int) (x + width / 2 - stringWidth / 2);
             int fontY = (int) (y + height / 2);
             g2.drawString(text, fontX, fontY);*/
            //String prefix = graphNode.getProposition().getPrefix();
            //System.out.println(prefix);
            int lineSep = 3;
            int stringWidth = fm.stringWidth(text);
            int strHeight = fm.getHeight();
            int strLength = text.length();

            if (graphNode.getWidth() > 50 && graphNode.getHeight() > 25) {

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

        if (graphNode.isParent()) {

            /*int x = (int) graphNode.getX();
             int y = (int) graphNode.getY();
             int width = graphNode.getWidth();
             int height = graphNode.getHeight();*/
            /*int lineSep = 3;
             int stringWidth = fm.stringWidth(text);
             int strHeight = fm.getHeight();
             int strLength = text.length();*/

            /*if (graphNode.getWidth() > 50 && graphNode.getHeight() > 50) {
             int charPerLine = (int) (strLength * width / (double) stringWidth);

             if (charPerLine >= strLength) {
             g2.drawString(" " + text, x, y + strHeight);
             } else {
             int lines = strLength / charPerLine;
             int skip = 0;
             for (int i = 1; i <= lines; i++) {
             String sTemp = text.substring(skip, skip + charPerLine - 1);
             if (!text.substring(skip + charPerLine - 1, skip + charPerLine).equals(" ") && !text.substring(skip + charPerLine - 2, skip + charPerLine - 1).equals(" ")) {

             sTemp += "- ";
             }
             g2.drawString(" " + sTemp.trim(), x, y + i * strHeight + (i - 1) * lineSep);
             skip += charPerLine - 1;
             }
             g2.drawString(text.substring(skip, strLength).trim(), x, y + (lines + 1) * strHeight + (lines) * lineSep);

             }
             } */
            g2.drawRect(x, y, width, height);
            float dash1[] = {10.0f};
            BasicStroke dashed = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
            g2.setStroke(dashed);
            int lineLength = 80;
            int lineStartX = x + width / 2;
            int lineStartY = y + height;
            int lineToX = x + width / 2;
            int lineToY = y + height + lineLength;
            g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

            g2.setStroke(graphNode.getStroke());
            int ovalDiameter = 20;
            int ovalX = x + width / 2 - ovalDiameter / 2;
            int ovalY = y + height + lineLength;

            g2.drawOval(ovalX, ovalY, ovalDiameter, ovalDiameter);

        }

        if (graphNode.isChild()) {

            GraphNode parent = graphNode.getParent();

            /*int x = (int) graphNode.getX();
             int y = (int) graphNode.getY();
             int width = graphNode.getWidth();
             int height = graphNode.getHeight();*/
            g2.drawRect(x, y, width, height);

            int lineStartX = x + width / 2;
            int lineStartY = y;
            int lineToX = (int) parent.getRefinementCentreX();
            int lineToY = (int) parent.getRefinementCentreY();

            double[] refinementIntersection = parent.getRefinementIntersection(lineStartX, lineStartY, lineToX, lineToY);
            
            lineToX = (int) refinementIntersection[0];
            lineToY = (int) refinementIntersection[1];
            
            g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

        }

        /*if (graphNode.isParentAndChild()) {

            /*int x = (int) graphNode.getX();
             int y = (int) graphNode.getY();
             int width = graphNode.getWidth();
             int height = graphNode.getHeight();*/
           /* g2.drawRect(x, y, width, height);

            float dash1[] = {10.0f};
            BasicStroke dashed = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
            g2.setStroke(dashed);
            int lineLength = 80;
            int lineStartX = x + width / 2;
            int lineStartY = y + height;
            int lineToX = x + width / 2;
            int lineToY = y + height + lineLength;
            g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

            g2.setStroke(graphNode.getStroke());
            int ovalDiameter = 20;
            int ovalX = x + width / 2 - ovalDiameter / 2;
            int ovalY = y + height + lineLength;

            g2.drawOval(ovalX, ovalY, ovalDiameter, ovalDiameter);

            GraphNode parent = graphNode.getParent();

            g2.drawRect(x, y, width, height);

            int lineStartX2 = x + width / 2;
            int lineStartY2 = y;
            int lineToX2 = (int) parent.getRefinementBottomX();
            int lineToY2 = (int) parent.getRefinementBottomY();

            g2.drawLine(lineStartX2, lineStartY2, lineToX2, lineToY2);

        } */

    }

}
