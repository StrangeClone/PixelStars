package com.pixelstar.gameobject.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.PixelStar;
import com.pixelstar.gameobject.GameObject;

/**
 * Objects shot by weapons, that can deal damages to Creatures
 *
 * @author StrangeClone
 */
public class Projectile extends GameObject {
    /**
     * The Projectile current position
     */
    Vector2 position;
    /**
     * The projectile direction (normal vector)
     */
    Vector2 direction;
    /**
     * The projectile position
     */
    float speed;
    /**
     * The texture region of this projectile
     */
    TextureRegion textureRegion;

    Projectile(Texture texture, Vector2 position, Vector2 direction, float speed) {
        this.textureRegion = new TextureRegion(texture);
        this.position = position;
        this.direction = new Vector2();
        setDirection(direction);
        setSpeed(speed);
    }

    void setSpeed(float speed) {
        if(speed < 0) {
            throw new IllegalArgumentException("A speed must be a non-negative value");
        }
        this.speed = speed;
    }

    void setDirection(Vector2 direction) {
        this.direction.x = direction.x;
        this.direction.y = direction.y;
        this.direction.nor();
    }

    @Override
    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        move(direction.x * speed * delta, direction.y * speed * delta);
        game.getBatch().draw(textureRegion,
                position.x,
                position.y,
                0,0,
                textureRegion.getTexture().getWidth() * PixelStar.PIXEL_DIMENSIONS,
                textureRegion.getTexture().getHeight() * PixelStar.PIXEL_DIMENSIONS,
                1,
                1,
                direction.angleDeg() - 90.f);
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(position);
    }

    @Override
    public void move(float dx, float dy) {
        position.x += dx;
        position.y += dy;
    }
}
