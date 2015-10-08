import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Upon ingestion, will cause Jim to recover some HP.
 * 
 * Alina Vuong
 * 5.9.13
 */
public class Flan extends Actor
{
    private int hpValue= 20;
    public void act() 
    {
        checkIfEaten();
    }    

    private void checkIfEaten()
    {
        Jim jim = (Jim) getOneIntersectingObject(Jim.class);
        if(jim!=null)
        {
            jim.add(hpValue);
            getWorld().removeObject(this);
        }
    }
}
