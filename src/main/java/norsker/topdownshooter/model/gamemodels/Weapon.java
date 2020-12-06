package norsker.topdownshooter.model.gamemodels;

public class Weapon implements Updatable
{
    int damage;
    int cooldown;
    int currentColdown = 0;

    public Weapon()
    {
        damage = 10;
        cooldown = 5;
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
}
