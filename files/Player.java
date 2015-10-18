import greenfoot.*;
import java.util.List;

/**
 * 
 * Alina Vuong
 * 5.9.13
 */
public class Player extends Mover
{
    private int maxHP = 100;
    private boolean revived;
    public boolean realized; //reset after every changeMap(); for bits of plot dialogue
    public int realizeDelCount = 0; //same as above
    private int realizeDelay = 30;
    private DialogueBox box;
    public Player(DialogueBox b) {
        super();
        box = b;
        imageR1 = new GreenfootImage("Ghost Sprite Right 1.png");
        imageR2 = new GreenfootImage("Ghost Sprite Right 3.png");
        imageRWalk = new GreenfootImage("Ghost Sprite Right 2.png");

        imageL1 = new GreenfootImage("Ghost Sprite Left 1.png");
        imageL2 = new GreenfootImage("Ghost Sprite Left 3.png");
        imageLWalk = new GreenfootImage("Ghost Sprite Left 2.png");

        setImage(imageR1);

        name = "Damon";   
        setDirection("right");
        speed = 2;

        health = maxHP;
        atkDelay = 25;
        dmgDelay = 50;
        hpDelay = 50;

        knockbackDelay = 50;
        kbDistance = 10;
    }

    public void act()
    {
        super.act();
        animate();
        checkKeys();
        hpLimit();
        checkHealth();//always leave this last
    }

    public void animate() //overrides the same method in the superclass Mover
    {
        if(!Greenfoot.isKeyDown("right") && !Greenfoot.isKeyDown("left") && !Greenfoot.isKeyDown("up") && !Greenfoot.isKeyDown("down"))
        {
            super.animate();
        }
    }

    private void checkKeys()
    {
        if(!box.visible()) {
            boolean right = Greenfoot.isKeyDown("right");
            boolean left = Greenfoot.isKeyDown("left");
            boolean up = Greenfoot.isKeyDown("up");
            boolean down = Greenfoot.isKeyDown("down");

            if (right || left || up || down) {
                if (right) {
                    setDirection("right");
                    setLocation(getX()+speed, getY());
                    super.walk();
                } else if (left) {
                    setDirection("left");
                    setLocation(getX()-speed, getY());
                    super.walk();
                }
                if (up) {
                    setLocation(getX(), getY()-speed);
                } else if (down) {
                    setLocation(getX(), getY()+speed);
                }
                if(walkDelCount > walkDelay) {
                    walkDelCount = 0;
                    if(getImage()==imageR1 || getImage()==imageR2) {
                        setImage(imageRWalk);
                    } else if(getImage()==imageRWalk) {
                        setImage(imageR1);
                    } else if(getImage()==imageL1 || getImage()==imageL2) {
                        setImage(imageLWalk);
                    } else if(getImage()==imageLWalk) {
                        setImage(imageL1);
                    }
                }
            }
            if(Greenfoot.isKeyDown("space")) {
                fire(); //defined in superclass Mover
            }
        }
    }

    private void hpLimit()
    {
        if(health>maxHP) //prevents flan from causing hp to go over 100
        {
            health -= health%maxHP; //or health = maxHP;
        }
        if(health<0) //prevents negatives from showing up in the hp counter
        {
            health = 0;
        }
    }

    public void checkHealth()
    {
        if(health<=0)
        {
            savePoint();
        }
    }

    private void savePoint()
    {
        if(!revived)
        {
            revived = true; //later set to alternative dialogue; i.e. set true after talking to galea, who'll say s.t. different
            Daumscape daum = (Daumscape) getWorld();  
            int mapNum = daum.getMapNum();

            List<Mob> mobs = getWorld().getObjects(Mob.class);
            getWorld().removeObjects(mobs);
            if(mapNum > 3) {
                daum.updateMap();
                health = 100;
            }
        }
    }    
}
