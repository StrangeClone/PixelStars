package com.pixelstar.gameobject.weapons;

import com.badlogic.gdx.math.Vector2;
import com.pixelstar.gameobject.creature.Creature;

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
     * @param point a point
     * @return if the Holdable contains the point
     */
    boolean contains(Vector2 point);

    /**
     * @return if the holdable is currently held
     */
    boolean held();
}
