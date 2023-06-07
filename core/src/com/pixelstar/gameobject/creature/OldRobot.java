package com.pixelstar.gameobject.creature;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.PixelStar;
import com.pixelstar.gameobject.GameObject;
import com.pixelstar.gameobject.weapons.LaserPistol;

/**
 * The player's enemy
 *
 * @author StrangeClone
 */
public class OldRobot extends Creature {
    /**
     * The robot will start shooting if its target is inside this range
     */
    public final static float ACTIVATION_RANGE = PixelStar.PIXEL_DIMENSIONS * 200;
    /**
     * The position of the robot's hand
     */
    private final static Vector2 HAND_LOCATION = new Vector2(10 * PixelStar.PIXEL_DIMENSIONS, 3 * PixelStar.PIXEL_DIMENSIONS);
    /**
     * The texture of all oldRobots
     */
    public static Texture oldRobotTexture;
    /**
     * The creature that this OldRobot is targeting
     */
    Creature target;

    /**
     * Creates an OldRobot in the specified position
     * @param center the position
     */
    public OldRobot(Vector2 center) {
        super(oldRobotTexture, 7, center);
        setWeapon(new LaserPistol(this));
        target = GameObject.game.getPlayer();
    }

    @Override
    public Vector2 getHandPosition() {
        return HAND_LOCATION;
    }

    /**
     * Shoot the target if it's inside the activation range
     */
    @Override
    public void update() {
        super.update();
        if(dist(target) < ACTIVATION_RANGE && !game.blockedView(this, target)) {
            getWeapon().ifPresent(w -> w.shoot(target.getPosition()));
        }
    }
}
