import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Sox--a woollie that got trapped in the facilities.
 * Sox is a special NPC.
 * 
 * Alina Vuong
 * 2.23.13
 */
public class Sox extends Mover implements Speaker
{
    private int distance = 80; //Maximum distance between Player and Sox.
    public boolean talkedTo = false;
    public Sox()
    {
        super();
        imageR1 = new GreenfootImage("soxR1.png");
        imageR2 = new GreenfootImage("soxR2.png");
        imageRWalk = new GreenfootImage("soxRWalk.png");

        imageL1 = new GreenfootImage("soxL1.png");
        imageL2 = new GreenfootImage("soxL2.png");
        imageLWalk = new GreenfootImage("soxLWalk.png");

        setImage(imageR1);

        name = "Sox";
        setDirection("right");
        speed = 1;
    }

    public void act() 
    {
        super.act();

        checkDirection();
        followPlayer();
        checkTalk();

        //If Player were to be removed from the world upon defeat, as it was at first,
        //this class would've had to continually check a boolean called playerOK because followPlayer() uses Player's fields
        //and would throw an IllegalStateException
    }    

    private void checkDirection()
    {
        Daumscape daum = (Daumscape) getWorld();
        if(bounceDelCount>bounceDelay) //set a delay so it doesn't look weird if you go back and forth
        //use the bounceDelCount b.c. it's convenient, don't need to make new variables
        {
            //don't need to reset the count because it's already being done in super.act()
            if(daum.player.getX()>getX())
            {
                setDirection("right");
            }
            else
            {
                setDirection("left");
            }
        }
    }

    private void followPlayer()
    {
        Daumscape daum = (Daumscape) getWorld();
        if (daum.mapIsClear()) {
            if(Math.abs(daum.player.getX()-getX())>distance)
            {
                if(daum.player.getX()>getX())
                {
                    setLocation(getX()+speed, getY());
                    super.walk();
                }
                else if(daum.player.getX()<getX())
                {
                    setLocation(getX()-speed, getY());
                    super.walk();
                }
            }
            if(Math.abs(daum.player.getY()-getY())>distance)
            {
                if(daum.player.getY()>getY())
                {
                    setLocation(getX(), getY()+speed);
                    super.walk();
                }
                if(daum.player.getY()<getY())
                {
                    setLocation(getX(), getY()-speed);
                    super.walk();
                }
            }
        }
    }

    public void checkTalk()
    {
        Player player = (Player) getOneIntersectingObject(Player.class);
        if(player!=null && !talkedTo)
        {
            talk();
        }
    }

    public void talk()
    {               
        Daumscape daum = (Daumscape) getWorld();
        int mapNum = daum.getMapNum();
        talkedTo = true;
    }
}

