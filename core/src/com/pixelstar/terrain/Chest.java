package com.pixelstar.terrain;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.gameobject.HardObject;
import com.pixelstar.gameobject.Interactive;
import com.pixelstar.gameobject.creature.Creature;

/**
 * Chest that contains units
 *
 * @author StrangeClone
 */
public class Chest extends HardObject implements Interactive {
    /**
     * Texture of the closed chests
     */
    public static Texture chestTexture;
    /**
     * Texture of the opened chests
     */
    public static Texture openedChestTexture;
    /**
     * If the chest has been opened
     */
    private boolean opened = false;
    /**
     * Reference to the unit that this chest contains
     */
    Unit chestUnit;

    /**
     * Creates a chest in the specified position
     * @param position the position
     */
    public Chest(Vector2 position) {
        super(chestTexture, position);
        chestUnit = new Unit(new Vector2(rectangle.x, rectangle.y - rectangle.width));
    }

    @Override
    public boolean contains(Vector2 point) {
        return rectangle.contains(point);
    }

    /**
     * If a creature that isn't armed interacts with this chest, it will open, dropping a unit
     * @param creature the creature that triggers the interaction
     */
    @Override
    public void interact(Creature creature) {
        if(!opened) {
            game.dynamicAddGameObject(chestUnit);
            textureRegion.setTexture(openedChestTexture);
            opened = true;
        }
    }

    /**
     * @return if this chest is opened and the unit is collected
     */
    public boolean completed() {
        return opened && chestUnit.isPicked();
    }
}
