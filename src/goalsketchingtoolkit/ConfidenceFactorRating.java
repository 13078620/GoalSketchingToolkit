/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * This class is a key value pair which contains a confidence factor as the key
 * and a rating from the ordinal scale as the value.
 *
 * @author Chris Berryman.
 */
public class ConfidenceFactorRating {

    /**
     * The confidence factor used in this rating.
     */
    private final ConfidenceFactor key;
    /**
     * The rating from the ordinal scale used in this rating.
     */
    private final OrdinalScale value;

    /**
     * Constructs a confidence factor rating.
     *
     * @param cf the confidence factor.
     * @param os the rating from the ordinal scale.
     */
    public ConfidenceFactorRating(ConfidenceFactor cf, OrdinalScale os) {

        this.key = cf;
        this.value = os;
    }

    /**
     * Returns the confidence factor for this confidence factor rating.
     *
     * @return the confidence factor.
     */
    public ConfidenceFactor getKey() {
        return key;
    }

    /**
     * Returns the rating from the ordinal scale for this confidence factor
     * rating.
     *
     * @return the rating from the ordinal scale.
     */
    public OrdinalScale getValue() {
        return value;
    }

}
