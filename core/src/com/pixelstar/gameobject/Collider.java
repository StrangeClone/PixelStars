package com.pixelstar.gameobject;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * For all GameObjects that can collide with something
 *
 * @author StrangeClone
 */
public interface Collider {
    /**
     * @param rectangle a rectangle
     * @return if this GameObject is colliding with the specified rectangle
     */
    boolean collides(Rectangle rectangle);

    /**
     * @param point a point
     * @return if this GameObject contains the specified point
     */
    boolean collides(Vector2 point);
}
