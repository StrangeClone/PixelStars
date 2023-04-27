package com.pixelstar.gameobject.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.PixelStar;
import com.pixelstar.gameobject.RectangularObject;
import com.pixelstar.gameobject.creature.Creature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The player's weapon
 *
 * @author StrangeClone
 */
public class PlasmaPistol extends RectangularObject implements Holdable {
    /**
     * The projectiles this weapon has shot
     */
    private final List<Projectile> PROJECTILES;
    /**
     * Texture of all Plasma Pistols
     */
    public static Texture plasmaPistolTexture;
    /**
     * Texture of the projectile shot
     */
    public static Texture plasmaShotTexture;
    /**
     * the Creature that holds this weapon
     */
    private Creature holder;

    /**
     * Creates a plasma pistol held by a Creature
     * @param holder the Creature
     */
    public PlasmaPistol(Creature holder) {
        super(plasmaPistolTexture, new Rectangle(
                holder.getPosition().x - PixelStar.PIXEL_DIMENSIONS * ((float) (plasmaPistolTexture.getWidth() / 2) - 13),
                holder.getPosition().y - PixelStar.PIXEL_DIMENSIONS * ((float) (plasmaPistolTexture.getHeight() / 2) - 1),
                PixelStar.PIXEL_DIMENSIONS * plasmaPistolTexture.getWidth(),
                PixelStar.PIXEL_DIMENSIONS * plasmaPistolTexture.getHeight()));
        this.holder = holder;
        PROJECTILES = new ArrayList<>();
    }

    @Override
    public boolean held() {
        return holder != null;
    }

    @Override
    public void drop() {
        if(held()) {
            rectangle.x = holder.getPosition().x;
            rectangle.y = holder.getPosition().y;
            holder = null;
        }
    }

    @Override
    public void pickUp(Creature creature) {
        if(!held()) {
            holder = creature;
            rectangle.x = holder.getPosition().x - PixelStar.PIXEL_DIMENSIONS * ((float) (plasmaPistolTexture.getWidth() / 2) - 13);
            rectangle.y = holder.getPosition().y - PixelStar.PIXEL_DIMENSIONS * ((float) (plasmaPistolTexture.getHeight() / 2) - 1);
        }
    }

    @Override
    public boolean contains(Vector2 point) {
        return rectangle.contains(point);
    }

    /**
     * Shoots a projectile directed to the position specified as target
     * @param target the position of the target
     */
    public void shoot(Vector2 target) {
        if(holder != null) {
            Vector2 direction = new Vector2(target.x - holder.getPosition().x, target.y - holder.getPosition().y);
            direction.nor();
            Vector2 projectilePosition = new Vector2(
                    holder.getPosition().x + direction.x / (float) Math.sqrt(2) * holder.getDimension(),
                    holder.getPosition().y + direction.y / (float) Math.sqrt(2) * holder.getDimension());
            PROJECTILES.add(new Projectile(plasmaShotTexture, projectilePosition, direction, 500));
        }
    }

    @Override
    public void update() {
        for (Iterator<Projectile> iterator = PROJECTILES.iterator(); iterator.hasNext();) {
            Projectile p = iterator.next();
            p.update();
            if(game.checkCollision(p.position)) {
                iterator.remove();
            }
        }
        super.update();
    }
}
