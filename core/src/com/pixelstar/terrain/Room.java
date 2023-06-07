package com.pixelstar.terrain;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pixelstar.PixelStar;
import com.pixelstar.gameobject.GameObject;
import com.pixelstar.gameobject.creature.OldRobot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to generate a room, after loading the resources
 *
 * @author StrangeClone
 */
public class Room {
    /**
     * Dimension of a tile
     */
    final static float TILE = PixelStar.PIXEL_DIMENSIONS * 20;
    /**
     * If this room is an hallway
     */
    private final boolean HALLWAY;
    /**
     * The area this room will take
     */
    Rectangle area;
    /**
     * List of rooms that are overlapping
     */
    List<Room> nearRooms;

    /**
     * Generate a room in the specified area
     *
     * @param x       the left side of the room
     * @param y       the bottom side of the room
     * @param width   the room's width
     * @param height  the room's height
     * @param hallway if this room is an hallway
     */
    Room(float x, float y, float width, float height, boolean hallway) {
        area = new Rectangle(x, y, width, height);
        nearRooms = new ArrayList<>();
        this.HALLWAY = hallway;
    }

    /**
     * Generate the room, its floor and its walls
     *
     * @param origin the position of the player
     */
    void generate(Vector2 origin) {
        Random roomRandom = new Random();
        roomRandom.setSeed((long) area.x * (long) area.width + (long) area.y * (long) area.height);
        GameObject.game.addGameObject(new Floor(area));
        generateWall(area.x - TILE, area.y + area.height, area.width + 2 * TILE, true);
        generateWall(area.x - TILE, area.y - TILE, area.width + 2 * TILE, true);
        generateWall(area.x - TILE, area.y - TILE, area.height + 2 * TILE, false);
        generateWall(area.x + area.width, area.y - TILE, area.height + 2 * TILE, false);
        if (roomRandom.nextBoolean() &&
                Vector2.dst(origin.x, origin.y,
                        area.getCenter(new Vector2(0, 0)).x,
                        area.getCenter(new Vector2(0, 0)).y) > OldRobot.ACTIVATION_RANGE) {
            GameObject.game.addGameObject(new OldRobot(area.getCenter(new Vector2(0, 0))));
        } else if (!HALLWAY) {
            GameObject.game.addGameObject(new Chest(area.getCenter(new Vector2(0, 0))));
        }
    }

    /**
     * Generates a wall, avoiding parts where there are rooms in the nearRooms array
     *
     * @param x          the starting x of the wall
     * @param y          the starting y of the wall
     * @param length     the walls length
     * @param horizontal if true, the wall will develop horizontally, else vertically
     */
    private void generateWall(float x, float y, float length, boolean horizontal) {
        List<Rectangle> sections = new ArrayList<>();
        float size = length / TILE;
        boolean last = false;
        for (int i = 0; i < size; i++) {
            Rectangle newRect = new Rectangle(x, y, TILE, TILE);
            if (nearRooms.stream().noneMatch(room -> room.area.overlaps(newRect))) {
                if (!last) {
                    sections.add(newRect);
                } else {
                    sections.get(sections.size() - 1).merge(newRect);
                }
                last = true;
            } else {
                last = false;
            }
            if (horizontal) {
                x += TILE;
            } else {
                y += TILE;
            }
        }
        for (Rectangle section : sections) {
            GameObject.game.addGameObject(new Wall(new Vector2(section.x, section.y),
                    new Vector2(section.width, section.height)));
        }
    }

    /**
     * Sets the contents of the nearRoom array of the rooms in the array.
     *
     * @param rooms an array of rooms
     */
    static void setNearRooms(List<Room> rooms) {
        for (int i = 0; i < rooms.size(); i++) {
            for (int o = i + 1; o < rooms.size(); o++) {
                if (rooms.get(i).nearTo(rooms.get(o))) {
                    rooms.get(i).nearRooms.add(rooms.get(o));
                    rooms.get(o).nearRooms.add(rooms.get(i));
                }
            }
        }
    }

    /**
     * @param other a room
     * @return if this room is near to the other
     */
    private boolean nearTo(Room other) {
        return area.x == other.area.x + other.area.width ||
                area.y == other.area.y + other.area.height ||
                area.x + area.width == other.area.x ||
                area.y + area.height == other.area.y;
    }
}
