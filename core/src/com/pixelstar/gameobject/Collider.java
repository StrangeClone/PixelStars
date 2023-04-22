package com.pixelstar.gameobject;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * For all GameObjects that can collide with something
 *
 * @author StrangeClone
 */
public interface Collider {
    boolean collides(Rectangle other);
    boolean collides(Vector2 other);
}
