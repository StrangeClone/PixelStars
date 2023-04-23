package com.pixelstar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pixelstar.gameobject.*;
import com.pixelstar.gameobject.creature.Player;

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
     * List of references to all GameObjects that can collide with each other
     */
    List<Collider> colliders;
    /**
     * Reference to the player
     */
    Player player;
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
        Player.playerTexture = new Texture(Gdx.files.internal("player.png"));

        gameObjects = new ArrayList<>();
        colliders = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                addGameObject(new Floor(x * SINGLE_TILE_DIMENSION, y * SINGLE_TILE_DIMENSION));
            }
            addGameObject(new Wall(x * SINGLE_TILE_DIMENSION, 10 * SINGLE_TILE_DIMENSION));
        }
        player = new Player(new Vector2(0, 0));
        addGameObject(player);

        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);
        camera.position.set(player.getPosition(), 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (GameObject object : gameObjects) {
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

    /**
     * @param rectangle a rectangle
     * @return true if the rectangle collides with one of the Colliders of this class
     */
    public boolean checkCollision(Rectangle rectangle) {
        for (Collider collider : colliders) {
            if (collider.collides(rectangle)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a GameObject, updating also the collider list, if necessary
     *
     * @param object the GameObject to add
     */
    private void addGameObject(GameObject object) {
        gameObjects.add(object);
        if (object instanceof Collider) {
            colliders.add((Collider) object);
        }
    }
}
