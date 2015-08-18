/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University 
 * - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly 
 * prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, 
 * September 2015
 */
package goalsketchingtoolkit;

import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * The panel which goal sketching graphics objects are drawn on.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class GoalSketchingPanel extends JPanel {

    private final int width;
    private final int height;
    private final ArrayList<Drawable> drawables;

    /**
     * Constructs a goal sketching panel with a given width and height.
     *
     * @param width the width of the panel.
     * @param height the height of the panel.
     */
    public GoalSketchingPanel(int width, int height) {

        this.height = height;
        this.width = width;
        this.drawables = new ArrayList<>();

    }

    /**
     * Adds a drawable object to the list of drawable objects.
     *
     * @param drawable the drawable object to add.
     */
    public void addDrawable(Drawable drawable) {
        this.drawables.add(drawable);
    }

    /**
     * Removes a a drawable object from the list of drawable objects.
     *
     * @param d the drawable to remove.
     */
    public void removeDrawable(Drawable d) {
        this.drawables.remove(d);
    }

    /**
     * Returns the list of drawables for this goal sketching panel.
     *
     * @return the list of drawables.
     */
    public ArrayList<Drawable> getDrawables() {
        return this.drawables;
    }

    /**
     * Paints the goal sketching panel and draws the goal sketching nodes, the
     * panel is not displayed as opaque as super's implementation is invoked.
     *
     * @param g the graphics object to protect.
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);

        for (Drawable d : drawables) {
            d.draw(g2);
        }
    }

    /**
     * Resets the list of drawables for this panel.
     */
    public void reset() {

        for (int i = drawables.size() - 1; i >= 0; i--) {
            drawables.remove(i);
        }
        repaint();
    }

}
