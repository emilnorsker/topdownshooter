package norsker.topdownshooter.model.gamemodels;

public class Weapon implements Updatable
{
    int damage;
    int cooldown;
    int currentColdown = 0;
    int velocity;

    public Weapon()
    {
        velocity = 10;
        damage = 40;
        cooldown = 60;
    }

    public boolean canShoot()
    {
        if (currentColdown<1)
            return true;
        else
            return false;
    }


    @Override
    public void update() {
        currentColdown--;
    }


    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public int getCurrentColdown() {
        return currentColdown;
    }

    public void setCurrentColdown(int currentColdown) {
        this.currentColdown = currentColdown;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void shoot()
    {
        this.currentColdown=cooldown;
    }
}
