# Creature package

This package will contain the Creature class, son of HardObject class, and its son classes; Creatures will be both
friends and enemies, and also the Player class will be son of Creature

```mermaid
classDiagram
class Creature {
    <<Abstract>>
    # int WEAPON_INDEX
    # Vector2 movementDirection
    # float speed
    # List~GameObject~ children
    + Creature(Texture,Vector2)
    + armed() boolean
    # dropWeapon()
    # equipWeapon(RangedWeapon)
    + getDimension() float
    + getSpeed() float
    + setSpeed(float)
    + update()
    + die()
    # setWeapon(PlasmaPistol)
    + abstract getHandPosition()
    + Optional~RangedWeapon~ getWeapon()
}
class Player {
    - static final float PICK_UP_RANGE
    - static final Vector2 HAND_LOCATION
    + static Texture playerTexture
    + Player(Vector2)
    + Vector2 getHandPosition()
}
Creature <|-- Player
class PlayerInputAdapter {
    <<Private for Player class>>
    + touchDown() boolean
    + keyDown() boolean
    + keyUp() boolean
}
class OldRobot {
    - static final float ACTIVATION_RANGE
    - static final Vector2 HAND_LOCATION
    + static Texture oldRobotTexture
    # Creature target
    + OldRobot(Vector2)
    + getHandPosition()
    + update()
}
Creature <|-- OldRobot
```