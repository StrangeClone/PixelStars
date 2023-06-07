# PixelStar class

The PixelStar class is the application class, that manages all the GameObjects on the screen.

```mermaid
classDiagram
class PixelStar{
    + static final float PIXEL_DIMENSIONS
    ~ Stage stage
    ~ Label scoreLabel
    ~ Group nextGameMessage
    ~ int score
    ~ List~GameObject~ gameObjects
    ~ List~GameObject~ gameObjectsToAdd
    ~ List~GameObject~ gameObjectsToRemove
    ~ List~Collider~ colliders
    ~ List~Interactive~ interactiveList
    ~ Player player
    ~ SpriteBatch batch
    ~ OrthographicCamera camera
    ~ float zoom
    ~ AssetManager assetManager
    ~ boolean loadingCompleted
    ~ boolean gameOver
    + create()
    + completeLoading()
    + render()
    + resize()
    + dispose()
    + score(int)
    + updateHP(double)
    + zoomIn()
    + zoomOut()
    + getPlayer() Player
    + getBatch() SpriteBatch
    + checkCollision(Rectangle) boolean
    + getCollider(Vector2) Optional~Collider~
    + blockedView(Creature,Creature) boolean
    + manageInteractions(int,int) boolean
    + gameObjectInScreenPosition(int,int) Optional~Interactive~
    + addGameObject(GameObject)
    + dynamicRemoveGameObject(GameObject)
    + dynamicAddGameObject(GameObject)
    + handleGameObjectsToAdd()
    + handleGameObjectsToRemove()
    - nextGame()
    + nextLevel()
    + gameOver()
    - showNextGameMessage(String)
    + sortGameObjects()
}
```