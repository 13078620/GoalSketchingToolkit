/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

/**
 * This class describes twin goal used in goal sketching, a twin goal does not
 * have responsibility assignments or refinements as these are defined in the
 * original goal.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
 */
public class Twin extends GSnode {

    /**
     * A reference to the original goal.
     */
    private Goal original;

    /**
     * The graphical properties of this twin goal.
     */
    private GSnodeGraphics graphicalProperties;

    /**
     * A new instance of the GOP from the original goal.
     */
    private GoalOrientedProposition gop;

    /**
     * A new instance of the GOP from the original goal.
     */
    private String temporaryId;

    /**
     * Constructs a twin with no original goal.
     */
    public Twin() {

    }

    /**
     * Constructs a twin goal with a reference to the original goal.
     *
     * @param original the original goal.
     */
    public Twin(Goal original) {
        this.original = original;
        if (original.hasGop()) {
            GoalType gt = original.getProposition().getGoalType();
            String statement = original.getProposition().getStatement();
            gop = new GoalOrientedProposition(gt, statement);
        //} else {
            //    gop = new GoalOrientedProposition();
        }
    }

    /**
     *
     * @param g
     */
    public void setOriginalGoal(Goal g) {
        this.original = g;
        if (original.hasGop()) {
            GoalType gt = original.getProposition().getGoalType();
            String statement = original.getProposition().getStatement();
            gop = new GoalOrientedProposition(gt, statement);
        }
    }

    /**
     * Returns the original goal for this twin.
     *
     * @return the original goal.
     */
    public Goal getOriginal() {
        return this.original;
    }

    /**
     * Returns the ID of the original goal this twin goal is a twin of.
     *
     * @return the ID of the original goal of this twin.
     */
    public String getID() {
        return original.getId();
    }

    /**
     * sets a temporary ID for parsing purposes.
     *
     * @param id the temporary ID.
     */
    public void setTemporaryID(String id) {
        this.temporaryId = id;
    }

    /**
     * Returns the temporary ID for parsing purposes.
     *
     * @return the temporary ID.
     */
    public String getTemporaryID() {
        return temporaryId;
    }

    /**
     * Returns the fit acceptance test of the original goal this twin goal is a
     * twin of.
     *
     * @return the fit acceptance test of the original goal of this twin.
     */
    public String getFit() {
        return original.getFit();
    }

    /**
     * Returns a new instance of the GOP from the original goal of this twin.
     *
     * @return a new instance of the GOP from the original goal.
     */
    public GoalOrientedProposition getProposition() {
        return gop;
    }

    /**
     * Returns this twin goal's graphical properties.
     *
     * @return the graphical properties of this twin goal.
     */
    @Override
    public GSnodeGraphics getGraphicalProperties() {
        return graphicalProperties;
    }

    /**
     * Sets this twin goal's graphical properties.
     *
     * @param graphicalProperties this twin goal's graphical properties.
     */
    @Override
    public void setGraphicalProperties(GSgraphics graphicalProperties) {
        graphicalProperties.setGSnode(this);
        this.graphicalProperties = (GSnodeGraphics) graphicalProperties;
    }

    /**
     * Returns a boolean to denote whether a twin goal has graphical properties
     * or not.
     *
     * @return true if this twin goal has graphical properties, false otherwise.
     */
    @Override
    public boolean hasGraphics() {
        return this.graphicalProperties != null;
    }

    /**
     * Updates a twin's GOP based on changes to the original goal.
     */
    public void updateGOP() {
        if (original.hasGop()) {
            GoalType gt = original.getProposition().getGoalType();
            String statement = original.getProposition().getStatement();
            gop.setPrefix(gt);
            gop.setStatement(statement);
        }

    }

}
