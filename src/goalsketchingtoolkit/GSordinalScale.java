/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

/**
 * Ratings from the ordinal scale used as confidence ratings in expert judgement
 * of goal graphs.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
 */
public enum GSordinalScale {
    
    /**
     * 'None' rating.
     */
    NONE("None"),
    /**
     * 'Low' rating.
     */
    LOW("Low"),
    /**
     * 'Medium' rating.
     */
    MEDIUM("Medium"),
    /**
     * 'High' rating.
     */
    HIGH("High");

    /**
     * The rating from the ordinal scale used in an expert judgement.
     */
    final String rating;

    /**
     * Constructs a goal sketching ordinal scale.
     *
     * @param rating either None, Low, Medium, or High.
     */
    GSordinalScale(String rating) {
        this.rating = rating;
    }

}
