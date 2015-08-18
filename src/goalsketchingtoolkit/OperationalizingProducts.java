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

import java.util.ArrayList;

/**
 * This class describes an operationalizing products object, it's graphical
 * properties and list of products.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
 */
public class OperationalizingProducts extends GSnode {

    /**
     * This operationalizing products list of products.
     */
    private ArrayList<String> products;
    /**
     * The graphical properties of this operationalizing products.
     */
    private GSnodeGraphics graphicalProperties;

    /**
     * Constructs an operationalizing products object and initialises it's list
     * of products.
     */
    public OperationalizingProducts() {
        products = new ArrayList();
    }

    /**
     * Adds a product to this operationalizing products.
     *
     * @param product the product to add.
     */
    public void addProduct(String product) {
        products.add(product);
    }

    /**
     * Sets the products for this operationalizing products.
     *
     * @param products the products.
     */
    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }

    /**
     * Returns the list of products for this operationalizing products.
     *
     * @return the list of products.
     */
    public ArrayList<String> getProducts() {
        return products;
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
        graphicalProperties.setGSnode(this);
        this.graphicalProperties = (GSnodeGraphics) graphicalProperties;
    }

    /**
     * Returns a boolean to denote whether an operationalizing products has
     * graphical properties or not.
     *
     * @return true if this operationalizing products has graphical properties,
     * false otherwise.
     */
    @Override
    public boolean hasGraphics() {
        return this.graphicalProperties != null;
    }

}
