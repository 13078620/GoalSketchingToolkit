/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * This class is a key value pair which contains a significance factor as the 
 * key and a rating between 1 and 100 as the value.
 *
 * @author Chris Berryman.
 */
public class SignificanceFactorRating {
    
    /**
     * The significance factor used in this rating.
     */
    private final SignificanceFactor key;
    /**
     * The rating from the ordinal scale used in this rating.
     */
    private final int value;

    /**
     * Constructs a significance factor rating.
     *
     * @param sf the confidence factor.
     * @param i the rating from the ordinal scale.
     */
    public SignificanceFactorRating(SignificanceFactor sf, int i) {

        this.key = sf;
        this.value = i;
    }

    /**
     * Returns the confidence factor for this confidence factor rating.
     *
     * @return the confidence factor.
     */
    public SignificanceFactor getKey() {
        return key;
    }

    /**
     * Returns the rating from the ordinal scale for this confidence factor
     * rating.
     *
     * @return the rating from the ordinal scale.
     */
    public int getValue() {
        return value;
    }
    
}
