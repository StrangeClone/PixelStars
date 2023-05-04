# Weapons package

This package contains the class for the Weapons that the player and the enemies will use.

```mermaid
classDiagram
class Holdable {
   <<Interface>>
   drop()
   pickUp(Creature)
   contains(Vector2) boolean
   held() boolean
}
class RangedWeapon {
    <<Abstract>>
    - final List~Projectile~ PROJECTILES
    - final Texture PROJECTILE_TEXTURE
    - Creature holder
    - long reloadTime
    - long lastShootTime
    + RangedWeapon(Texture,Texture,Creature)
    + setReloadTime(long)
    + held() boolean
    + drop()
    + pickUp(Creature)
    + contains(Vector2) boolean
    + shoot(Vector2)
    + update()
}
Holdable <|-- RangedWeapon
class PlasmaPistol {
    + static Texture plasmaShotTexture
    + static Texture plasmaPistolTexture
    + PlasmaPistol(Creature)
}
RangedWeapon <|-- PlasmaPistol
class LaserPistol {
    + static Texture laserPistolTexture
    + static Texture laserShotTexture
    + LaserPistol(Creature)
}
RangedWeapon <|-- LaserPistol
class Projectile {
    ~ Vector2 position
    ~ Vector2 direction
    ~ float speed
    ~ TextureRegion textureRegion
    + Projectile(Texture, Vector2, Vector2, float)
    + setSpeed(float)
    + setDirection(Vector2)
    + getPosition() Vector2
    + update()
    + getPosition() Vector2
    + move(float, float)
}
RangedWeapon <-- Projectile
```