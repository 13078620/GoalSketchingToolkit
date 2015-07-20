/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * The user of this interface has control over what logic is performed when a
 * goal sketching node component is added to another. The is correct method in
 * implementation should reflect the rules of goal sketching and the
 * consequences of adding one type of component to another.
 *
 * @author Chris Berryman.
 */
public interface GoalSketchingLogic {

    /**
     * Denotes whether an operation performed on a goal graph is correct or not.
     *
     * @param node the selected node.
     * @param nodeToAdd the node to add to the selected node.
     * @return true if the operation is valid, false otherwise.
     */
    public boolean isCorrect(GSnode nodeToAdd);

}
