/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * The user of this interface has control over what types of confidence factor
 * ratings are added to a judgement. Implementations should specify what sort of
 * confidence factor ratings can be added to a concrete judgement.
 *
 * @author Chris Berryman.
 */
public interface Judgement {
    
    /**
     * Sets the confidence factor rating.
     */
    public void addConfidenceFactorRating(ConfidenceFactorRating cfr);

}
