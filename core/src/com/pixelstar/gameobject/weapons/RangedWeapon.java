package com.pixelstar.gameobject.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.PixelStar;
import com.pixelstar.gameobject.Collider;
import com.pixelstar.gameobject.RectangularObject;
import com.pixelstar.gameobject.creature.Creature;
import com.pixelstar.gameobject.creature.OldRobot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Weapons that can fire Projectiles
 *
 * @author StrangeClone
 */
public abstract class RangedWeapon extends RectangularObject implements Holdable {
    /**
     * The projectiles this weapon has shot
     */
    private final List<Projectile> PROJECTILES;
    /**
     * Texture of the projectiles that this weapon will shoot
     */
    private final Texture PROJECTILE_TEXTURE;
    /**
     * the Creature that holds this weapon
     */
    private Creature holder;
    /**
     * Time that must pass from a shoot
     */
    private long reloadTime;
    /**
     * Time when the weapon has shot the last projectile
     */
    private long lastShootTime;

    /**
     * Creates a ranged weapon, held by a Creature
     *
     * @param holder the Creature
     */
    public RangedWeapon(Texture texture, Texture projectileTexture, Creature holder) {
        super(texture, new Rectangle(
                holder.getPosition().x - PixelStar.PIXEL_DIMENSIONS * ((float) (texture.getWidth() / 2) - holder.getHandPosition().x),
                holder.getPosition().y - PixelStar.PIXEL_DIMENSIONS * ((float) (texture.getHeight() / 2) - holder.getHandPosition().y),
                PixelStar.PIXEL_DIMENSIONS * texture.getWidth(),
                PixelStar.PIXEL_DIMENSIONS * texture.getHeight()));
        this.holder = holder;
        this.PROJECTILE_TEXTURE = projectileTexture;
        PROJECTILES = new ArrayList<>();
        lastShootTime = System.currentTimeMillis();
    }

    public void setReloadTime(long reloadTime) {
        this.reloadTime = reloadTime;
    }

    @Override
    public boolean held() {
        return holder != null;
    }

    @Override
    public void drop() {
        if (held()) {
            rectangle.x = holder.getPosition().x;
            rectangle.y = holder.getPosition().y;
            holder = null;
        }
    }

    @Override
    public void pickUp(Creature creature) {
        if (!held()) {
            holder = creature;
            rectangle.x = holder.getPosition().x - PixelStar.PIXEL_DIMENSIONS * ((float) (texture.getWidth() / 2) - holder.getHandPosition().x);
            rectangle.y = holder.getPosition().y - PixelStar.PIXEL_DIMENSIONS * ((float) (texture.getHeight() / 2) - holder.getHandPosition().y);
        }
    }

    @Override
    public boolean contains(Vector2 point) {
        return rectangle.contains(point);
    }

    /**
     * Shoots a projectile directed to the position specified as target
     *
     * @param target the position of the target
     */
    public void shoot(Vector2 target) {
        if (holder != null && System.currentTimeMillis() - lastShootTime > reloadTime) {
            lastShootTime = System.currentTimeMillis();
            Vector2 direction = new Vector2(target.x - holder.getPosition().x, target.y - holder.getPosition().y);
            direction.nor();
            Vector2 projectilePosition = new Vector2(
                    holder.getPosition().x + direction.x / (float) Math.sqrt(2) * holder.getDimension(),
                    holder.getPosition().y + direction.y / (float) Math.sqrt(2) * holder.getDimension());
            PROJECTILES.add(new Projectile(PROJECTILE_TEXTURE, projectilePosition, direction, 500));
        }
    }

    @Override
    public void update() {
        for (Iterator<Projectile> iterator = PROJECTILES.iterator(); iterator.hasNext(); ) {
            Projectile p = iterator.next();
            p.update();
            Optional<Collider> collider = game.getCollider(p.position);
            if (collider.isPresent()) {
                if(collider.get() instanceof OldRobot) {
                    ((OldRobot) collider.get()).die();
                    game.dynamicRemoveGameObject((OldRobot)collider.get());
                }
                iterator.remove();
            }
        }
        super.update();
    }
}
