/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * Ratings from the ordinal scale used as confidence ratings in expert judgement
 * of goal graphs.
 *
 * @author Chris Berryman.
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
