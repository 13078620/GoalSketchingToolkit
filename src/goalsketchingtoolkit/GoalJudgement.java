/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

   
/**
 * Instances of this class can contain one confidence factor rating where the
 * confidence factor is REFINE, one confidence factor rating where the
 * confidence factor is ENGAGE and one significance factor rating where the
 * significance factor is VALUE.
 *
 * @author Chris Berryman.
 */
public class GoalJudgement implements Judgement {
    
    /**
     * The REFINE confidence factor rating for this goal judgement.
     */
    private ConfidenceFactorRating refineCfr;
    /**
     * The ENGAGE confidence factor rating for this goal judgement.
     */
    private ConfidenceFactorRating engageCfr;
    /**
     * The VALUE significance factor rating for this goal judgement.
     */
    private SignificanceFactorRating sfr;
    
    /**
     * Constructs an goal judgement and initialises it's REFINE and ENGAGE
     * confidence factor ratings, and VALUE significance factor rating.
     *
     * @param refineCfr the REFINE confidence factor rating for this goal judgement.
     * @param engageCfr the ENGAGE confidence factor rating for this goal judgement.
     * @param sfr the VALUE significance factor rating for this leaf judgement.
     */
    public GoalJudgement(ConfidenceFactorRating refineCfr, ConfidenceFactorRating engageCfr,
            SignificanceFactorRating sfr) {
        
        if (refineCfr.getKey().factor.equalsIgnoreCase("refine")) {
            this.refineCfr = refineCfr;
        } else {
            throw new UnsupportedOperationException("Goal judgements can only "
                    + "contain refine judgements and engage judgements in that order");
        }
        
        if (engageCfr.getKey().factor.equalsIgnoreCase("engage")) {
            this.engageCfr = refineCfr;
        } else {
            throw new UnsupportedOperationException("Goal judgements can only "
                    + "contain refine judgements and engage judgements in that order");
        }
        
        if (sfr.getKey().factor.equalsIgnoreCase("value")) {
            this.sfr = sfr;
        } else {
            throw new UnsupportedOperationException("Goal judgements can only "
                    + "contain cost ratings.");
        }
    }
    
    /**
     * Sets a REFINE or ENGAGE confidence factor rating for this goal judgement.
     *
     * @param cfr the REFINE or ENGAGE confidence factor rating to add.
     */
    @Override
    public void addConfidenceFactorRating(ConfidenceFactorRating cfr) {
        
        if (cfr.getKey().factor.equalsIgnoreCase("refine")) {
            this.refineCfr = cfr;
        } else if (cfr.getKey().factor.equalsIgnoreCase("engage")){
            this.engageCfr = cfr;
        } else {
            throw new UnsupportedOperationException("Goal judgements can only "
                    + "contain achieve or engage judgements.");
        }
    }

    /**
     * Returns the REFINE confidence factor rating for this goal judgement.
     *
     * @return the REFINE confidence factor rating.
     */
    public ConfidenceFactorRating getRefineConfidenceFactorRating() {
        return refineCfr;
    }
    
    /**
     * Returns the ENGAGE confidence factor rating for this goal judgement.
     *
     * @return the ENGAGE confidence factor rating.
     */
    public ConfidenceFactorRating getEngageConfidenceFactorRating() {
        return engageCfr;
    }
    
    /**
     * Sets the VALUE significance factor rating for this goal judgement
     *
     * @param sfr the VALUE significance factor rating to add.
     */
    public void addSignificanceFactorRating(SignificanceFactorRating sfr) {
        if (sfr.getKey().factor.equalsIgnoreCase("value")) {
            this.sfr = sfr;
        } else {
            throw new UnsupportedOperationException("Goal judgements can only "
                    + "contain value ratings.");
        }
    }
    
    /**
     * Returns the VALUE significance factor rating for this leaf judgement.
     *
     * @return the VALUE significance factor rating.
     */
    public SignificanceFactorRating getSignificanceFactorRating() {
        return sfr;
    }
    
}