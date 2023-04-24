# Weapons package

This package contains the class for the Weapons that the player and the enemies will use.

```mermaid
classDiagram

class PlasmaPistol {
    + static Texture plasmaPistolTexture
    - Creature holder
    + PlasmaPistol(Creature holder)
    + update()
}
```