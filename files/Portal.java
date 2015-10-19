import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Transports the player between maps.
 * There is one portal throughout the game.
 * 
 * Alina Vuong
 * 5.6.13
 */
public class Portal extends Actor
{
    private boolean alreadyTried = false;
    private GreenfootImage open = new GreenfootImage("lift-open.png");
    public GreenfootImage closed = new GreenfootImage("lift-closed.png");
    public void act() 
    {
        open();
        checkStep();
    }    

    private void checkStep()
    {
        Daumscape daum = (Daumscape) getWorld();        
        Player player = (Player) getOneIntersectingObject(Player.class); 

        if(player==null && alreadyTried == true)//used for when adding a box stating not cleared; prevents memory overflow
        {
            alreadyTried = false;
        }
        if(player!=null && alreadyTried == false)
        {
            alreadyTried = true;
            daum.changeNext();
        }
    }
    
    private void open() {
        Daumscape daum = (Daumscape) getWorld();
        List<DialogueBox> mobs = daum.getObjects(Mob.class);
        if(daum.dialogueDone() && mobs.size() == 0) {
            this.setImage(open);
        }
    }
}
