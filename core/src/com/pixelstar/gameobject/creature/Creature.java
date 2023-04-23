package com.pixelstar.gameobject.creature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.gameobject.HardObject;

/**
 * Class for enemies, NPCs, player
 *
 * @author StrangeClone
 */
public abstract class Creature extends HardObject {
    static final float CENTIMETERS_IN_METER = 100.f;
    /**
     * The direction of the Creature's movement, as a normal vector.
     * It will be (0,0) if the creature isn't moving
     */
    protected Vector2 movementDirection;
    /**
     * The speed of the Creature (m/s)
     */
    protected float speed;

    /**
     * Creates a Creature, with the specified texture, center and dimensions
     *
     * @param texture   the texture of this creature
     * @param center    the (initial) center of this creature
     * @param dimension the dimensions of this creature (will be used for both width and height)
     */
    public Creature(Texture texture, Vector2 center, float dimension) {
        super(texture, new Rectangle(center.x - dimension / 2, center.y - dimension / 2, dimension, dimension));
        movementDirection = new Vector2(0, 0);
    }

    public float getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of this creature
     *
     * @param speed the new value (must be non-negative)
     * @throws IllegalArgumentException if the speed parameter is negative
     */
    public void setSpeed(float speed) {
        if (speed < 0) {
            throw new IllegalArgumentException("A speed must be a non-negative value");
        }
        this.speed = speed;
    }

    public Vector2 getPosition() {
        return new Vector2(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2);
    }

    /**
     * Draws and moves the creature. If the movement makes the creature collides with something,
     * the creature will stop.
     */
    @Override
    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        float oldX = rectangle.x;
        float oldY = rectangle.y;
        rectangle.x += movementDirection.x * delta * getSpeed() * CENTIMETERS_IN_METER;
        rectangle.y += movementDirection.y * delta * getSpeed() * CENTIMETERS_IN_METER;
        if(game.checkCollision(rectangle)) {
            rectangle.x = oldX;
            rectangle.y = oldY;
            movementDirection.x = 0;
            movementDirection.y = 0;
        }
        super.update();
    }
}
