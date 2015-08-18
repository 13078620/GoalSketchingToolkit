/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University 
 * - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly 
 * prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, 
 * September 2015
 */
package goalsketchingtoolkit;

/**
 * Confidence factors used for expert judgement of goal graphs.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
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
