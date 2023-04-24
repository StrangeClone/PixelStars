package com.pixelstar.gameobject.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.pixelstar.PixelStar;
import com.pixelstar.gameobject.RectangularObject;
import com.pixelstar.gameobject.creature.Creature;

/**
 * The player's weapon
 *
 * @author StrangeClone
 */
public class PlasmaPistol extends RectangularObject {
    /**
     * Texture of all Plasma Pistols
     */
    public static Texture plasmaPistolTexture;
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
    }

    @Override
    public void update() {
        rectangle.x = holder.getPosition().x - PixelStar.PIXEL_DIMENSIONS * ((float) (plasmaPistolTexture.getWidth() / 2) - 13);
        rectangle.y = holder.getPosition().y - PixelStar.PIXEL_DIMENSIONS * ((float) (plasmaPistolTexture.getHeight() / 2) - 1);
        super.update();
    }
}
