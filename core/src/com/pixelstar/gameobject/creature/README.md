# Creature package

This package will contain the Creature class, son of HardObject class, and its son classes; Creatures will be both
friends and enemies, and also the Player class will be son of Creature

```mermaid
classDiagram
class Creature {
    <<Abstract>>
    # Vector2 movementDirection
    # float speed
    + Creature(Texture,Vector2,float)
    + getSpeed() float
    + setSpeed(float)
    + getPosition() Vector2
    + update()
}
class Player {
    + static Texture playerTexture
    + Player(Vector2)
}
Creature <|-- Player
class PlayerInputAdapter {
    <<Private for Player class>>
    + keyDown() boolean
    + keyUp() boolean
}
```