package com.pixelstar.terrain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.gameobject.RectangularObject;

/**
 * Tiles of the map of the game
 *
 * @author StrangeClone
 */
public class Floor extends RectangularObject {
    /**
     * Texture of all the floors
     */
    public static Texture floorTexture;

    /**
     * Creates a floor in the specified position, with the specified dimensions
     *
     * @param leftBottomCorner the position of the left bottom corner
     * @param dimensions       the dimensions of the wall
     */
    public Floor(Vector2 leftBottomCorner, Vector2 dimensions) {
        super(floorTexture, new Rectangle(leftBottomCorner.x, leftBottomCorner.y, dimensions.x, dimensions.y));
    }

    public Floor(Rectangle rectangle) {
        super(floorTexture, rectangle);
    }
}
