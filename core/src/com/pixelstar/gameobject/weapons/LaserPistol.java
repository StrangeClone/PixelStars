package com.pixelstar.gameobject.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.pixelstar.gameobject.creature.Creature;

/**
 * OldRobot's standard Weapon
 *
 * @author StrangeClone
 */
public class LaserPistol extends RangedWeapon {
    public static Texture laserPistolTexture;
    public static Texture laserShotTexture;

    /**
     * Creates a laser pistol held by a Creature
     * @param holder the Creature
     */
    public LaserPistol(Creature holder) {
        super(laserPistolTexture, laserShotTexture, holder);
        setReloadTime(500);
        setDamages(5, 1);
    }
}
