/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.awt.Graphics2D;

/**
 * Describes any class that can draw a goal sketching node.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public interface Drawable {

    /**
     * Draws a drawable object.
     *
     * @param g2 the graphics context.
     */
    public void draw(Graphics2D g2);
    
    public boolean contains(int x, int y);

}
