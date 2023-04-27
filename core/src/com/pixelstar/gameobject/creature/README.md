# Creature package

This package will contain the Creature class, son of HardObject class, and its son classes; Creatures will be both
friends and enemies, and also the Player class will be son of Creature

```mermaid
classDiagram
class Creature {
    <<Abstract>>
    # Vector2 movementDirection
    # float speed
    # List~GameObject~ children
    + Creature(Texture,Vector2,float)
    + getDimension() float
    + getSpeed() float
    + setSpeed(float)
    + getPosition() Vector2
    + update()
    + addChild(GameObject)
}
class Player {
    ~ int WEAPON_INDEX
    - static final float PICK_UP_RANGE
    + static Texture playerTexture
    + Player(Vector2)
    + armed() boolean
    + dropWeapon()
    + equip(PlasmaPistol)
}
Creature <|-- Player
class PlayerInputAdapter {
    <<Private for Player class>>
    + touchDown() boolean
    + keyDown() boolean
    + keyUp() boolean
}
```