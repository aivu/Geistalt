import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
import java.awt.Graphics;

/**
 * Tracks Player's remaining HP.
 * The Counter class from newAsteroids was a significant reference.
 * 
 * Alina Vuong
 * 2.23.13
 */
public class HPCounter extends Actor
{
    private static final Color textColor = new Color(70, 100, 90);
    private int hp = 100;
    private int newHP = 0;
    private String text;
    private int stringLength;

    public HPCounter()
    {
        text = "HP: ";
        stringLength = (text.length()+2) * 10;

        setImage(new GreenfootImage(stringLength, 16));
        GreenfootImage image = getImage();
        image.setColor(textColor);

        updateImage();
    }

    public void act() 
    {
        Daumscape daum = (Daumscape) getWorld();
        newHP = daum.player.health;
        if(hp < newHP)
        {
            hp++;
            updateImage();
        }
        else if(hp > newHP)
        {
            hp--;
            updateImage();
        }
    }

    public void add(int recovery)
    {
        hp += recovery;
    }

    public void subtract(int damage)
    {
        hp -= damage;
    }

    private void updateImage()
    {
        GreenfootImage image = getImage();
        image.clear();
        image.drawString(text + hp, 1, 12);
    }
}

