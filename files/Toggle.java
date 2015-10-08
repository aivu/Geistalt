import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * @Alina Vuong
 * 
 * 5/9/13
 * 
 * This is the class Toggle. It regulates the sound settings of the game.
 */

public class Toggle extends Actor
{
    private int tog = 0;
    
    private GreenfootImage noMusic, noSound, allSound;
    public Toggle()
    {
        noMusic = new GreenfootImage("noMusic.png");
        noSound = new GreenfootImage("noSound.png");
        allSound = new GreenfootImage("allSound.png");
        
        setImage(allSound);
    }

    public void act() 
    {
        checkToggle();
    }    
    
    private void checkToggle()
    {
        if(Greenfoot.mouseClicked(this))
        {
            tog++;
            tog%=3;
            setSound();
        }
    }
    
    private void setSound()
    {
        Daumscape daum = (Daumscape) getWorld();
        if(tog==0)
        {
            daum.bgmOn = true;
            daum.soundOn = true;
            
            setImage(allSound);
        }
        else if(tog==1)
        {
            daum.bgmOn = false;
            
            setImage(noMusic);
        }
        else if(tog==2)
        {
            daum.soundOn = false;
            
            setImage(noSound);
        }
    }
}
