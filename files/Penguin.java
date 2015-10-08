import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * 
 * 
 * Alina Vuong
 * 2.23.13
 */
public class Penguin extends Mover
{
    public Penguin() //for testing purposes
    {
        super();

        imageR1 = new GreenfootImage("penguinR1.png");
        imageR2 = new GreenfootImage("penguinR2.png");
        imageRWalk = new GreenfootImage("penguinRWalk.png");
        imageL1 = new GreenfootImage("penguinL1.png");
        imageL2 = new GreenfootImage("penguinL2.png");
        imageLWalk = new GreenfootImage("penguinLWalk.png");

        setImage(imageL2); //must set an image even though setDirection should be doing that
        //they don't switch at all otherwise
        setDirection("right");
    }

    public void act() 
    {
        super.act();
    }
}
