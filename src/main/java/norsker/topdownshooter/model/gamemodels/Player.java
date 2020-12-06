package norsker.topdownshooter.model.gamemodels;

import norsker.topdownshooter.model.Input;

public class Player extends Gameobject implements Updatable
{
    int health;
    int playerId;
    Weapon weapon;

    //last inputs
    int[]   moveDirection = {0,0};
    int      angle = 0;
    boolean isMouseDown = false;


    public Player(int playerId)
    {
        super("https://opengameart.org/sites/default/files/survivor-idle_shotgun_0.png", (int)(Math.random()*400)+100, (int)(Math.random()*400+100), 50, 50, 0);
        this.health = 100;
        this.playerId = playerId;
        this.weapon = new Weapon();
    }


    public void setInput(Input input)
    {
        this.moveDirection=input.getMoveDirection();
        this.angle = input.getPlayerDirection();
    }

    void move()
    {
        int speed = 5;
        x+=moveDirection[0]*speed; //Math.cos(Math.toDegrees(angle))
        y+=moveDirection[1]*speed; //Math.sin(Math.toDegrees(angle))

    }

    void shoot()
    {
        if (weapon.canShoot())
        {
            GameInfo.getInstance().addObject(new Bullet(this,  x,  y, angle));
        }
    }

    @Override
    public void update()
    {
        weapon.update();
        move();
        if (isMouseDown)
            shoot();
        moveDirection = new int[]{0, 0};
        angle = 0;
        isMouseDown = false;
    }


    public int getHealth() {
        return health;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int[] getMoveDirection() {
        return moveDirection;
    }

    @Override
    public int getAngle() {
        return angle;
    }

    public boolean isMouseDown() {
        return isMouseDown;
    }
}
