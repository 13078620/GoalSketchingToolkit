/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

/**
 * Instances of this class can contain one confidence factor rating where the
 * confidence factor is ASSUME.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
 */
public class AssumptionJudgement implements Judgement {

    /**
     * The ASSUME confidence factor rating for this assumption judgement.
     */
    private ConfidenceFactorRating cfr;

    /**
     * Constructs an assumption judgement with no confidence factor rating.
     */
    public AssumptionJudgement() {
        
    }
    
    /**
     * Constructs an assumption judgement and initialises it's ASSUME 
     * confidence factor rating.
     *
     * @param cfr the ASSUME confidence factor rating for this assumption judgement.
     */
    public AssumptionJudgement(ConfidenceFactorRating cfr) {
        if (cfr.getKey().factor.equalsIgnoreCase("assume")) {
            this.cfr = cfr;
        } else {
            throw new UnsupportedOperationException("Assumption judgements can only "
                    + "contain assumption judgements.");
        }
    }

    /**
     * Sets the ASSUME confidence factor rating for this assumption judgement
     *
     * @param cfr the ASSUME confidence factor rating to add.
     */
    @Override
    public void addConfidenceFactorRating(ConfidenceFactorRating cfr) {
        if (cfr.getKey().factor.equalsIgnoreCase("assume")) {
            this.cfr = cfr;
        } else {
            throw new UnsupportedOperationException("Assumption judgements can only "
                    + "contain assumption judgements.");
        }
    }

    /**
     * Returns the ASSUME confidence factor rating for this assumption judgement.
     *
     * @return the ASSUME confidence factor rating.
     */
    public ConfidenceFactorRating getConfidenceFactorRating() {
        return cfr;
    }

}
