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
public class PlasmaPistol extends RectangularObject {
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
