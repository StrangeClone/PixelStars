package com.pixelstar.gameobject.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.pixelstar.gameobject.creature.Creature;

/**
 * The player's weapon
 *
 * @author StrangeClone
 */
public class PlasmaPistol extends RangedWeapon {
    /**
     * Texture of all Plasma Pistols
     */
    public static Texture plasmaPistolTexture;
    /**
     * Texture of the projectile shot
     */
    public static Texture plasmaShotTexture;

    /**
     * Creates a plasma pistol held by a Creature
     * @param holder the Creature
     */
    public PlasmaPistol(Creature holder) {
        super(plasmaPistolTexture, plasmaShotTexture, holder);
        setReloadTime(750);
    }
}
