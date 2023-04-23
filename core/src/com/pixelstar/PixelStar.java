package com.pixelstar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pixelstar.gameobject.Floor;
import com.pixelstar.gameobject.GameObject;
import com.pixelstar.gameobject.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Application class
 *
 * @author StrangeClone
 */

public class PixelStar extends ApplicationAdapter {
    /**
     * Dimensions of a single tile in the game (in cm);
     * This dimension will be used as a sort of standard;
     */
    public static final float SINGLE_TILE_DIMENSION = 50.f;
    /**
     * List of game objects that will be rendered in the screen
     */
    List<GameObject> gameObjects;
    /**
     * Batch that will render all the GameObjects
     */
    SpriteBatch batch;
    /**
     * Camera of the scene
     */
    OrthographicCamera camera;

    @Override
    public void create() {
        GameObject.game = this;

        Floor.floorTexture = new Texture(Gdx.files.internal("floor.png"));
        Wall.wallTexture = new Texture(Gdx.files.internal("wall.png"));

        gameObjects = new ArrayList<>();
        for(int x = 0; x < 10; x++) {
            for(int y = 0; y < 10; y++) {
                gameObjects.add(new Floor(x * SINGLE_TILE_DIMENSION, y * SINGLE_TILE_DIMENSION));
            }
            gameObjects.add(new Wall(x * SINGLE_TILE_DIMENSION, 10 * SINGLE_TILE_DIMENSION));
        }

        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(GameObject object : gameObjects) {
            object.update();
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
