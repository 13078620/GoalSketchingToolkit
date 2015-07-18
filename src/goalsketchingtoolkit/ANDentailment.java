/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 * This class consists of operations associated with semantic entailment which
 * uses conjunction. an and entailment can have zero to many goals as it's
 * children.
 *
 * @author Chris Berryman.
 */
public class ANDentailment extends GSnode {

    /**
     * A list of goals for this AND entailment.
     */
    private ArrayList<GSnode> goals;
    /**
     * The graphical properties object for this AND entailment.
     */
    private GSentailmentGraphics graphicalProperties;

    public ANDentailment() {
         goals = new ArrayList();
    }
    
    /**
     * Constructs an AND entailment and initialises it's list of goals.
     *
     * @param graphicalProperties the graphical properties of this and
     * entailment.
     */
    public ANDentailment(GSentailmentGraphics graphicalProperties) {

        goals = new ArrayList();
        this.graphicalProperties = graphicalProperties;
    }

    /**
     * Returns a reference to this AND entailment because it is a composite.
     *
     * @see GSnode#getCompositeGSnode() getCompositeGSnode.
     * @return a reference to this AND entailment.
     */
    @Override
    public GSnode getCompositeGSnode() {
        return this;
    }

    
    @Override
    public void addChild(GSnode node) {

        if (node.getClass().toString().contains("Goal")) {
            /*Goal g = (Goal) node;
             for(GSnode c : getChildren()) {
             if (g.equals(c)){
             throw new IllegalArgumentException();
             }
             }*/
            super.addChild(node);
        } else {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public void setChildren(ArrayList<GSnode> children) {

        for (GSnode c : children) {
            if (!c.getClass().toString().contains("Goal")) {
                throw new IllegalArgumentException();
            }
        }

        super.setChildren(children);

    }

}
