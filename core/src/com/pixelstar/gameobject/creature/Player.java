package com.pixelstar.gameobject.creature;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.PixelStar;
import com.pixelstar.gameobject.weapons.PlasmaPistol;

/**
 * Avatar of the player
 *
 * @author StrangeClone
 */
public class Player extends Creature {
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
        super(playerTexture, center, PixelStar.PIXEL_DIMENSIONS * 32);
        Gdx.input.setInputProcessor(new PlayerInputAdapter());
        setSpeed(4);
        addChild(new PlasmaPistol(this));
    }

    private class PlayerInputAdapter extends InputAdapter {
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
         * Key W | S | A | D: stops the player if no other movement key is pressed
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
            return true;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            if(amountY > 0) {
                game.ZoomIn();
            }
            else if(amountY < 0) {
                game.ZoomOut();
            }
            return true;
        }
    }
}
