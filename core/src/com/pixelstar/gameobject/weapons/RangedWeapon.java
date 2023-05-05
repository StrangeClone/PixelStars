package com.pixelstar.gameobject.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.PixelStar;
import com.pixelstar.gameobject.Collider;
import com.pixelstar.gameobject.Interactive;
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
public abstract class RangedWeapon extends RectangularObject implements Holdable, Interactive {
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
        super(texture, new Vector2(holder.getPosition().x + holder.getHandPosition().x,
                        holder.getPosition().y + holder.getHandPosition().y));
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
            rectangle.x = holder.getPosition().x - PixelStar.PIXEL_DIMENSIONS *
                    (float) (textureRegion.getTexture().getWidth() / 2) + holder.getHandPosition().x;
            rectangle.y = holder.getPosition().y - PixelStar.PIXEL_DIMENSIONS *
                    (float) (textureRegion.getTexture().getHeight() / 2) + holder.getHandPosition().y;
        }
    }

    @Override
    public boolean contains(Vector2 point) {
        return rectangle.contains(point);
    }

    @Override
    public void interact(Creature creature) {
        if(!creature.armed()) {
            creature.equipWeapon(this);
        }
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
            PROJECTILES.add(new Projectile(PROJECTILE_TEXTURE, holder.getPosition(), direction.nor(), 500));
        }
    }

    @Override
    public void update() {
        for (Iterator<Projectile> iterator = PROJECTILES.iterator(); iterator.hasNext(); ) {
            Projectile p = iterator.next();
            p.update();
            Optional<Collider> collider = game.getCollider(p.position);
            if (collider.isPresent() && collider.get() != holder) {
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
