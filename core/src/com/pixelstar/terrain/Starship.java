package com.pixelstar.terrain;

import com.pixelstar.PixelStar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Class for the abandoned starship where the player will move
 *
 * @author StrangeClone
 */
public class Starship {
    static private final Random STARSHIP_RANDOM = new Random();
    /**
     * The rooms of this ship
     */
    ArrayList<Room> rooms;

    /**
     * Creates a starship, a place where the player will move
     *
     * @param seed the seed used to generate the starship map
     */
    public Starship(long seed) {
        generateStarship(seed);
        Room.setNearRooms(rooms);
        for(Room room : rooms) {
            room.generate();
        }
    }

    /**
     * Generates the starship map
     *
     * @param seed the seed used to generate the map
     */
    private void generateStarship(long seed) {
        STARSHIP_RANDOM.setSeed(seed);
        int nOfRooms = STARSHIP_RANDOM.nextInt(4, 7);
        rooms = new ArrayList<>();
        for (int i = 0; i < nOfRooms; i++) {
            generateRoom();
        }
        while(!checkOverlappingRooms()){
            fixRooms();
        }
        addCentralHallway();
        makeMapSymmetric();
    }

    /**
     * try to fix the rooms that are overlapping
     */
    private void fixRooms(){
        for (int i = 0; i < rooms.size(); i++) {
            for (int o = i + 1; o < rooms.size(); o++) {
                if (rooms.get(i).area.overlaps(rooms.get(o).area)) {
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
        if (STARSHIP_RANDOM.nextBoolean()) {
            rooms.get(index2).area.x = rooms.get(index1).area.x + rooms.get(index1).area.width;
        } else {
            if (rooms.get(index2).area.y <= 0) {
                rooms.get(index2).area.y = rooms.get(index1).area.y - rooms.get(index2).area.height;
            } else {
                rooms.get(index2).area.y = rooms.get(index1).area.y + rooms.get(index1).area.height;
            }
        }
    }

    /**
     * @return true if there are overlapping rooms in the starship
     */
    boolean checkOverlappingRooms(){
        for(int i = 0; i < rooms.size(); i++){
            for(int o = i + 1; o < rooms.size(); o++){
                if(rooms.get(i).area.overlaps(rooms.get(o).area)){
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
        rooms.add(new Room(0, STARSHIP_RANDOM.nextInt(-6, 12) * PixelStar.PIXEL_DIMENSIONS * 40,
                STARSHIP_RANDOM.nextInt(3, 6) * PixelStar.PIXEL_DIMENSIONS * 40,
                STARSHIP_RANDOM.nextInt(3, 6) * PixelStar.PIXEL_DIMENSIONS * 40));
    }

    /**
     * makes the starship symmetric
     */
    private void makeMapSymmetric() {
        int startingNumberOfRooms = rooms.size();
        for (int i = 0; i < startingNumberOfRooms; i++) {
            if (rooms.get(i).area.x == 0) {
                rooms.get(i).area.x = -rooms.get(i).area.width;
                rooms.get(i).area.width *= 2;
            } else {
                rooms.add(new Room(-rooms.get(i).area.x - rooms.get(i).area.width,
                        rooms.get(i).area.y,
                        rooms.get(i).area.width,
                        rooms.get(i).area.height));
            }
        }
    }

    /**
     * Function to add a central hallway to the ship, that joins all the rooms;
     * (must be called before the makeMapSymmetric method)
     */
    private void addCentralHallway() {
        List<Room> centralRooms = rooms.stream().filter(room -> room.area.x == 0).
                sorted((r1,r2) -> Float.compare(r1.area.y, r2.area.y)).collect(Collectors.toList());
        for(int i = 0; i < centralRooms.size() - 1; i++) {
            rooms.add(new Room(0, centralRooms.get(i).area.y + centralRooms.get(i).area.height,
                    PixelStar.PIXEL_DIMENSIONS * 40,
                    centralRooms.get(i + 1).area.y - (centralRooms.get(i).area.y + centralRooms.get(i).area.height)));
        }
    }
}

