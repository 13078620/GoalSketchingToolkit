/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

/**
 * The user of this interface has control over what logic is performed when a
 * goal sketching node component is added to another. The is correct method in
 * implementations should reflect the rules of goal sketching and the
 * consequences of adding one type of component to another.
 *
 * @author Chris Berryman - Oxfrod Brookes University - 2015.
 */
public interface GoalSketchingLogic {

    /**
     * Denotes whether an operation performed on a goal graph is correct or not.
     *
     * @param nodeToAdd the node to add to the selected node.
     * @return true if the operation is valid, false otherwise.
     */
    public boolean isCorrect(GSnode nodeToAdd);

}
