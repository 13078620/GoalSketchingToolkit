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
        } else if (gop.isChild()) {
            Goal g = (Goal) gop.getParent();
            Annotation a = (Annotation) nodeToAdd;
            Judgement j = a.getJudgement();
            if (g.isEntailed() && !j.getClass().toString().contains("GoalJudgement")) {
                throw new UnsupportedOperationException("Only annotations with "
                        + "goal judgements can be added to GOPs with parent goals");
            }
        }

        return correct;

    }

    /**
     *
     * @param goaltype
     * @return
     */
    /*public boolean prefixIsCorrect(GoalType goaltype) {
        
     boolean correctPrefix = false;
        
     if (gop.isChild()) {
     Goal g = (Goal) gop.getParent();
     if (g.isOperationalized() && goaltype.prefix.equalsIgnoreCase("/a/")) {
     throw new UnsupportedOperationException("The goal this proposition belongs to is "
     + "operationalized, cannot set goal type as assumption");
     } else if (g.isRootGoal()&& !goaltype.prefix.equalsIgnoreCase("/m/")) {
     throw new UnsupportedOperationException("The goal this proposition belongs to is "
     + "the root goal, cannot set goal type as anything other than motivation");
     } else if (g.hasParent) {
     Goal parentGoal = (Goal) g.getParent().getParent();
     if (parentGoal.hasGop()) {
     GoalOrientedProposition parentGoalGOP = parentGoal.getProposition();
     if (parentGoalGOP.hasPrefix()) {
     if (parentGoalGOP.isAssumption() && !goaltype.prefix.equalsIgnoreCase("/a/")) {
     throw new UnsupportedOperationException("The goal this proposition belongs "
     + "to's parent goal's proposition is an assumption, this"
     + "proposition cannot have a goal type other than assumption");
     } else {
     correctPrefix = true;
     }
     } else {
     correctPrefix = true;
     }
     } else {
     correctPrefix = true;
     }
     } else {
     correctPrefix = true;
     }
     } else {
     correctPrefix = true;
     }
     return correctPrefix;
     } */
}
