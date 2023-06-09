package com.pixelstar.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Any rectangular object that can collides with other GameObject
 *
 * @author StrangeClone
 */
public abstract class HardObject extends RectangularObject implements Collider {

    /**
     * Creates an HardObject with the specified texture and coordinates
     *
     * @param texture   the texture that this Object will have
     * @param rectangle the rectangular space this Object will take
     */
    public HardObject(Texture texture, Rectangle rectangle) {
        super(texture, rectangle);
    }

    /**
     * Creates an HardObject with the specified texture and coordinates.
     * Dimensions will be set so that the whole texture will be rendered.
     *
     * @param texture the texture
     * @param center  the center
     */
    public HardObject(Texture texture, Vector2 center) {
        super(texture, center);
    }

    /**
     * @param rectangle a rectangle
     * @return if this Object collides with the specified rectangle
     */
    @Override
    public boolean collides(Rectangle rectangle) {
        return this.rectangle != rectangle && this.rectangle.overlaps(rectangle);
    }

    /**
     * @param point a point
     * @return if this Object contains the specified point
     */
    @Override
    public boolean collides(Vector2 point) {
        return rectangle.contains(point);
    }
}
