# GameObject package

This package contains the GameObject abstract class, representing all the objects in the game that will be rendered on
screen and its son classes.

```mermaid
classDiagram
class GameObject {
    <<Abstract>>
    + static PixelStar game
    + update()
}
class RectangularObject {
    <<Abstract>>
    # Texture texture
    # Rectangle rectangle
    + RectangularObject(Texture, Rectangle)
    + update()
}
GameObject <|-- RectangularObject
class Floor {
    + static Texture floorTexture
    + Floor(Vector2)
    + Floor(float,float)
}
RectangularObject <|-- Floor
class Collider {
    <<Interface>>
    + boolean collides(Rectangle)
    + boolean collides(Vector2)
}
class HardObject {
    <<Abstract>>
    + HardObject (Texture, Rectangle)
    + boolean collides(Rectangle)
    + boolean collides(Vector2)
}
GameObject <|-- HardObject
Collider <|-- HardObject
class Wall {
    + static Texture wallTexture
    + Wall(Vector2)
    + Wall(float, float)
}
HardObject <|-- Wall
```