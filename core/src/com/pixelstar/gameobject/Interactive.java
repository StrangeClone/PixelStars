package com.pixelstar.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.pixelstar.gameobject.creature.Creature;

/**
 * For all objects that the player can touch and select
 *
 * @author StrangeClone
 */
public interface Interactive {

    /**
     * @param point a point
     * @return if the interactive contains the point
     */
    boolean contains(Vector2 point);

    /**
     * When a creature interacts with this instance
     * @param creature the creature that triggers the interaction
     */
    void interact(Creature creature);
}
