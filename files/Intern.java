import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * 
 * Alina Vuong
 * 2.23.13
 */
public class Intern extends Mob
{
    public Intern()
    {
        super();
        
        health = 30;
        speed = 2;

        touchDmg = 10;
    }

    public void act() 
    {
        super.act();
    }    

    public void checkHealth() //overrides checkHealth() in superclass Mob
    {
        if(health<=0)
        {
            Daumscape daum = (Daumscape) getWorld();
            
            daum.removeObject(this);
            daum.internCount--;
        }
    }
}
