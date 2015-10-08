import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *Intro sequence.
 *
 *Alina Vuong
 *2.25.13
 */
public class Intro extends Actor
{
    private GreenfootImage intro1 = new GreenfootImage("intro1.png");
    private GreenfootImage intro2 = new GreenfootImage("intro2.png");
    private GreenfootImage intro3 = new GreenfootImage("intro3.png");
    private GreenfootImage intro4 = new GreenfootImage("intro4.png");
    private int proceedDelCount = 0;
    private int proceedDelay = 15;
    public Intro()
    {
        setImage(intro1);
    }
    public void act() 
    {
        proceedDelCount++;
        checkKeys();
    }    
    private void checkKeys()
    {
        if(Greenfoot.isKeyDown("enter")&&proceedDelCount>proceedDelay)
        {
            proceedDelCount = 0;
            if(getImage()==intro1)
            {
                setImage(intro2);
            }
            else if(getImage()==intro2)
            {
                setImage(intro3);
            }
            else if(getImage()==intro3)
            {
                setImage(intro4);
            }
            else if(getImage()==intro4)
            {
                getWorld().removeObject(this);
            }
        }
    }
}
