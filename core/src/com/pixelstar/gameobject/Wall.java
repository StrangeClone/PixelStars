package com.pixelstar.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.PixelStar;

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
     * Creates a Wall in the specified coordinates with the specified dimensions
     *
     * @param center the position of the center of this wall
     */
    public Wall(Vector2 center) {
        super(wallTexture, new Rectangle(center.x - PixelStar.PIXEL_DIMENSIONS * wallTexture.getWidth() / 2,
                center.y - PixelStar.PIXEL_DIMENSIONS * wallTexture.getHeight() / 2,
                PixelStar.PIXEL_DIMENSIONS * wallTexture.getWidth(),
                PixelStar.PIXEL_DIMENSIONS * wallTexture.getHeight()));
    }

    /**
     * Creates a Wall in the specified coordinates with the specified dimensions
     *
     * @param xCenter the x of position of the center of this wall
     * @param yCenter the y of position of the center of this wall
     */
    public Wall(float xCenter, float yCenter) {
        super(wallTexture, new Rectangle(xCenter - PixelStar.PIXEL_DIMENSIONS * wallTexture.getWidth() / 2,
                yCenter - PixelStar.PIXEL_DIMENSIONS * wallTexture.getHeight() / 2,
                PixelStar.PIXEL_DIMENSIONS * wallTexture.getWidth(),
                PixelStar.PIXEL_DIMENSIONS * wallTexture.getHeight()));
    }
}
