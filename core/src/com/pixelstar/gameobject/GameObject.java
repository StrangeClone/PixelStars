package com.pixelstar.gameobject;

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
}
