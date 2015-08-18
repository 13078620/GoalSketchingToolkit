/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 * This class describes operationalizers in the exploitation domain and their
 * sub domains.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015
 */
public class Operationalizer {

    private String agentName;
    private ArrayList<String> subDomains;

    public Operationalizer() {
    }

    /**
     * Constructs an operationalizer with a specified agent name.
     *
     * @param agentName the name of the operationalizer.
     */
    public Operationalizer(String agentName) {

        this.agentName = agentName;
        this.subDomains = new ArrayList<>();
    }

    /**
     * Sets the agent name for this operationalizer. *
     *
     * @param agentName the agent name.
     */
    public void setAgentName(String agentName) {

        this.agentName = agentName;

    }

    /**
     * Returns the agent name for this operationalizer.
     *
     * @return the agent name.
     */
    public String getAgentName() {

        return this.agentName;

    }

    /**
     * Appends a sub domain to the sub domain list.
     *
     * @param subDomain the sub domain to add.
     */
    public void addSubDomain(String subDomain) {

        this.subDomains.add(subDomain);

    }

    /**
     * Appends a sub domain to the sub domain list.
     *
     * @return an array list containing the sub domains.
     */
    public ArrayList getSubDomains() {

        return subDomains;

    }

    /**
     * Sets the sub domains of this operationalzer.
     *
     * @param subDomains the sub domains to set.
     */
    public void setSubDomains(ArrayList subDomains) {

        this.subDomains = subDomains;

    }

}
