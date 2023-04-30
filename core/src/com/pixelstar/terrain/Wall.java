package com.pixelstar.terrain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.gameobject.HardObject;

/**
 * Wall, defines the rooms in the map
 *
 * @author StrangeClone
 */
public class Wall extends HardObject {
    /**
     * Texture of all the walls
     */
    public static Texture wallTexture;

    /**
     * Creates a wall in the specified position, with the specified dimensions
     *
     * @param leftBottomCorner the position of the left bottom corner
     * @param dimensions       the dimensions of the wall
     */
    public Wall(Vector2 leftBottomCorner, Vector2 dimensions) {
        super(wallTexture, new Rectangle(leftBottomCorner.x, leftBottomCorner.y, dimensions.x, dimensions.y));
    }
}
