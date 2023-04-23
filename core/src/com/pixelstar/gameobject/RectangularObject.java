package com.pixelstar.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class RectangularObject extends GameObject {
    /**
     * The texture of this object, that will be rendered on screen
     */
    protected final Texture texture;
    /**
     * The rectangular space that this Object will occupy on the screen
     */
    protected final Rectangle rectangle;

    /**
     * Creates a RectangularObject with the specified texture and coordinates
     *
     * @param texture   the texture that this Object will have
     * @param rectangle the rectangular space this Object will take
     */
    public RectangularObject(Texture texture, Rectangle rectangle) {
        this.texture = texture;
        this.rectangle = rectangle;
    }


    @Override
    public void update() {
        game.getBatch().draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
