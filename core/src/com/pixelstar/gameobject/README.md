# GameObject package

This package contains the GameObject abstract class, representing all the objects in the game that will be rendered on
screen and its son classes.

```mermaid
classDiagram
class GameObject {
    <<Abstract>>
    + static PixelStar game
    + update()
    + getPosition() Vector2
    + move(float, float)
    + dist(GameObject) float
}
class RectangularObject {
    <<Abstract>>
    # Texture texture
    # Rectangle rectangle
    + RectangularObject(Texture, Rectangle)
    + RectangularObject(Texture, Vector2)
    + getPosition() Vector2
    + move(float,float)
    + update()
}
GameObject <|-- RectangularObject
class Collider {
    <<Interface>>
    + collides(Rectangle) boolean
    + collides(Vector2) boolean
}
class Interactive {
    <<Interface>>
    + contains(Vector2) boolean
    + interact(Creature)
}
class HardObject {
    <<Abstract>>
    + HardObject (Texture, Rectangle)
    + HardObject (Texture, Vector2)
    + collides(Rectangle) boolean
    + collides(Vector2) boolean
}
GameObject <|-- HardObject
Collider <|-- HardObject
```