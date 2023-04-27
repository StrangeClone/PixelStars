package com.pixelstar.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.pixelstar.PixelStar;

/**
 * A generic object that will be rendered on screen
 *
 * @author StrangeClone
 */
public abstract class GameObject {
    /**
     * Reference to the application that owns all the GameObjects
     */
    public static PixelStar game;

    /**
     * Must be called at every frame, to update the gameObject
     */
    public abstract void update();

    /**
     * @return the position of this GameObject
     */
    public abstract Vector2 getPosition();

    public abstract void move(float dx, float dy);

    /**
     * @param other another GameObject
     * @return the distance between their positions
     */
    public float dist(GameObject other) {
        return (float) Math.sqrt(Math.pow(getPosition().x - other.getPosition().x, 2) +
                        Math.pow(getPosition().y - other.getPosition().y, 2));
    }
}
