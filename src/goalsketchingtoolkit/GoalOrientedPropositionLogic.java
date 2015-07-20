/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * This class checks if the goal sketching node added to a GOP is supported by
 * the rules of goal sketching.
 *
 * @author Chris Berryman.
 */
public class GoalOrientedPropositionLogic implements GoalSketchingLogic {

    /**
     * The GOP which the logic is performed on.
     */
    private final GoalOrientedProposition gop;

    /**
     * Constructs a goal oriented proposition logic object initialised with the
     * GOP in question.
     *
     * @param gop the goal oriented proposition.
     */
    public GoalOrientedPropositionLogic(GoalOrientedProposition gop) {
        this.gop = gop;
    }

    /**
     * Returns true if the addition of the node to add to the GOP conforms to
     * the rules of goal sketching, false otherwise.
     *
     * @return true if the node to add is a valid node, false otherwise.
     */
    @Override
    public boolean isCorrect(GSnode nodeToAdd) {

        boolean correct = true;

        if (!nodeToAdd.getClass().toString().contains("Annotation")) {
            throw new UnsupportedOperationException("Only annotations can be added to GOPs");
        }

        return correct;

    }

}
