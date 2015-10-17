import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Upon ingestion, will cause Player to recover some HP.
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
        Player player = (Player) getOneIntersectingObject(Player.class);
        if(player!=null)
        {
            player.add(hpValue);
            getWorld().removeObject(this);
        }
    }
}
