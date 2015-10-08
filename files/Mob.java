import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * 
 * Alina Vuong
 * 2.23.13
 */
abstract class Mob extends Penguin
{  
    public int touchDmg; //to be defined in subclass; determines damage inflicted on Jim by touch
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

        //If Jim were to be removed from the world upon defeat, as it was at first,
        //this class would've had to continually check a boolean called jimOK because aggro() uses Jim's fields
        //and would throw an IllegalStateException
    }    

    public void aggro()
    {
        if(aggroDelCount>aggroDelay)
        {
            aggroDelCount = 0;

            Daumscape daum = (Daumscape) getWorld();

            if(daum.jim.getX()>getX())
            {
                setLocation(getX()+speed, getY());
                setDirection("right");
                super.walk();
            }
            if(daum.jim.getX()<getX())
            {
                setLocation(getX()-speed, getY());
                setDirection("left");
                super.walk();
            }
            if(daum.jim.getY()>getY())
            {
                setLocation(getX(), getY()+speed);
            }
            if(daum.jim.getY()<getY())
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
        Jim jim = (Jim) getOneIntersectingObject(Jim.class);
        if(jim!=null)
        {
            jim.hit(touchDmg, getX(), getY());
        }
    }

    abstract void checkHealth(); //to be overridden in subclasses
}

