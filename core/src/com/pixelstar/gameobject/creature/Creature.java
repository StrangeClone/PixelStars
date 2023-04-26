package com.pixelstar.gameobject.creature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.gameobject.HardObject;
import com.pixelstar.gameobject.RectangularObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for enemies, NPCs, player
 *
 * @author StrangeClone
 */
public abstract class Creature extends HardObject {
    /**
     * The direction of the Creature's movement, as a normal vector.
     * It will be (0,0) if the creature isn't moving
     */
    protected Vector2 movementDirection;
    /**
     * The speed of the Creature (cm/s)
     */
    protected float speed;
    /**
     * Children GameObjects of this Creature
     */
    protected List<RectangularObject> children;

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
        children = new ArrayList<>();
    }

    public float getDimension() {
        return rectangle.width;
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

    /**
     * Draws and moves the creature. If the movement makes the creature collides with something,
     * the creature will stop.
     */
    @Override
    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        float oldX = rectangle.x;
        float oldY = rectangle.y;
        move(movementDirection.x * delta * getSpeed(),
                movementDirection.y * delta * getSpeed());
        if (game.checkCollision(rectangle)) {
            rectangle.x = oldX;
            rectangle.y = oldY;
            movementDirection.x = 0;
            movementDirection.y = 0;
        } else {
            for (RectangularObject child : children) {
                child.move(movementDirection.x * delta * getSpeed(),
                        movementDirection.y * delta * getSpeed());
            }
        }
        for (RectangularObject child : children) {
            child.update();
        }
        super.update();
    }

    public void addChild(RectangularObject newChild) {
        children.add(newChild);
    }
}
