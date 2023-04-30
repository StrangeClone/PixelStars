package com.pixelstar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pixelstar.gameobject.*;
import com.pixelstar.gameobject.creature.Player;
import com.pixelstar.gameobject.weapons.Holdable;
import com.pixelstar.gameobject.weapons.PlasmaPistol;
import com.pixelstar.terrain.Floor;
import com.pixelstar.terrain.Starship;
import com.pixelstar.terrain.Wall;

import java.util.ArrayList;
import java.util.List;

/**
 * Application class
 *
 * @author StrangeClone
 */

public class PixelStar extends ApplicationAdapter {
    /**
     * Dimensions of a single pixel in the game (in cm)
     */
    public static final float PIXEL_DIMENSIONS = 2.5f;

    /**
     * List of game objects that will be rendered in the screen
     */
    List<GameObject> gameObjects;
    /**
     * List of references to all GameObjects that can collide with each other
     */
    List<Collider> colliders;
    /**
     * List of references to all GameObjects that can be held by a Creature
     */
    List<Holdable> holdableList;
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
    /**
     * The scene zoom
     */
    float zoom = 1.0f;

    /**
     * Manager of the assets of this game
     */
    AssetManager assetManager;
    boolean loadingCompleted = false;

    @Override
    public void create() {
        GameObject.game = this;

        assetManager = new AssetManager();
        assetManager.load("floor.png", Texture.class);
        assetManager.load("wall.png", Texture.class);
        assetManager.load("player.png", Texture.class);
        assetManager.load("plasmaPistol.png", Texture.class);
        assetManager.load("plasmaShot.png", Texture.class);

        gameObjects = new ArrayList<>();
        colliders = new ArrayList<>();
        holdableList = new ArrayList<>();

        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() * zoom, Gdx.graphics.getHeight() * zoom);
    }

    /**
     * Function called when the asset manager has finished to load the assets, to generate the scene
     */
    private void completeLoading() {
        loadingCompleted = true;

        Floor.floorTexture = assetManager.get("floor.png");
        Floor.floorTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        Wall.wallTexture = assetManager.get("wall.png");
        Wall.wallTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        Player.playerTexture = assetManager.get("player.png");
        PlasmaPistol.plasmaPistolTexture = assetManager.get("plasmaPistol.png");
        PlasmaPistol.plasmaShotTexture = assetManager.get("plasmaShot.png");

        new Starship(45);
        player = new Player(new Vector2(0, 0));
        addGameObject(player);
    }

    @Override
    public void render() {
        if(!loadingCompleted && assetManager.update()) {
            completeLoading();
        }

        ScreenUtils.clear(Color.BLACK);
        if(player != null) {
            camera.position.set(player.getPosition(), 0);
            camera.update();
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        gameObjects.forEach(GameObject::update);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width * zoom, height * zoom);
    }

    /**
     * Increases zoom
     */
    public void ZoomIn() {
        zoom /= 0.9f;
        camera.setToOrtho(false, Gdx.graphics.getWidth() * zoom, Gdx.graphics.getHeight() * zoom);
    }

    /**
     * Decrease zoom
     */
    public void ZoomOut() {
        zoom *= 0.9f;
        camera.setToOrtho(false, Gdx.graphics.getWidth() * zoom, Gdx.graphics.getHeight() * zoom);
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * @param rectangle a rectangle
     * @return true if the rectangle collides with one of the Colliders of this class
     */
    public boolean checkCollision(final Rectangle rectangle) {
        return colliders.stream().anyMatch(c -> c.collides(rectangle));
    }
    /**
     * @param point a point
     * @return true if the point collides with one of the Colliders of this class
     */
    public boolean checkCollision(Vector2 point) {
        return colliders.stream().anyMatch(c -> c.collides(point));
    }

    /**
     * if under the specified position of the screen there's a holdable GameObject
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @return the selected holdable, or null if there's no holdable in the specified point
     */
    public Holdable gameObjectInScreenPosition(int screenX, int screenY) {
        Vector2 point = new Vector2(
                player.getPosition().x + (screenX - Gdx.graphics.getWidth() / 2.f) * zoom,
                player.getPosition().y - (screenY - Gdx.graphics.getHeight() / 2.f) * zoom
        );
        return holdableList.stream().filter(c -> c.contains(point)).findAny().orElse(null);
    }

    /**
     * Adds a GameObject, updating also the collider and holdable list, if necessary.
     * Then, sorts the objects so that colliders will be rendered after non colliders
     *
     * @param object the GameObject to add
     */
    public void addGameObject(GameObject object) {
        gameObjects.add(object);
        if (object instanceof Collider) {
            colliders.add((Collider) object);
        }
        if(object instanceof Holdable) {
            holdableList.add((Holdable) object);
        }
        sortGameObjects();
    }

    /**
     * Removes a GameObject, updating also the collider and holdable list, if necessary.
     * Then, sorts the objects so that colliders will be rendered after non colliders
     *
     * @param object the GameObject to remove
     */
    public void removeGameObject(GameObject object) {
        gameObjects.remove(object);
        if(object instanceof Collider) {
            colliders.remove((Collider) object);
        }
        if(object instanceof Holdable) {
            holdableList.remove((Holdable) object);
        }
        sortGameObjects();
    }

    /**
     * sorts the objects so that colliders will be rendered after non colliders
     */
    private void sortGameObjects() {
        gameObjects.sort((o1, o2) -> {
            if(o1 instanceof Collider) {
                if(o2 instanceof Collider) {
                    return 0;
                }else {
                    return 1;
                }
            }else if(o2 instanceof Collider){
                return -1;
            }
            return 0;
        });
    }
}
