import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Spaceship.
 * 
 * Alina Vuong
 * 2.25.13
 */
public class Spaceship extends Actor
{
    private int animDelCount = 0;
    private int animateDelay = 30;
    
    private GreenfootImage ship1, ship2;
    public Spaceship()
    {
        ship1 = new GreenfootImage("spaceship1.png");
        ship2 = new GreenfootImage("spaceship2.png");
        setImage(ship1);
    }

    public void act() 
    {
        animDelCount++;
        animate();
    }    

    private void animate()
    {
        if(animDelCount>animateDelay)
        {
            animDelCount = 0;
            if(getImage()==ship1)
            {
                setImage(ship2);
            }
            else
            {
                setImage(ship1);
            }
        }
    }
}
