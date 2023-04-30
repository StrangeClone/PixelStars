package com.pixelstar.terrain;

import com.badlogic.gdx.math.Rectangle;
import com.pixelstar.PixelStar;
import com.pixelstar.gameobject.GameObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class for the abandoned starship where the player will move
 */
public class Starship {
    /**
     * The rooms of this ship
     */
    ArrayList<Rectangle> rooms;
    static Random starshipRandom = new Random();

    /**
     * Creates a starship, a place where the player will move
     *
     * @param seed the seed used to generate the starship map
     */
    public Starship(long seed) {
        generateStarship(seed);
        for(Rectangle room : rooms) {
            GameObject.game.addGameObject(new Floor(room));
        }
    }

    /**
     * Generates the starship map
     *
     * @param seed the seed used to generate the map
     */
    private void generateStarship(long seed) {
        starshipRandom.setSeed(seed);
        int nOfRooms = starshipRandom.nextInt(4, 7);
        rooms = new ArrayList<>();
        for (int i = 0; i < nOfRooms; i++) {
            generateRoom();
        }
        while(!checkOverlappingRooms()){
            fixRooms();
        }
        makeMapSymmetric();
    }

    /**
     * try to fix the rooms that are overlapping
     */
    private void fixRooms(){
        for (int i = 0; i < rooms.size(); i++) {
            for (int o = i + 1; o < rooms.size(); o++) {
                if (rooms.get(i).overlaps(rooms.get(o))) {
                    fixOverlappingRooms(i, o);
                }
            }
        }
    }

    /**
     * Function to fix two overlapping rooms: one will be moved, the other won't
     *
     * @param index1 index of the room that won't be moved
     * @param index2 index of the room that will be moved
     */
    private void fixOverlappingRooms(int index1, int index2) {
        if (starshipRandom.nextBoolean()) {
            rooms.get(index2).x = rooms.get(index1).x + rooms.get(index1).width;
        } else {
            if (rooms.get(index2).y <= 0) {
                rooms.get(index2).y = rooms.get(index1).y - rooms.get(index2).height;
            } else {
                rooms.get(index2).y = rooms.get(index1).y + rooms.get(index1).height;
            }
        }
    }

    /**
     * @return true if there are overlapping rooms in the starship
     */
    boolean checkOverlappingRooms(){
        for(int i = 0; i < rooms.size(); i++){
            for(int o = i + 1; o < rooms.size(); o++){
                if(rooms.get(i).overlaps(rooms.get(o))){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Generate a room, in a random location, with random dimensions
     */
    private void generateRoom() {
        rooms.add(new Rectangle(0, starshipRandom.nextInt(-20, 60) * PixelStar.PIXEL_DIMENSIONS * 20,
                starshipRandom.nextInt(10, 30) * PixelStar.PIXEL_DIMENSIONS * 20,
                starshipRandom.nextInt(10, 30) * PixelStar.PIXEL_DIMENSIONS * 20));
    }

    /**
     * makes the starship symmetric
     */
    private void makeMapSymmetric() {
        int startingNumberOfRooms = rooms.size();
        for (int i = 0; i < startingNumberOfRooms; i++) {
            if (rooms.get(i).x == 0) {
                rooms.get(i).x = -rooms.get(i).width;
                rooms.get(i).width *= 2;
            } else {
                rooms.add(new Rectangle(-rooms.get(i).x - rooms.get(i).width,
                        rooms.get(i).y,
                        rooms.get(i).width,
                        rooms.get(i).height));
            }
        }
    }
}

