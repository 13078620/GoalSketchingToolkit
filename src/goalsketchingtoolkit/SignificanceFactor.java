/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * Significance factors used for expert judgement of goal graphs.
 *
 * @author Chris Berryman
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
