package com.pixelstar.gameobject;

/**
 * A generic object that will be rendered on screen
 *
 * @author StrangeClone
 */
public abstract class GameObject {
    /**
     * Must be called at every frame, to update the gameObject
     */
    public abstract void update();
}
