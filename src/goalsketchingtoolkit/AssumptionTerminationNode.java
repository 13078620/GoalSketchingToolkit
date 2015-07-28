/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.awt.geom.Point2D;

/**
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class AssumptionTerminationNode  {

    private double xTop, yLeft, radius, xCenter, yCenter;
    //private GraphNode par;
    //private Point2D.Double point;

     public AssumptionTerminationNode() {
         
     }
    
   /* public AssumptionTerminationNode(double x, double y, int width, int height) {

        super(x, y, width, height);
        this.radius = width / 2;
        this.xCenter = xTop + width / 2;
        this.yCenter = yLeft + width / 2;
    }

    /**
     *
     * @param X
     * @param Y
     * @param p
     * @return
     */
   // @Override
    //public boolean contains(int X, int Y) {

    //    Point2D.Double p = new Point2D.Double(X, Y);
    //    return ((p.x - xCenter) * (p.x - xCenter)) + ((p.y - yCenter) * (p.y - yCenter)) < (radius * radius);

    //}
    
    

    /**
     * Sets the reference to this operationalAssumption Terminationizer node's
     * parent.
     *
     * @param parent the specified parent graph node for this Assumption
     * Termination node.
     */
    //public void setParent(GraphNode parent) {

    //    this.par = parent;

   // }

    /**
     * Gets the reference to this Assumption Termination node's parent.
     *
     * @return the parent graph node of this Assumption Termination node.
     */
   // public GraphNode getParent() {

    //    return this.par;

    //}

}
