package com.pixelstar.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.PixelStar;

/**
 * For all Objects that have a texture and a rectangular shape
 *
 * @author StrangeClone
 */
public class RectangularObject extends GameObject {
    /**
     * The rectangular space that this Object will occupy on the screen
     */
    protected final Rectangle rectangle;
    /**
     * The texture region of this floor
     */
    protected TextureRegion textureRegion;

    /**
     * Creates a RectangularObject with the specified texture and coordinates
     *
     * @param texture   the texture that this Object will have
     * @param rectangle the rectangular space this Object will take
     */
    public RectangularObject(Texture texture, Rectangle rectangle) {
        this.rectangle = rectangle;
        this.textureRegion = new TextureRegion(texture, 0, 0,
                rectangle.width / (PixelStar.PIXEL_DIMENSIONS * texture.getWidth()),
                rectangle.height / (PixelStar.PIXEL_DIMENSIONS * texture.getHeight()));
    }

    /**
     * Creates a RectangularObject with the specified texture and coordinates.
     * Dimensions will be set so that the whole texture will be rendered.
     *
     * @param texture        the texture
     * @param centerPosition the center
     */
    public RectangularObject(Texture texture, Vector2 centerPosition) {
        this.rectangle = new Rectangle(centerPosition.x - texture.getWidth() * PixelStar.PIXEL_DIMENSIONS / 2,
                centerPosition.y - texture.getHeight() * PixelStar.PIXEL_DIMENSIONS / 2,
                texture.getWidth() * PixelStar.PIXEL_DIMENSIONS,
                texture.getHeight() * PixelStar.PIXEL_DIMENSIONS);
        this.textureRegion = new TextureRegion(texture, 0, 0,
                rectangle.width / (PixelStar.PIXEL_DIMENSIONS * texture.getWidth()),
                rectangle.height / (PixelStar.PIXEL_DIMENSIONS * texture.getHeight()));
    }

    public Vector2 getPosition() {
        return new Vector2(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2);
    }

    /**
     * Moves the GameObject
     *
     * @param dx the variation of the x
     * @param dy the variation of the y
     */
    public void move(float dx, float dy) {
        rectangle.x += dx;
        rectangle.y += dy;
    }

    @Override
    public void update() {
        game.getBatch().draw(textureRegion, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
