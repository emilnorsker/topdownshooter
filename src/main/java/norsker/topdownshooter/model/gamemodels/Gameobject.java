package norsker.topdownshooter.model.gamemodels;

public class Gameobject
{
    String sprite_location;
    int width;
    int height;
    int x;
    int y;
    int angle;
    String type;

    public Gameobject(String sprite_location, int position_x, int position_y, int height, int width, int angle)
    {
        this.sprite_location=sprite_location;
        this.x=position_x;
        this.y=position_y;
        this.height=height;
        this.width = width;
        this.angle = angle;
        this.type = this.getClass().getSimpleName();
    }

    public boolean intersects(Gameobject object)
    {
        if (this.x-width/2 < object.x + object.width && this.x-width/2 + this.width > object.x && this.y-height/2 < object.y + object.height && this.y-height/2 + this.height > object.y)
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

    public void setSprite_location(String sprite_location) {
        this.sprite_location = sprite_location;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAngle() {
        return angle;
    }

}
