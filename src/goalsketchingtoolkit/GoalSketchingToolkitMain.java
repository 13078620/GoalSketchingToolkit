/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Main class for the goal sketching toolkit.
 *
 * @author Chris Berryman
 */
public class GoalSketchingToolkitMain {
    
     public static void main(String[] args) throws ParserConfigurationException, 
             TransformerConfigurationException, 
             TransformerException, 
             Exception {
         
        GoalGraphModelInterface model = new GoalGraphModel();
        GoalSketchingControllerInterface controller = new GoalSketchingController(model); 
         
     }

}
