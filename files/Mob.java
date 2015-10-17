import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * 
 * Alina Vuong
 * 2.23.13
 */
abstract class Mob extends Mover {  
    public int touchDmg; //to be defined in subclass; determines damage inflicted on player by touch
    public int aggroDelay = 30;
    public int aggroDelCount = 0;

    public Mob() //test
    {
        super();
        name = "mob";
        health = 100;
        setDirection("left");
        speed = 2;

        knockbackDelay = 20;
        kbDistance = 10;
    }

    public void act() 
    {
        super.act();
        aggroDelCount++;

        List<DialogueBox> boxes = getWorld().getObjects(DialogueBox.class);
        if(boxes.size()<1)
        {
            aggro();
            checkHit();
            checkHealth(); //always leave this last
        }

        //If player were to be removed from the world upon defeat, as it was at first,
        //this class would've had to continually check a boolean called playerOK because aggro() uses player's fields
        //and would throw an IllegalStateException
    }    

    public void aggro()
    {
        if(aggroDelCount>aggroDelay)
        {
            aggroDelCount = 0;

            Daumscape daum = (Daumscape) getWorld();

            if(daum.player.getX()>getX())
            {
                setLocation(getX()+speed, getY());
                setDirection("right");
                super.walk();
            }
            if(daum.player.getX()<getX())
            {
                setLocation(getX()-speed, getY());
                setDirection("left");
                super.walk();
            }
            if(daum.player.getY()>getY())
            {
                setLocation(getX(), getY()+speed);
            }
            if(daum.player.getY()<getY())
            {
                setLocation(getX(), getY()-speed);
            }
        }
    }

    public void hit(int damage, int x, int y)
    {
        if(x>getX()) //so the mobs "react" when they've been hit
        {
            setDirection("right");
        }
        else
        {
            setDirection("left");
        }
        super.hit(damage, x, y);
    }

    public void checkHit()
    {
        Player player = (Player) getOneIntersectingObject(Player.class);
        if(player!=null)
        {
            player.hit(touchDmg, getX(), getY());
        }
    }

    abstract void checkHealth(); //to be overridden in subclasses
}

