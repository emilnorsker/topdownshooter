package norsker.topdownshooter.model.gamemodels;

public class Bullet extends  Gameobject implements Updatable
{
    Player owner;
    int speed = 30;
    int damage = 0;
    public Bullet(Player owner, int position_x, int position_y, int angle, int damage, int speed)
    {
        super("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fc2.staticflickr.com%2F6%2F5603%2F15166218114_687c0b1e8a_z.jpg&f=1&nofb=1", position_x, position_y, 5, 5, angle);
        this.speed = speed;
        this.damage = damage;
        this.owner = owner;
    }


    void move()
    {
        x+=speed*Math.cos(Math.toRadians(angle-90));
        y+=speed*Math.sin(Math.toRadians(angle-90));
    }

    @Override
    public void update() {
        if (this.x>GameInfo.getInstance().boardsize[0] || this.x<0 ||this.y<0 || this.y>GameInfo.getInstance().boardsize[1])
            GameInfo.getInstance().removeObject(this);
        move();
    }

    public void damage(Player player)
    {
        if (player!=owner)
        {
            player.health-=damage;
            GameInfo.getInstance().removeObject(this);
        }
    }


    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
