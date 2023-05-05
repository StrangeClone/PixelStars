package com.pixelstar.gameobject.weapons;

import com.pixelstar.gameobject.creature.Creature;

/**
 * Interface for all objects that can be held by a creature
 *
 * @author StrangeClone
 */
public interface Holdable {
    /**
     * Frees this holdable from its holder
     */
    void drop();

    /**
     * Gives this holdable to a holder
     * @param creature the new holder
     */
    void pickUp(Creature creature);

    /**
     * @return if the holdable is currently held
     */
    boolean held();
}
