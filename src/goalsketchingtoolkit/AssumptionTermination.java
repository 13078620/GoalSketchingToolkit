/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

/**
 * This class describes an assumption termination and it's graphical properties.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
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
        graphicalProperties.setGSnode(this);
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
