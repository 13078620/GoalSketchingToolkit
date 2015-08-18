/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

/**
 * The user of this interface has control over what types of confidence factor
 * ratings are added to a judgement. Implementations should specify what sort of
 * confidence factor ratings can be added to a concrete judgement.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
 */
public interface Judgement {

    /**
     * Sets the confidence factor rating.
     *
     * @param cfr the confidence factor rating to add.
     */
    public void addConfidenceFactorRating(ConfidenceFactorRating cfr);

}
