/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;
/**
 * This class describes an operationalizing products object,
 * it's graphical properties and list of products.
 *
 * @author Chris Berryman.
 */
public class OperationalizingProducts extends GSnode{
    
    /**
     * This operationalizing products list of products.
     */
    private ArrayList<String> products;
    /**
     * The graphical properties of this operationalizing products.
     */
    private GSnodeGraphics graphicalProperties;
    
    /**
     * Constructs an operationalizing products object and initialises 
     * it's list of products.
     */
    public OperationalizingProducts() {
        products = new ArrayList();
    }
    
    /**
     * Returns this operationalizing product's graphical properties.
     *
     * @return the graphical properties of this operationalizing product.
     */
    @Override
    public GSnodeGraphics getGraphicalProperties() {
        return graphicalProperties;
    }

    /**
     * Sets this operationalizing product's graphical properties.
     *
     * @param graphicalProperties this assumption operationalizing product's 
     * graphical properties.
     */
    @Override
    public void setGraphicalProperties(GSgraphics graphicalProperties) {
        this.graphicalProperties = (GSnodeGraphics) graphicalProperties;
    }
    
}
