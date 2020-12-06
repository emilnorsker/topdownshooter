package norsker.topdownshooter.model.gamemodels;

public class Gameobject
{
    String sprite_location;
    int width;
    int height;
    int x;
    int y;
    int angle;

    public Gameobject(String sprite_location, int position_x, int position_y, int height, int width, int angle)
    {
        this.sprite_location=sprite_location;
        this.x=position_x;
        this.y=position_y;
        this.height=height;
        this.width = width;
        this.angle = angle;
    }

    public boolean intersects(Gameobject object)
    {
        if (this.x < object.x + object.width && this.x + this.width > object.x && this.y < object.y + object.height && this.y + this.height > object.y)
        {
            return true;
        }
        else return false;
    }

    public String getSprite_location() {
        return sprite_location;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAngle() {
        return angle;
    }

}
