import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A laser.
 * 
 * Alina Vuong
 * 5.9.13
 */
public class Laser extends Actor
{
    private String direction;
    private int speed = 6; //sets the distance covered per act-method
    private int damage = 20; //sets the amount of damage to be dealt on mobs/Player (depending on who fired it)
    private int life = 50; //sets the duration of the laser's lifespan
    private String className; //holds the name of the object that fired the laser

    public Laser(String direction, String theShooter)
    {
        this.direction = direction;
        className = theShooter;
    }

    public void act() 
    {
        life--;
        checkDuration();
    }    

    private void checkDuration()
    {
        if(life<=0 || atWorldEdge())
        {
            getWorld().removeObject(this);
        }
        else
        {
            move();
            checkHit();
        }
    }

    public void move()
    {
        if(direction.equals("right"))
        {
            setLocation(getX()+speed, getY());
        }
        if(direction.equals("left"))
        {
            setLocation(getX()-speed, getY());
        }
    }

    private boolean atWorldEdge()
    {
        Wall wall = (Wall) getOneIntersectingObject(Wall.class);
        if(wall!=null)
        {
            return true;
        }
        else if(getX()>=getWorld().getWidth()-1 || getX() == 0)
        {
            return true;
        }        
        return false;
    }

    private void checkHit()
    {
        if(className.equals("Player"))
        {
            Mob mob = (Mob) getOneIntersectingObject(Mob.class);
            if(mob!=null)
            {
                mob.hit(damage, getX(), getY()); //getX() and getY() for knockback effect
                getWorld().removeObject(this);
            }
        }
        if(className.equals("mob"))
        {
            Player player = (Player) getOneIntersectingObject(Player.class);
            if(player!=null)
            {
                player.hit(damage, getX(), getY());
                getWorld().removeObject(this);
            }
        }
    }
}
