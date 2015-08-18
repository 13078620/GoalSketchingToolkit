/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class AssumptionTerminationNodeDrawer  {

    private AssumptionTerminationNode node;

    public AssumptionTerminationNodeDrawer(AssumptionTerminationNode node) {
        this.node = node;
    }

    /**
     * Draws an assumption termination node.
     *
     * @param g2 the graphics context.
     */
    //@Override
    public void draw(Graphics2D g2) {

       // GraphNode parent = node.getParent();
        
       /* int x = (int) node.getX();
        int y = (int) node.getY();
        int width = node.getWidth();
        int height = node.getHeight();

        g2.setColor(node.getStrokeColor());
        g2.setStroke(node.getStroke());

        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, width, height);
        g2.draw(circle);
        
        int lineStartX = x + width / 2;
        int lineStartY = y;
        int lineToX = (int) parent.getX() + parent.getWidth() / 2;
        int lineToY = (int) parent.getY() + parent.getHeight();

        g2.drawLine(lineStartX, lineStartY, lineToX, lineToY);

    }*/

}
}