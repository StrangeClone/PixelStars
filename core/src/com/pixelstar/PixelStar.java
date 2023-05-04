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
import com.pixelstar.gameobject.creature.Creature;
import com.pixelstar.gameobject.creature.OldRobot;
import com.pixelstar.gameobject.creature.Player;
import com.pixelstar.gameobject.weapons.Holdable;
import com.pixelstar.gameobject.weapons.LaserPistol;
import com.pixelstar.gameobject.weapons.PlasmaPistol;
import com.pixelstar.terrain.Floor;
import com.pixelstar.terrain.Starship;
import com.pixelstar.terrain.Wall;

import java.util.*;

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
     * GameObjects to remove from the arrays during a frame
     */
    List<GameObject> gameObjectsToRemove;
    /**
     * GameObjects to add to the arrays during a frame
     */
    List<GameObject> gameObjectsToAdd;
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
        assetManager.load("oldRobot.png", Texture.class);
        assetManager.load("laserShot.png", Texture.class);
        assetManager.load("laserPistol.png", Texture.class);

        gameObjects = new ArrayList<>();
        gameObjectsToRemove = new ArrayList<>();
        gameObjectsToAdd = new ArrayList<>();
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

        Floor.floorTexture = assetManager.get("floor.png");
        Floor.floorTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        Wall.wallTexture = assetManager.get("wall.png");
        Wall.wallTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        Player.playerTexture = assetManager.get("player.png");
        PlasmaPistol.plasmaPistolTexture = assetManager.get("plasmaPistol.png");
        PlasmaPistol.plasmaShotTexture = assetManager.get("plasmaShot.png");
        OldRobot.oldRobotTexture = assetManager.get("oldRobot.png");
        LaserPistol.laserShotTexture = assetManager.get("laserShot.png");
        LaserPistol.laserPistolTexture = assetManager.get("laserPistol.png");

        player = new Player(new Vector2(0, 0));
        new Starship(4);
        addGameObject(player);
        sortGameObjects();

        loadingCompleted = true;
    }

    @Override
    public void render() {
        if (!loadingCompleted && assetManager.update()) {
            completeLoading();
        }

        ScreenUtils.clear(Color.BLACK);
        if (player != null) {
            camera.position.set(player.getPosition(), 0);
            camera.update();
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        gameObjects.forEach(GameObject::update);
        batch.end();
        handleGameObjectsToAdd();
        handleGameObjectsToRemove();
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

    public Player getPlayer() {
        return player;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    /**
     * @param rectangle a rectangle
     * @return true if the rectangle collides with one of the Colliders of this class
     */
    public boolean checkCollision(Rectangle rectangle) {
        return colliders.stream().anyMatch(c -> c.collides(rectangle));
    }

    /**
     * Checks if the specified point collides with a Collider, and, if it does, returns the collider
     * @param point a point
     * @return an optional that contains the collider, or null if there's no collision
     */
    public Optional<Collider> getCollider(Vector2 point) {
        return colliders.stream().filter(c -> c.collides(point)).findAny();
    }

    /**
     * @param watcher the Creature that watch
     * @param target the Creature that is watched
     * @return if something is blocking the view between the two creatures
     */
    public boolean blockedView(Creature watcher, Creature target) {
        Vector2 wPosition = watcher.getPosition();
        Vector2 tPosition = target.getPosition();
        int dist = (int)Vector2.dst(wPosition.x, wPosition.y, tPosition.x, tPosition.y);
        float dx = (tPosition.x - wPosition.x) / dist;
        float dy = (tPosition.y - wPosition.y) / dist;
        for(int i = 0; i < dist; i++) {
            Optional<Collider> c = getCollider(new Vector2(wPosition.x + i * dx, wPosition.y + i * dy));
            if(c.isPresent() && c.get() != watcher && c.get() != target) {
                return true;
            }
        }
        return false;
    }

    /**
     * if under the specified position of the screen there's a holdable GameObject
     *
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
     * Can't be called after completeLoading method, because altering gameObject list during a frame can cause
     * ConcurrentModificationExceptions;
     *
     * @param object the GameObject to add
     */
    public void addGameObject(GameObject object) {
        if(loadingCompleted) {
            throw new IllegalStateException("addGameObject(GameObject) method can't be used after the loading has been completed");
        }
        gameObjects.add(object);
        if (object instanceof Collider) {
            colliders.add((Collider) object);
        }
        if (object instanceof Holdable) {
            holdableList.add((Holdable) object);
        }
    }

    /**
     * Removes a GameObject from the gameObject list. Actually, the real call to remove method will be done at the end
     * of the frame, to avoid ConcurrentModificationExceptions.
     * Can't be used before the completeLoading method;
     *
     * @param object the object to remove
     */
    public void dynamicRemoveGameObject(GameObject object) {
        if(!loadingCompleted) {
            throw new IllegalStateException("dynamicRemoveGameObject(GameObject) method can't be used before the loading has been completed");
        }
        gameObjectsToRemove.add(object);
    }

    /**
     * Adds a GameObject to the gameObject list. Actually, the real call to add method will be done at the end
     * of the frame, to avoid ConcurrentModificationExceptions.
     * Can't be used before the completeLoading method;
     *
     * @param object the object to add
     */
    public void dynamicAddGameObject(GameObject object) {
        if(!loadingCompleted) {
            throw new IllegalStateException("dynamicAddGameObject(GameObject) method can't be used before the loading has been completed");
        }
        gameObjectsToAdd.add(object);
    }

    /**
     * Puts all the objects in the gameObjectsToAdd list in the gameObject list
     */
    private void handleGameObjectsToAdd() {
        for(GameObject object : gameObjectsToAdd) {
            gameObjects.add(object);
            if(object instanceof Collider) {
                colliders.add((Collider) object);
            }
            if(object instanceof Holdable) {
                holdableList.add((Holdable) object);
            }
        }
        sortGameObjects();
        gameObjectsToAdd.clear();
    }

    /**
     * Removes all the objects in the gameObjectsToAdd list from the gameObject list
     */
    private void handleGameObjectsToRemove() {
        for (GameObject object : gameObjectsToRemove) {
            gameObjects.remove(object);
            if (object instanceof Collider) {
                colliders.remove((Collider) object);
            }
            if (object instanceof Holdable) {
                holdableList.remove((Holdable) object);
            }
        }
        gameObjectsToRemove.clear();
    }

    /**
     * sorts the objects so that colliders will be rendered after non colliders
     */
    private void sortGameObjects() {
        gameObjects.sort((o1, o2) -> {
            if (o1 instanceof Collider) {
                if (o2 instanceof Collider) {
                    return 0;
                } else {
                    return 1;
                }
            } else if (o2 instanceof Collider) {
                return -1;
            }
            return 0;
        });
    }
}
