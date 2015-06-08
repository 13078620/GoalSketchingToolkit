/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import javax.swing.JPanel;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class GoalSketchingPanel extends JPanel {

    private int width;
    private int height;
    private ArrayList<Drawable> drawables;

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

    public void reset() {
        
        for (int i = drawables.size() - 1; i >= 0; i--) {
            drawables.remove(i);
        }
        repaint();
    }

}
