package norsker.topdownshooter.model.gamemodels;

public class Bullet extends  Gameobject implements Updatable
{
    Gameobject owner;

    public Bullet(Gameobject owner, int position_x, int position_y, int angle)
    {
        super("http://pixelartmaker.com/art/ce198f20476c227.png", position_x, position_y, 20, 20, angle);
    }


    void move()
    {
        int speed = 30;
        x+=speed;
        y+=speed;
    }

    @Override
    public void update() {
        move();
    }
}
