package com.pixelstar;

import com.badlogic.gdx.ApplicationAdapter;
import com.pixelstar.gameobject.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Application class
 *
 * @author StrangeClone
 */

public class PixelStar extends ApplicationAdapter {
    /**
     * List of game objects that will be rendered in the screen
     */
    List<GameObject> gameObjects;

    @Override
    public void create() {
        gameObjects = new ArrayList<>();
    }

    @Override
    public void render() {
        for(GameObject object : gameObjects) {
            object.update();
        }
    }
}
