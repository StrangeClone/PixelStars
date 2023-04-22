package com.pixelstar.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Tiles of the map of the game
 *
 * @author StrangeClone
 */
public class Floor extends GameObject {
    public final static float FLOOR_DIMENSION = 100.f;
    /**
     * Rectangle of the floor
     */
    private final Rectangle RECTANGLE;
    /**
     * Texture of all the floors
     */
    public static Texture floorTexture;

    public Floor(Vector2 centerPosition) {
        RECTANGLE = new Rectangle(centerPosition.x - FLOOR_DIMENSION / 2, centerPosition.y - FLOOR_DIMENSION / 2,
                FLOOR_DIMENSION, FLOOR_DIMENSION);
    }

    public Floor(float xCenter, float yCenter) {
        RECTANGLE = new Rectangle(xCenter - FLOOR_DIMENSION / 2, yCenter - FLOOR_DIMENSION / 2,
                FLOOR_DIMENSION, FLOOR_DIMENSION);
    }

    @Override
    public void update() {
        game.getBatch().draw(floorTexture, RECTANGLE.x, RECTANGLE.y, FLOOR_DIMENSION, FLOOR_DIMENSION);
    }
}
