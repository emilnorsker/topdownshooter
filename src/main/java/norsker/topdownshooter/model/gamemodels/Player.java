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
        this.isMouseDown = input.getIsMouseDown();
    }

    void move()
    {
        int speed = 3;
        x+=moveDirection[0]*speed;
        y+=moveDirection[1]*speed;

    }

    void shoot()
    {
        if (weapon.canShoot())
        {
            weapon.shoot();
            GameInfo.getInstance().addObject(new Bullet(this,  x, y, angle,  weapon.damage, weapon.velocity)); //0.66 for aspect

            isMouseDown = false;
        }
    }

    @Override
    public void update()
    {
        if (health<1)
            GameInfo.getInstance().removeObject(this);
        weapon.update();
        move();
        if (isMouseDown)
            shoot();
        moveDirection = new int[]{0, 0};



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

    public void setHealth(int health) {
        this.health = health;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setMoveDirection(int[] moveDirection) {
        this.moveDirection = moveDirection;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setMouseDown(boolean mouseDown) {
        isMouseDown = mouseDown;
    }
}
