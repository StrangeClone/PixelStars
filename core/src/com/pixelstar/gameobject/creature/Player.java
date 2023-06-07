package com.pixelstar.gameobject.creature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.PixelStar;
import com.pixelstar.gameobject.GameObject;
import com.pixelstar.gameobject.weapons.PlasmaPistol;

/**
 * Avatar of the player
 *
 * @author StrangeClone
 */
public class Player extends Creature {
    /**
     * maximum distance
     */
    public final static float PICK_UP_RANGE = 100.f;
    /**
     * Position of the hand of a player
     */
    private final static Vector2 HAND_LOCATION = new Vector2(10 * PixelStar.PIXEL_DIMENSIONS, 0);
    /**
     * Player's texture
     */
    public static Texture playerTexture;

    /**
     * Creates a player in the specified position
     *
     * @param center the (initial) center of the player
     */
    public Player(Vector2 center) {
        super(playerTexture, 15, center);
        Gdx.input.setInputProcessor(new PlayerInputAdapter());
        setSpeed(400);
        setWeapon(new PlasmaPistol(this));
    }

    @Override
    public void damage(double damages) {
        super.damage(damages);
        GameObject.game.updateHP(HP);
    }

    /**
     * Sets the position of the player
     *
     * @param position the new position of the player
     */
    public void setPosition(Vector2 position) {
        Vector2 previousPosition = new Vector2(rectangle.x, rectangle.y);
        rectangle.x = position.x;
        rectangle.y = position.y;
        children.forEach(o -> o.move(position.x - previousPosition.x, position.y - previousPosition.y));
    }

    /**
     * Resets the player to full health
     */
    public void reset() {
        HP = 15;
        GameObject.game.updateHP(HP);
        Gdx.input.setInputProcessor(new PlayerInputAdapter());
    }

    /**
     * Game Over
     */
    @Override
    public void die() {
        GameObject.game.gameOver();
    }

    public Vector2 getHandPosition() {
        return HAND_LOCATION;
    }

    public class PlayerInputAdapter extends InputAdapter {
        /**
         * If the player clicks and has a weapon, the weapon will shoot in the specified direction.
         * If the player doesn't have a weapon and has clicked a weapon, that weapon will be equipped.
         *
         * @param screenX The x coordinate, origin is in the upper left corner
         * @param screenY The y coordinate, origin is in the upper left corner
         * @param pointer the pointer for the event.
         * @param button  the button
         * @return true
         */
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if (button == Input.Buttons.LEFT) {
                if (!GameObject.game.manageInteractions(screenX, screenY) && armed()) {
                    getWeapon().ifPresent(w -> w.shoot(
                            new Vector2(getPosition().x + screenX - Gdx.graphics.getWidth() / 2.f,
                                    getPosition().y - (screenY - Gdx.graphics.getHeight() / 2.f))
                    ));
                }
            }
            return true;
        }

        /**
         * When a key is pressed:
         * key W: moves player north
         * key S: moves player south
         * key A: moves player west
         * key D: moves player east
         *
         * @param keycode one of the constants in {@link Input.Keys}
         * @return true
         */
        @Override
        public boolean keyDown(int keycode) {
            if (keycode == Input.Keys.W) {
                movementDirection.x = 0;
                movementDirection.y = 1;
            } else if (keycode == Input.Keys.S) {
                movementDirection.x = 0;
                movementDirection.y = -1;
            } else if (keycode == Input.Keys.D) {
                movementDirection.x = 1;
                movementDirection.y = 0;
            } else if (keycode == Input.Keys.A) {
                movementDirection.x = -1;
                movementDirection.y = 0;
            }
            return true;
        }

        /**
         * When a key is released:
         * key W | S | A | D: stops the player if no other movement key is pressed
         * key Q: drop the weapon
         *
         * @param keycode one of the constants in {@link Input.Keys}
         * @return true
         */
        @Override
        public boolean keyUp(int keycode) {
            if ((keycode == Input.Keys.W && movementDirection.x == 0 && movementDirection.y == 1)
                    || (keycode == Input.Keys.S && movementDirection.x == 0 && movementDirection.y == -1)
                    || (keycode == Input.Keys.D && movementDirection.x == 1 && movementDirection.y == 0)
                    || (keycode == Input.Keys.A && movementDirection.x == -1 && movementDirection.y == 0)) {
                movementDirection.x = 0;
                movementDirection.y = 0;
            }
            if (keycode == Input.Keys.Q && armed()) {
                dropWeapon();
            }
            return true;
        }

        /**
         * When the mouse wheel is scrolled
         *
         * @param amountX the horizontal scroll amount, negative or positive depending on the direction the wheel was scrolled.
         * @param amountY the vertical scroll amount, negative or positive depending on the direction the wheel was scrolled.
         * @return always true
         */
        @Override
        public boolean scrolled(float amountX, float amountY) {
            if (amountY > 0) {
                game.ZoomIn();
            } else if (amountY < 0) {
                game.ZoomOut();
            }
            return true;
        }
    }
}
