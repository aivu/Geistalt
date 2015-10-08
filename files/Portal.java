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
        Jim jim = (Jim) getOneIntersectingObject(Jim.class); 

        if(jim==null && alreadyTried == true)//used for when adding a box stating not cleared; prevents memory overflow
        {
            alreadyTried = false;
        }
        if(jim!=null && alreadyTried == false)
        {
            alreadyTried = true;
            daum.changeNext();
        }
    }
    
    private void open()
    {
        Daumscape daum = (Daumscape) getWorld();
        List<DialogueBox> boxes = daum.getObjects(DialogueBox.class);
        if(daum.bossCount==0 && daum.internCount==0 && daum.npcCount==0 && boxes.size()<1)
        //boxes.size() parameter is so that the portal doesn't open until the conversations are over.
        {
            this.setImage(open);
        }
    }
}
