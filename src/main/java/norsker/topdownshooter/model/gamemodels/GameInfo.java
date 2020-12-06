package norsker.topdownshooter.model.gamemodels;
import java.util.ArrayList;

public class GameInfo
{
    static GameInfo instance;
    ArrayList<Gameobject> staticObjects = new ArrayList<>();
    ArrayList<Updatable> gameObjects = new ArrayList<>();
    boolean updated = true;

    int[] boardsize = {800,800};

    ArrayList<Updatable> toBeRemoved = new ArrayList<>();


    private GameInfo()
    {

    }

    public static GameInfo getInstance()
    {
        if (instance == null)
            instance = new GameInfo();
        return instance;
    }

    public static void setInstance(GameInfo instance) {
        GameInfo.instance = instance;
    }

    public ArrayList<Gameobject> getStaticObjects() {
        return staticObjects;
    }

    public void setStaticObjects(ArrayList<Gameobject> staticObjects) {
        this.staticObjects = staticObjects;
    }

    public void setGameObjects(ArrayList<Updatable> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public int[] getBoardsize() {
        return boardsize;
    }

    //Garbarge collection
    public void updateGameInfo()
    {
        gameObjects.removeAll(toBeRemoved);
        toBeRemoved = new ArrayList<>();
    }


    public void setBoardsize(int[] boardsize) {
        this.boardsize = boardsize;
    }

    public void addObject(Updatable object)
    {
        gameObjects.add(object);
    }


    public ArrayList<Updatable> getGameObjects() {
        return gameObjects;
    }

    public void removeObject(Updatable object)
    {
        if (!toBeRemoved.contains(object))
        this.toBeRemoved.add(object);
    }

    public boolean isUpdated() {
        return updated;
    }


    public void setUpdated(boolean flag)
    {
        updated=flag;
    }

}
