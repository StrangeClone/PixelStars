# Terrain package
This package contains classes to generate the map and its contents

```mermaid
classDiagram
class Starship{
    - static final STARSHIP_GENERATOR
    ~ ArrayList~Room~ rooms
    + Starship(long)
    - generateStarship(long)
    - fixRooms()
    - fixOverlappingRooms(int,int)
    - checkOverlappingRooms() boolean
    - generateRoom()
    - makeMapSymmetric()
    - addCentralHallway()
}
class Room {
    ~ final static float TILE
    ~ Rectangle area
    ~ List~Room~ nearRooms
    + Room(float,float,float,float)
    + generate()
    - generateWall(float,float,float,boolean)
    ~ static setNearRooms()
    - nearTo(Room) boolean
}
class Floor {
    + static Texture floorTexture
    + Floor(Vector2)
    + Floor(float,float)
}
class Wall {
    + static Texture wallTexture
    + Wall(Vector2)
    + Wall(float, float)
}
class Chest {
    + static Texture chestTexture
    + static Texture openedChestTexture
    - boolean opened
    + Chest(Vector2)
    + contains(Vector2)
    + interact(Creature)
}
class Unit {
    + static Texture unitTexture
    + Unit(Vector2)
    + contains(Vector2) boolean
    + interact(Creature)
}
```