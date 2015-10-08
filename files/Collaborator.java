import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Specialized mob class.
 * 
 * Alina Vuong
 * 2.24.13
 */
public class Collaborator extends Mob
{
    public Collaborator(int speed, int health, int touchDmg, int atkDelay)
    {
        super();

        this.health = health;
        this.speed = speed;

        this.touchDmg = touchDmg;
        this.atkDelay = atkDelay;
    }

    public void act() 
    {
        List<DialogueBox> boxes = getWorld().getObjects(DialogueBox.class);
        if(boxes.size()<1)
        {
            aggro();
        }
        super.act();
    }    

    public void checkHealth() //overrides super.checkHealth() to account for bossCount
    {
        if(health<=0)
        {
            Daumscape daum = (Daumscape) getWorld();
            daum.addBossNPCs(getX(), getY());

            daum.removeObject(this);
            daum.bossCount--;
        }
    }

    public void aggro()
    {
        super.aggro();
        fire();
    }
}
