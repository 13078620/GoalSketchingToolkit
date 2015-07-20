/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * Instances of this class can contain one confidence factor rating where the
 * confidence factor is ACHIEVE and one significance factor rating where the
 * significance factor is COST.
 *
 * @author Chris Berryman.
 */
public class LeafJudgement implements Judgement {
    
    /**
     * The ACHIEVE confidence factor rating for this leaf judgement.
     */
    private ConfidenceFactorRating cfr;
    /**
     * The COST significance factor rating for this leaf judgement.
     */
    private SignificanceFactorRating sfr;
    
    /**
     * Constructs an leaf judgement and initialises it's ACHIEVE confidence 
     * factor rating and COST significance factor rating.
     *
     * @param cfr the ACHIEVE confidence factor rating for this leaf judgement.
     * @param sfr the COST significance factor rating for this leaf judgement.
     */
    public LeafJudgement(ConfidenceFactorRating cfr, SignificanceFactorRating sfr) {
        
        if (cfr.getKey().factor.equalsIgnoreCase("achieve")) {
            this.cfr = cfr;
        } else {
            throw new UnsupportedOperationException("Leaf judgements can only "
                    + "contain achieve judgements.");
        }
        
        if (sfr.getKey().factor.equalsIgnoreCase("cost")) {
            this.sfr = sfr;
        } else {
            throw new UnsupportedOperationException("Leaf judgements can only "
                    + "contain cost ratings.");
        }
    }
    
    /**
     * Sets the ACHIEVE confidence factor rating for this leaf judgement
     *
     * @param cfr the ACHIEVE confidence factor rating to add.
     */
    @Override
    public void addConfidenceFactorRating(ConfidenceFactorRating cfr) {
        if (cfr.getKey().factor.equalsIgnoreCase("achieve")) {
            this.cfr = cfr;
        } else {
            throw new UnsupportedOperationException("Leaf judgements can only "
                    + "contain achieve judgements.");
        }
    }

    /**
     * Returns the ACHIEVE confidence factor rating for this leaf judgement.
     *
     * @return the confidence factor rating.
     */
    public ConfidenceFactorRating getConfidenceFactorRating() {
        return cfr;
    }
    
    /**
     * Sets the COST significance factor rating for this leaf judgement
     *
     * @param sfr the COST significance factor rating to add.
     */
    public void addSignificanceFactorRating(SignificanceFactorRating sfr) {
        if (sfr.getKey().factor.equalsIgnoreCase("cost")) {
            this.sfr = sfr;
        } else {
            throw new UnsupportedOperationException("Leaf judgements can only "
                    + "contain cost ratings.");
        }
    }
    
    /**
     * Returns the ACHIEVE significance factor rating for this leaf judgement.
     *
     * @return the ACHIEVE significance factor rating.
     */
    public SignificanceFactorRating getSignificanceFactorRating() {
        return sfr;
    }
    
}
