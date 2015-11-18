/* 
 * Copyright (C) Christopher Berryman, Oxford Brookes University - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Christopher Berryman <c.p.berryman@btinternet.com>, September 2015
 */
package goalsketchingtoolkit;

/**
 * Types of goal's used in goal sketching.
 *
 * @author Chris Berryman - Oxfrod Brookes University - 2015.
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
    OBSTACLE("/o/"),
    /**
     * General goal.
     */
    GENERAL("/ /");    

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
