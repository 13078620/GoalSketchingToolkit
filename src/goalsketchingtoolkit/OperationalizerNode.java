/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 * Describes an operationalizer node.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class OperationalizerNode  {

    /**
     * The list of operationalizers for this operationalizer node.
     */
    private ArrayList<Operationalizer> operationalizers = new ArrayList<>();

   /* private GraphNode par;

    public OperationalizerNode() {
        super();
    }

    /**
     *
     * @param x the starting x position of the operationalizer node.
     * @param y the starting y position of the operationalizer node.
     * @param width the width of the operationalizer node
     * @param height the height of the operationalizer node.
     * @param operationalizers the operationalizers for the operationalizer
     * node.
     
    public OperationalizerNode(double x, double y, int width, int height, ArrayList operationalizers) {

        super(x, y, width, height);
        this.operationalizers = operationalizers;

    }

    /**
     * Appends an operationalizer to this operationalizer node's list of
     * operationalizers.
     *
     * @param operationalizer the operationalizer to add.
     
    public void addOperationalizer(Operationalizer operationalizer) {

        this.operationalizers.add(operationalizer);

    }

    /**
     * Removes an operationalizer from this operationalizer node's list of
     * operationalizers.
     *
     * @param agentName the agent name of the operationalizer to remove.
     
    public void removeOperationalizer(String agentName) {

        for (int i = 0; i < operationalizers.size(); i++) {
            if (this.operationalizers.get(i).getAgentName().equalsIgnoreCase(agentName)) {
                this.operationalizers.remove(i);
            }
        }

    }

    /**
     * Sets this operationalizer node's list of operationalizers.
     *
     * @param operationalizers the list of operationalizers to set.
     
    public void setOperationalizers(ArrayList operationalizers) {

        this.operationalizers = operationalizers;

    }

    /**
     * Returns this operationalizer node's list of operationalizers.
     *
     * @return the list of operationalizers.
     
    public ArrayList getOperationalizers() {

        return this.operationalizers;

    }

    /**
     * Sets the reference to this operationalizer node's parent.
     *
     * @param parent the specified parent graph node for this operationalizer
     * node.
     
    public void setParent(GraphNode parent) {

        this.par = parent;

    }

    /**
     * Gets the reference to this operationalizer node's parent.
     *
     * @return the parent graph node of this operationalizer node.
     
    public GraphNode getParent() {

        return this.par;

    }

    /**
     * Sets the sub domains for an operationalizer of this operationalizer node.
     *
     * @param op the operationalizer to set the sub domains.
     * @param subDomains the sub domains to set.
     
    public void setOperationalizerSubDomains(Operationalizer op, ArrayList subDomains) {

    }*/

}
