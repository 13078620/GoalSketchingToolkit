/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goalsketchingtoolkit;

/**
 * Types of goal's used in goal sketching.
 *
 * @author Chris Berryman
 */
public enum GoalType {

    /**
     * Motivation goal.
     */
    MOTIVATION("/m/"),
    /**
     * Behaviour goal.
     */
    BEHAVIOUR("/b/"),
    /**
     * Constraint goal.
     */
    CONSTRAINT("/c/"),
    /**
     * Assumption goal.
     */
    ASSUMPTION("/a/"),
    /**
     * Obstacle goal.
     */
    OBSTACLE("/o/");

    /**
     * The prefix used in the goal oriented proposition.
     */
    final String prefix;

    /**
     * Constructs a goal type.
     * @param prefix either /m/, /b/, /c/, /a/ or /o/.
     */
    GoalType(String prefix) {
        this.prefix = prefix;
    }

}
