# Weapons package

This package contains the class for the Weapons that the player and the enemies will use.

```mermaid
classDiagram

class PlasmaPistol {
    - final List~Projectile~ projectiles
    + static Texture plasmaShotTexture
    + static Texture plasmaPistolTexture
    - Creature holder
    + PlasmaPistol(Creature)
    + shoot(Vector2)
    + update()
}
class Projectile {
    ~ final Texture TEXTURE
    ~ Vector2 position
    ~ Vector2 direction
    ~ float speed
    + Projectile(Texture, Vector2, Vector2, float)
    + setSpeed(float)
    + setDirection(Vector2)
    + update()
}
PlasmaPistol <-- Projectile
```