/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Describes a class that can draw an operationalizer node.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class OperationalizerNodeDrawer implements Drawable {

    private OperationalizerNode operationalizerNode;

    /**
     * Constructs a operationalizer node drawer.
     *
     * @param operationalizerNode the operationalizer node to be drawn.
     */
    public OperationalizerNodeDrawer(OperationalizerNode operationalizerNode) {

        this.operationalizerNode = operationalizerNode;

    }

    /**
     * Draws an operationalizer node.
     *
     * @param g2 the graphics context.
     */
    @Override
    public void draw(Graphics2D g2) {

        GraphNode parent = operationalizerNode.getParent();
        FontMetrics fm = g2.getFontMetrics();        

        int x = (int) operationalizerNode.getX();
        int y = (int) operationalizerNode.getY();
        int width = operationalizerNode.getWidth();
        int height = operationalizerNode.getHeight();
        
        if (operationalizerNode.isSelected()) {
            operationalizerNode.setStrokeColor(Color.RED);
        } else {
            operationalizerNode.setStrokeColor(Color.BLACK);
        }
        
        g2.setColor(operationalizerNode.getStrokeColor());
        g2.setStroke(operationalizerNode.getStroke());

        if (operationalizerNode.getOperationalizers().size() >= 1) {

            ArrayList<Operationalizer> ops = operationalizerNode.getOperationalizers();
            String text = "";

            for (int i = 0; i < ops.size(); i++) {
                ArrayList<String> sdText = ops.get(i).getSubDomains();
                String s = "";
                /*if (sdText.size() >= 1) {
                    s += "[";
                    for (int j = 0; j < sdText.size(); j++) {
                        s += sdText.get(j) + ", ";
                    }
                    s += "]";
                } */
                if (ops.indexOf(ops.get(i)) != ops.size() - 1) {
                    text += ops.get(i).getAgentName() + s + ", ";
                } else {
                    text += ops.get(i).getAgentName() + s;
                }
                
            } 

            int lineSep = 3;
            int stringWidth = fm.stringWidth(text);
            int strHeight = fm.getHeight();
            int strLength = text.length();
            if (operationalizerNode.getWidth() > 50 && operationalizerNode.getHeight() > 25) {

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

        }

        g2.drawOval(x, y, width, height);

        int lineStartX = x + width / 2;
        int lineStartY = y;
        int lineToX = (int) parent.getX() + parent.getWidth() / 2;
        int lineToY = (int) parent.getY() + parent.getHeight();

        g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

    }

}
