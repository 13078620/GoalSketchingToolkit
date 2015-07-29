/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

import java.util.ArrayList;

/**
 *
 * @author Chris Berryman.
 */
public interface GoalGraphModelInterface {

   /**
     * Sets the root goal for a goal graph model.
     *
     * @param root the root goal to set.
     */
    void addRootGoal(Goal root);
    
    void addAndEntailment(Goal parent, ANDentailment entailment);

    void addOrEntailment(Goal parent, ORentailment entailment);

    void addLeafGoal(Goal parent, Goal leaf);

    //void addLeafToANDentailment(ANDentailment parent, Goal leaf);
    //void addLeafToORentailment(ORentailment parent, Goal leaf);
    void addOperationalizingProducts(Goal parent, OperationalizingProducts ops);

    void addProduct(OperationalizingProducts ops, String product);

    void removeGoal(Goal child);

    //void removeLeafGoal(Goal child);
    //void removeParentGoal(GSnode parent);
    void removeOperationalisingProducts(Goal parent);

    void deleteGoalID(Goal goal);

    void deleteGoalOrientedProposition(Goal goal);

    void addAnnotation(Goal goal, Annotation annotation);

    void addToGSnodes(GSnode n);

    void removeFromGSnodes(int i);

    ArrayList getGSnodes();
    /**
     * Notifies the view of a change in state.
     */
    void notifyView();
}
