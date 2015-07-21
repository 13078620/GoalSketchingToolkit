/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * An annotation is comprised of a judgement and added to goal oriented
 * propositions.
 *
 * @author Chris Berryman.
 */
public class Annotation extends GSnode {

    /**
     * The judgement this annotation includes.
     */
    private Judgement judgement;
    /**
     * The graphical properties of this annotation.
     */
    private GSnodeGraphics graphicalProperties;

    /**
     * Constructs an annotation and initialises it's judgement.
     *
     * @param judgement the judgement for this annotation.
     */
    public Annotation(Judgement judgement) {
        this.judgement = judgement;
    }

    /**
     * Returns the judgement for this annotation.
     *
     * @return the judgement.
     */
    public Judgement getJudgement() {
        return judgement;
    }

    /**
     * Sets the judgement for this annotation.
     *
     * @param judgement the judgement to add.
     */
    public void setJudgement(Judgement judgement) {
        this.judgement = judgement;
    }
    
    /**
     * Returns this annotation's graphical properties.
     *
     * @return the graphical properties of this annotation.
     */
    @Override
    public GSnodeGraphics getGraphicalProperties() {
        return graphicalProperties;
    }

    /**
     * Sets this annotation's graphical properties.
     *
     * @param graphicalProperties this annotation's graphical properties.
     */
    @Override
    public void setGraphicalProperties(GSgraphics graphicalProperties) {
        this.graphicalProperties = (GSnodeGraphics) graphicalProperties;
    }
    
    /**
     * Returns a boolean to denote whether an annotation has
     * graphical properties or not.
     *
     * @return true if this annotation has graphical properties,
     * false otherwise.
     */
    @Override
    public boolean hasGraphics() {
        return this.graphicalProperties != null;
    }
    
}
