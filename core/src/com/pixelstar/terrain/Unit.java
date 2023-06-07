package com.pixelstar.terrain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.gameobject.Interactive;
import com.pixelstar.gameobject.RectangularObject;
import com.pixelstar.gameobject.creature.Creature;

import java.util.Random;

/**
 * The in-game currency - very valuable
 *
 * @author StrangeClone
 */
public class Unit extends RectangularObject implements Interactive {
    /**
     * Unit random generator
     */
    private final static Random UNIT_RANDOM = new Random();
    /**
     * Texture of the units
     */
    public static Texture unitTexture;
    /**
     * The value of the unit
     */
    int value;
    /**
     * If the unit has been picked up
     */
    boolean picked = false;

    /**
     * Creates a unit in the specified position
     *
     * @param position the position
     */
    public Unit(Vector2 position) {
        super(unitTexture, position);
        value = randomValue();
    }

    private static int randomValue() {
        double t = 1 / UNIT_RANDOM.nextDouble();
        int value = 1;
        while (value < t) {
            value *= 10;
        }
        return value * UNIT_RANDOM.nextInt(10, 99) * 100;
    }

    @Override
    public boolean contains(Vector2 point) {
        return rectangle.contains(point);
    }

    /**
     * If a player interacts with this unit and isn't armed, the unit will be picked up
     *
     * @param creature the creature that triggers the interaction
     */
    @Override
    public void interact(Creature creature) {
        game.score(value);
        game.dynamicRemoveGameObject(this);
        picked = true;
    }

    public boolean isPicked() {
        return picked;
    }
}
