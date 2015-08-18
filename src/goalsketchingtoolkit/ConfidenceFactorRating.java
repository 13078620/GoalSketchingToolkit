/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

/**
 * This class is a key value pair which contains a confidence factor as the key
 * and a rating from the ordinal scale as the value.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
 */
public class ConfidenceFactorRating {

    /**
     * The confidence factor used in this rating.
     */
    private final ConfidenceFactor key;
    
    /**
     * The rating from the ordinal scale used in this rating.
     */
    private final GSordinalScale value;

    /**
     * Constructs a confidence factor rating.
     *
     * @param cf the confidence factor.
     * @param os the rating from the ordinal scale.
     */
    public ConfidenceFactorRating(ConfidenceFactor cf, GSordinalScale os) {

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
    public GSordinalScale getValue() {
        return value;
    }

}
