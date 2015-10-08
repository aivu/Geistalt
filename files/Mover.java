import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * All the moving characters.
 * 
 * Alina Vuong
 * 2.25.13
 */
public class Mover extends Actor
{    
    //animation fields
    public String name; //to be defined in ALL subclasses
    public String direction; //to be defined in ALL subclasses
    public int speed; //to be defined in subclass Mob's subclasses

    //image fields--to be defined in subclass
    public GreenfootImage imageR1, imageR2, imageRWalk, imageL1, imageL2, imageLWalk;

    public int bounceDelCount = 0; //this will track the time since the last standing-bounce
    public int bounceDelay = 30; //this sets the minimum required time between animation changes while standing still
    public int walkDelCount = 0; //this will track the time since the last walking-bounce
    public int walkDelay = 15; //this sets the minimum required time between animation changes while walking

    //health and damage fields
    public int health, atkDelay, dmgDelay, knockbackDelay, kbDistance; //to be defined in the subclasses Mob and Jim
    public int hpDelay; //to be defined in class Jim; sets the minimum required time between instance of HP recovery
    public int atkDelCount = 0;
    public int dmgDelCount = 0;
    public int hpDelCount = 0;
    public int kbDelCount = 0;

    public void act() 
    {
        animate();
        bounceDelCount++;
        walkDelCount++;        

        atkDelCount++;
        dmgDelCount++;
        hpDelCount++;
        kbDelCount++;
    }

    public void animate()
    {
        if(bounceDelCount>bounceDelay)
        {
            bounceDelCount = 0;
            if((getImage()==imageR1 || getImage()==imageR2 || getImage()==imageRWalk))
            {
                if(getImage()==imageR1)
                {
                    setImage(imageR2);
                }
                else
                {
                    setImage(imageR1);
                }
            }
            else if(getImage()==imageL1 || getImage()==imageL2 || getImage()==imageLWalk)
            {
                if(getImage()==imageL1)
                {
                    setImage(imageL2);
                }
                else
                {
                    setImage(imageL1);
                }
            }
        }
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
        if(this.direction.equals("right"))
        {
            if(getImage()==imageL1 || getImage()==imageL2 || getImage()==imageLWalk) 
            {
                setImage(imageR1);
            }
        }
        if(this.direction.equals("left"))
        {
            if(getImage()==imageR1 || getImage()==imageR2 || getImage()==imageRWalk)
            {
                setImage(imageL1);
            }
        }
    }

    public void hit(int damage, int x, int y) //will be invoked by the Laser and Mob classes. ref: newAsteroids
    {
        if(dmgDelCount>dmgDelay)
        {
            dmgDelCount = 0;
            health -= damage;
        }
        if(kbDelCount>knockbackDelay)
        {
            kbDelCount = 0;
            if(x>getX())
            {   
                this.setLocation(getX()-kbDistance, getY());
            }
            else if(x<getX())
            {
                this.setLocation(getX()+kbDistance, getY());
            }
        }
    }

    public void add(int recovery) //will be invoked by the Flan class. This method is really just for Jim.
    {
        if(hpDelCount>hpDelay)
        {
            hpDelCount = 0;
            health += recovery;
        }
    }

    public void fire() //some parts of this and related methods were inspired by the newAsteroids project
    {
        Daumscape daum = (Daumscape) getWorld();        
        //note: you have to declare "daum" separately from whatever statements you reference it in
        //e.g. it wouldn't work if you tried to declare it while trying to reference ".soundOn"
        
        if(atkDelCount>atkDelay)
        {
            atkDelCount = 0;
            Laser laser = new Laser(direction, name);
            daum.addObject(laser, getX(), getY());

            //the laser sound effect is included in Mover.fire() so as to employ the delay counts already being
            //used for the actual laser function
            if(daum.soundOn) //can't put this in the Laser constructor because you can't call other objects in an object's constructor
            //I suppose because the object hasn't actually been constructed yet and nonexisting objects can't make other
            //objects do things
            {
                Greenfoot.playSound("laser.mp3");
            }
        }
    }  

    public void walk() //don't put act()
    {
        if(direction.equals("right"))
        {
            if(walkDelCount>walkDelay)
            {
                walkDelCount = 0;
                if(getImage()==imageR1 || getImage()==imageR2)
                {
                    setImage(imageRWalk);
                }
                else
                {
                    setImage(imageR1);
                }
            }
        }
        if(direction.equals("left"))
        {
            if(walkDelCount>walkDelay)
            {
                walkDelCount = 0;
                if(getImage()==imageL1 || getImage()==imageL2)
                {
                    setImage(imageLWalk);
                }
                else
                {
                    setImage(imageL1);
                }
            }
        }
    }

    public boolean jimOK() 
    //this would've been the method to use in some classes' act()s if the game ended upon Jim's defeat
    {
        Daumscape daum = (Daumscape) getWorld();
        if(daum.jim.health>0) //prevents an IllegalStateException when Jim is defeated
        {
            return true;
        }
        return false;
    }
}
