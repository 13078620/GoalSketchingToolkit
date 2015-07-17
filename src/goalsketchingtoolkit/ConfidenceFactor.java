/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * Confidence factors used for expert judgement of goal graphs.
 *
 * @author Chris Berryman.
 */
public enum ConfidenceFactor {

    /**
     * Assume CF.
     */
    Assume("ASSUME"),
    /**
     * Achieve CF.
     */
    Achieve("ACHIEVE"),
    /**
     * Refine CF.
     */
    Refine("REFINE"),
    /**
     * Engage CF.
     */
    Engage("ENGAGE");

    /**
     * The CF used in an expert judgement.
     */
    final String factor;

    /**
     * Constructs a confidence factor.
     * 
     * @param factor either ASSUME, ACHIEVE, REFINE or ENGAGE.
     */
    ConfidenceFactor(String factor) {
        this.factor = factor;
    }

}
