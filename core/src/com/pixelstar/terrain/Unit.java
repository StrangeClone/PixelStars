package com.pixelstar.terrain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.gameobject.Interactive;
import com.pixelstar.gameobject.RectangularObject;
import com.pixelstar.gameobject.creature.Creature;

/**
 * The in-game currency - very valuable
 *
 * @author StrangeClone
 */
public class Unit extends RectangularObject implements Interactive {
    /**
     * Texture of the units
     */
    public static Texture unitTexture;

    /**
     * Creates a unit in the specified position
     * @param position the position
     */
    public Unit(Vector2 position) {
        super(unitTexture, position);
    }

    @Override
    public boolean contains(Vector2 point) {
        return rectangle.contains(point);
    }

    /**
     * If a player interacts with this unit and isn't armed, the unit will be picked up
     * @param creature the creature that triggers the interaction
     */
    @Override
    public void interact(Creature creature) {
        if(!creature.armed()) {
            game.dynamicRemoveGameObject(this);
        }
    }
}
