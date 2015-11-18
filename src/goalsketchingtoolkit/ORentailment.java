/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 * This class consists of operations associated with semantic entailment which
 * uses disjunction. an OR entailment can have zero to two goals as it's
 * children.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
 */
public class ORentailment extends ANDentailment {

    /**
     * The maximum goals an OR entailment can have.
     */
    
    public final static int MAXIMUM_GOALS = 2;
    /**
     * The logic for this AND entailment.
     */
    
    private GoalSketchingLogic logic;
    /**
     * The graphical properties object for this OR entailment.
     */
    
    private GSorEntailmentGraphics graphicalProperties;

    /**
     * Constructs an OR entailment and initialises it's list of goals.
     */
    public ORentailment() {
        super();
        logic = new ORentailmentLogic(this);
    }

    /**
     * Constructs an OR entailment and initialises it's list of goals.
     *
     * @param graphicalProperties the graphical properties of this OR
     * entailment.
     */
    public ORentailment(GSorEntailmentGraphics graphicalProperties) {
        super();
        logic = new ORentailmentLogic(this);
        this.graphicalProperties = graphicalProperties;
    }

    /**
     * Adds a child node to this OR entailment which can be of the goal or twin
     * type.
     *
     * @param node the goal to add.
     */
    @Override
    public void addChild(GSnode node) {

        if (logic.isCorrect(node)) {
            super.addChild(node);
        }
    }

    /**
     * Sets the children for this OR entailment.
     *
     * @param children the goals of this AND entailment.
     */
    @Override
    public void setChildren(ArrayList<GSnode> children) {

        if (children.size() > MAXIMUM_GOALS) {
            throw new UnsupportedOperationException("An OR entailment can only "
                    + " have a maximum of"
                    + " two goals");
        }
        super.setChildren(children);
    }

    /**
     * Returns this OR entailment's graphical properties.
     *
     * @return the graphical properties of this OR entailment.
     */
    @Override
    public GSorEntailmentGraphics getGraphicalProperties() {
        return graphicalProperties;
    }

    /**
     * Sets this OR entailment's graphical properties.
     *
     * @param graphicalProperties this OR entailment's graphical properties.
     */
    @Override
    public void setGraphicalProperties(GSgraphics graphicalProperties) {
        graphicalProperties.setGSnode(this);
        this.graphicalProperties = (GSorEntailmentGraphics) graphicalProperties;
    }

    /**
     * Returns a boolean to denote whether an OR entailment has graphical
     * properties or not.
     *
     * @return true if this OR entailment has graphical properties, false
     * otherwise.
     */
    @Override
    public boolean hasGraphics() {
        return this.graphicalProperties != null;
    }

}
