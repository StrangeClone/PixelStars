package com.pixelstar.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.PixelStar;

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
     * Creates a floor, with the center in the specified position
     *
     * @param centerPosition the position of the center
     */
    public Floor(Vector2 centerPosition) {
        super(floorTexture, new Rectangle(centerPosition.x - PixelStar.PIXEL_DIMENSIONS * floorTexture.getWidth() / 2,
                centerPosition.y - PixelStar.PIXEL_DIMENSIONS * floorTexture.getHeight() / 2,
                PixelStar.PIXEL_DIMENSIONS * floorTexture.getWidth(),
                PixelStar.PIXEL_DIMENSIONS * floorTexture.getHeight()));
    }

    /**
     * Creates a floor, with the center in the specified position
     *
     * @param xCenter the x of the position of the center
     * @param yCenter the y of the position of the center
     */
    public Floor(float xCenter, float yCenter) {
        super(floorTexture, new Rectangle(xCenter - PixelStar.PIXEL_DIMENSIONS * floorTexture.getWidth() / 2,
                yCenter - PixelStar.PIXEL_DIMENSIONS * floorTexture.getHeight() / 2,
                PixelStar.PIXEL_DIMENSIONS * floorTexture.getWidth(),
                PixelStar.PIXEL_DIMENSIONS * floorTexture.getHeight()));
    }
}
