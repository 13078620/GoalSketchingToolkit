/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 * This class consists of operations associated with semantic entailment which
 * uses disjunction. an OR entailment can have zero to two goals as it's
 * children.
 *
 * @author Chris Berryman.
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
     * Adds a child node to this OR entailment which can be of the goal type.
     *
     * @throws UnsupportedOperationException() if the node added is anything
     * other than a Goal or if the goal list is already equal to the maximum
     * number of goals.
     * @see ANDentailment#addChild() addChild.
     * @param node the goal to add.
     */
    @Override
    public void addChild(GSnode node) {
        
        if (logic.isCorrect(node)) {
            //goals.add(node);
            //hasChildren = true;
            super.addChild(node);            
        }
        
        /*if(goals.size() >= MAXIMUM_GOALS) {
            throw new UnsupportedOperationException("Can only add a maximum of"
                    + " two goals to an OR entailment");
        } else {
            super.addChild(node);
        }*/
    }

    /**
     * Sets this OR entailment's children.
     *
     * @throws UnsupportedOperationException() if the size of the list of goals
     * exceeds two.
     * @param children the goals of this AND entailment.
     */
    @Override
    public void setChildren(ArrayList<GSnode> children) {

        if(children.size() > MAXIMUM_GOALS) {
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
    public GSgraphics getGraphicalProperties() {
        return graphicalProperties;
    }

    /**
     * Sets this OR entailment's graphical properties.
     *
     * @param graphicalProperties this OR entailment's graphical properties.
     */
    @Override
    public void setGraphicalProperties(GSgraphics graphicalProperties) {
        this.graphicalProperties = (GSorEntailmentGraphics) graphicalProperties;
    }
    
    
    
    
    
}
