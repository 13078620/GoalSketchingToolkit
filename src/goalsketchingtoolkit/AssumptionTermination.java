/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * This class describes an assumption termination and it's graphical properties.
 *
 * @author Chris Berryman.
 */
public class AssumptionTermination extends GSnode {

    /**
     * The graphical properties of this assumption termination.
     */
    private GSnodeGraphics graphicalProperties;

    /**
     * Returns this assumption termination's graphical properties.
     *
     * @return the graphical properties of this assumption termination.
     */
    @Override
    public GSnodeGraphics getGraphicalProperties() {
        return graphicalProperties;
    }

    /**
     * Sets this assumption termination's graphical properties.
     *
     * @param graphicalProperties this assumption termination's graphical
     * properties.
     */
    @Override
    public void setGraphicalProperties(GSgraphics graphicalProperties) {
        this.graphicalProperties = (GSnodeGraphics) graphicalProperties;
    }

    /**
     * Returns a boolean to denote whether an assumption termination has
     * graphical properties or not.
     *
     * @return true if this assumption termination has graphical properties,
     * false otherwise.
     */
    @Override
    public boolean hasGraphics() {
        return this.graphicalProperties != null;
    }

}
