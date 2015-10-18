import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * 
 * Alina Vuong
 * 2.24.13
 */
public class NPC extends Mover implements Speaker
{
    private boolean talkedTo; //tracks whether Player has already talked to the NPC; prevents memory overflow
    private String name;
    public NPC() {
        Daumscape daum = (Daumscape) getWorld();
        this.name = "Pythia";

        setDirection("right");
        walkDelay = 250; //use this variable for changeDirection() b.c. it's convenient
    }

    public NPC(String name, String imageR, String imageL, String direction) {
        Daumscape daum = (Daumscape) getWorld();
        this.name = name;
        talkedTo = false;
        imageR1 = new GreenfootImage(imageR);
        imageR2 = imageR1;
        imageL1 = new GreenfootImage(imageL);
        imageL2 = imageL1;

        setDirection(direction);
        walkDelay = 250; //use this variable for changeDirection() b.c. it's convenient
    }

    public void act() {
        super.act();
        //changeDirection(); //instead of super.walk(), because NPCs don't walk around
        checkTalk();
    }    

    private void changeDirection()
    {
        if(walkDelCount > walkDelay) {
            walkDelCount = 0;
            if(direction.equals("left")) {
                setDirection("right");
            } else {
                setDirection("left");
            }
        }
    }

    public void checkTalk() {
        Daumscape daum = (Daumscape) getWorld();
        Player player = (Player) getOneIntersectingObject(Player.class);
        if(player!=null && talkedTo==false) {
            talkedTo = true;
            talk();
        }
    }

    public void talk() {
        Daumscape daum = (Daumscape) getWorld();
        daum.talk(name);
    }
}
