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
 * Significance factors used for expert judgement of goal graphs.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
 */
public enum SignificanceFactor {

    /**
     * Value SF.
     */
    Value("VALUE"),
    /**
     * Cost SF.
     */
    Cost("COST");

    /**
     * The SF used in an expert judgement.
     */
    final String factor;

    /**
     * Constructs a Significance factor.
     *
     * @param factor either VALUE or COST.
     */
    SignificanceFactor(String factor) {
        this.factor = factor;
    }

}
