import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Prop.
 * 
 * Alina Vuong
 * 2.25.13
 */
public class Prop extends Actor
{
    private int animDelCount = 0;
    private int animateDelay = 30;
    
    private GreenfootImage img1, img2;
    public Prop(String image1, String image2) {
        img1 = new GreenfootImage(image1);
        img2 = new GreenfootImage(image2);
        setImage(img2);
    }

    public void act() {
        animDelCount++;
        animate();
    }    

    private void animate() {
        if (animDelCount > animateDelay) {
            animDelCount = 0;
            if (getImage() == img1) {
                setImage(img2);
            } else {
                setImage(img1);
            }
        }
    }
}
