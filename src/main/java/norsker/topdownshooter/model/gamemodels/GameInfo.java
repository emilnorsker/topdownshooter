package norsker.topdownshooter.model.gamemodels;
import java.util.ArrayList;

public class GameInfo
{
    static GameInfo instance;
    ArrayList<Gameobject> staticObjects = new ArrayList<>();
    ArrayList<Updatable> gameObjects = new ArrayList<>();





    private GameInfo(){}

    public static GameInfo getInstance()
    {
        if (instance == null)
            instance = new GameInfo();
        return instance;
    }

    public void addObject(Updatable object)
    {
        gameObjects.add(object);
    }


    public ArrayList<Updatable> getGameObjects() {
        return gameObjects;
    }

}
