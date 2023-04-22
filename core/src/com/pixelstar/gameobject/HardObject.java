package com.pixelstar.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Any rectangular object that can collides with other GameObject
 *
 * @author StrangeClone
 */
public abstract class HardObject extends GameObject implements Collider {
    private final Texture texture;
    private final Rectangle rectangle;

    public HardObject(Texture texture, Rectangle rectangle) {
        this.texture = texture;
        this.rectangle = rectangle;
    }

    @Override
    public boolean collides(Rectangle rectangle) {
        return this.rectangle.overlaps(rectangle);
    }

    @Override
    public boolean collides(Vector2 point) {
        return rectangle.contains(point);
    }

    @Override
    public void update() {
        game.getBatch().draw(texture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
