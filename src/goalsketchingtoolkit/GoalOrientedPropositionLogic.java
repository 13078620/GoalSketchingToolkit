/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University 
 * - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly 
 * prohibited
 * Proprietary and confidential
 *  Written by Christopher Berryman <c.p.berryman@btinternet.com>, 
 * September 2015
 */
package goalsketchingtoolkit;

/**
 * This class checks if the goal sketching node added to a GOP is supported by
 * the rules of goal sketching.
 *
 * @author Chris Berryman - Oxford Brookes University - 2015.
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
        } else if (gop.isChild()) {
            Goal g = (Goal) gop.getParent();
            Annotation a = (Annotation) nodeToAdd;
            if (a.getJudgement() != null) {
                Judgement j = a.getJudgement();
                if (g.isEntailed() && !j.getClass().toString().contains("GoalJudgement")) {
                    throw new UnsupportedOperationException("Only annotations with "
                            + "goal judgements can be added to GOPs with parent goals");
                }
            }
        }
        return correct;
    }
}
