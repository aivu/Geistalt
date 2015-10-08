import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.awt.Font;
import java.util.Calendar;

/**
 * The dialogue box holds the dialogue of a speaker.
 * It shows the speaker's name and up to 3 lines of text per box.
 * 
 * Alina Vuong
 * 2.24.13
 */
public class DialogueBox extends Actor
{
    private float nameSize = 20.0f; //the font size of the name
    private float textSize = 20.0f; //the font size of the dialogue text
    private float instrSize = 12.0f; //the font size of the "press enter" text

    private int width = 650;
    public int height = 200; //will be referenced by the NPC class.

    private int boxDelay = 10;
    private int boxDelCount = 0; //otherwise pressing enter would stream through all the text

    private String name;
    private int chatNum;

    public DialogueBox(String npcName, int number) //test
    {
        this.name = npcName;
        chatNum = number;
        chat();
    }

    public void act() 
    {
        boxDelCount++;
        checkPress();
    }    

    private void update(String name, String dialogue1, String dialogue2, String dialogue3)
    {
        GreenfootImage image = new GreenfootImage(width, height);

        image.setColor(new Color(200, 70, 10, 128)); //sets color of the image
        image.fillRect(0, 0, width, height); //image is a rectangle

        image.setColor(new Color(0, 0, 0, 128)); //sets color of 2nd image
        image.fillRect(5, 5, width-10, height-10); //2nd image is a smaller rectangle

        //put text into the box
        //Font font = image.getFont();
        Font font = new java.awt.Font("Courier New",Font.PLAIN,14);

        font = font.deriveFont(nameSize);
        image.setFont(font);
        image.setColor(new Color(150,150,150));
        image.drawString(name, 30, 40); //draws the name of the speaker

        image.setColor(Color.WHITE);
        image.drawString(dialogue1, 30, 80); //draws the first line of dialogue
        image.drawString(dialogue2, 30, 105); //draws the second line of dialogue
        image.drawString(dialogue3, 30, 130); //draws the second line of dialogue

        font = font.deriveFont(instrSize);
        image.setFont(font);
        image.setColor(new Color(150,150,150));
        image.drawString("Press enter to continue.", width-190, height-20);

        setImage(image);
    }

    private void checkPress()
    {
        if(boxDelCount>boxDelay)
        {
            boxDelCount = 0;
            if(Greenfoot.isKeyDown("enter"))
            {
                chatNum++;
                getImage().clear();
                chat();
            }
        }
    }

    private void chat()
    {
        Daumscape daum = (Daumscape) getWorld();
        if(name.equals("Instructions:"))
        {
            if(chatNum==0)
            {
                update(name, "Use the arrow keys to move and the spacebar","to throw dodgeballs.", "Walk on NPCs to talk to them.");
            }
            if(chatNum==1)
            {
                daum.removeObject(this);
            }
        }
        if(name.equals("Researcher"))
        {
            if(chatNum==0)
            {
                int random = Greenfoot.getRandomNumber(7);
                if(random<2)
                {
                    update(name, "Huh?","","");
                }
                if(random>1 && random<4)
                {
                    update(name, "What do you want?", "", "");
                }
                if(random>3 && random<6)
                {
                    update(name, "I don't know what you're talking about.","","");
                }
                if(random>5 && random<8)
                {
                    update(name, "What?", "","");
                }
            }
            if(chatNum==1)
            {
                daum.removeObject(this);
            }
        }
        if(name.equals("Jim"))
        {
            if(chatNum==0)
            {
                update(name, "Hm?", "I don't appreciate their attitude.","");                
            }
            if(chatNum==1)
            {
                update(name, "It hasn't even been that long since I died!","I'm gonna go see my friend.","");
            }
            if(chatNum==2)
            {
                daum.removeObject(this);
            }
            if(chatNum==3)
            {
                update(name, "And then after I'll go to mall.", "","");
            }
            if(chatNum==4)
            {
                update(name, "...And window shop?","","");
            }
            if(chatNum==5)
            {
                update(name, "Anyways.","","");
            }
            if(chatNum==6)
            {
                daum.removeObject(this);
            }
        }        
        if(name.equals("Regimund"))
        {
            if(chatNum==0)
            {
                update(name, "Jim!", "","");
            }
            if(chatNum==1)
            {
                update(name, "What do you think you're doing here?","", "");
            }
            if(chatNum==2)
            {
                update(name, "Look, haven't you died already?","","");
            }
            if(chatNum==3)
            {
                update("Jim","What? So?","","");
            }
            if(chatNum==4)
            {
                update("Jim","Anyways. What's my bestie up to these days?","", "");
            }
            if(chatNum==5)
            {
                update(name, "She's doing so-so. She's still going to class", "at Hellhouse at least.","");
            }
            if(chatNum==6)
            {
                update("Jim", "Mmmmm.","Okay.","");
            }
            if(chatNum==7)
            {
                daum.removeObject(this);
            }
        }
        if(name.equals("Interns"))
        {
            if(chatNum==0)
            {
                update("Jim", "Who are these fools?","Fine. Let's play.","");
            }
            if(chatNum==1)
            {
                update("Instructions:", "Press the spacebar to shoot dodgeballs at these", "fools. Don't let them touch you!", "");
            }
            if(chatNum==2)
            {
                name = "Jim";
                chatNum = 3;
                chat();
            }
        }
        if(name.equals("Larimen"))
        {
            if(chatNum==3) //out of order because of how I coded checkTalk() in NPC class
            {
                update(name, "That's them.","","");
            }
            if(chatNum==4)
            {
                update(name, "We don't pay you nothing for nothing.","","Go!");
            }
            if(chatNum==5)
            {
                daum.removeObject(this);
            }
            if(chatNum==0)
            {
                update(name, "No...","","");
            }
            if(chatNum==1)
            {
                update(name, "Can't let them go home--can't let them ruin our", "plans...","");
            }           
            if(chatNum==2)
            {
                daum.removeObject(this);
            }
        }
        if(name.equals("Galea"))
        {
            if(chatNum==0)
            {
                update("Jim","Galea!","","");
            }
            if(chatNum==1)
            {
                update(name, "So you didn't go, huh? I heard from Reggie.","","");
            }
            if(chatNum==2)
            {
                update("Jim", "Long time no see.", "Listen, do you know where the woollie is?","");
            }
            if(chatNum==3)
            {
                update(name, "...","Hmm.","");
            }
            if(chatNum==4)
            {
                update(name, "I heard they're being kept in a sublevel. The", "sixth, I think.","");
            }
            if(chatNum==5)
            {
                update(name,"If you're going to go, you should hurry. All", "the higher-ups know you're here. It's not","going to be easy.");
            }
            if(chatNum==6)
            {
                update(name, "That's not the only thing going on around here","either.","");
            }            
            if(chatNum==7)
            {
                update("Jim","...?","","");
            }
            if(chatNum==8)
            {
                update(name, "Hmm.","","You'll see.");
            }
            if(chatNum==9)
            {
                update(name, "Watch out for Calico and Gunther.", "If you're careful, you can probably pull it off.","");
            }
            if(chatNum==10)
            {
                daum.removeObject(this);
            }
        }
        if(name.equals("Sox"))
        {
            if(chatNum==0)
            {
                update(name,"Hey!","","");                
            }
            if(chatNum==1)
            {
                update(name, "You made it!","","");
            }
            if(chatNum==2)
            {
                update(name, "Look, we gotta get a ship and fly outta here.","","Or else!");
            }
            if(chatNum==3)
            {                
                daum.removeObject(this);
            }
            if(chatNum==4)
            {
                update(name, "I was listening to the guards--they like to", "talk around here. It gets boring, you know?", "");
            }
            if(chatNum==5)
            {
                update(name, "Anyway, they were talking about a spaceship","on the last floor.","");
            }
            if(chatNum==6)
            {
                update(name,"And the keys are in Gunther's office! I don't", "know who that is, though. Do you?","");
            }
            if(chatNum==7)
            {
                update("Jim","Nope.","","");
            }
            if(chatNum==8)
            {
                update(name, "Whatever. Let's go!","","");
            }
            if(chatNum==9)
            {
                daum.removeObject(this);
            }
            if(chatNum==10)
            {
                update(name, "Hey, that's Calico! He's the second-in-command.","He probably knows something.","");
            }
            if(chatNum==11)
            {
                daum.removeObject(this);
            }
        }
        if(name.equals("Calico"))
        {
            if(chatNum==0)
            {
                update(name, "Even if you stop me, Teguin will still be ours.","","");
            }
            if(chatNum==1)
            {
                update("Sox","Our home planet!","","");
            }
            if(chatNum==2)
            {
                update(name, "Hahaha. Our invasion cannot be stopped.","","");
            }
            if(chatNum==3)
            {
                update(name, "Gunther awaits...","","");
            }
            if(chatNum==4)
            {
                daum.removeObject(this);
            }
        }
        if(name.equals("Gunther"))
        {
            if(chatNum==0)
            {
                update("","You receive Gunther's Keys.","","");
            }
            if(chatNum==1)
            {
                update(name, "Ugh... Fine.","","");
            }
            if(chatNum==2)
            {
                daum.removeObject(this);
            }
            if(chatNum==3)
            {
                update("Sox","Gunther?","","");
            }
            if(chatNum==4)
            {
                update(name, "Yes?","","");
            }
            if(chatNum==5)
            {
                update("Jim","Give us your keys!","","");                
            }
            if(chatNum==6)
            {
                update("Sox","We're going back to Teguin!","","");
            }
            if(chatNum==7)
            {
                update(name, "Hmm...","","");                
            }
            if(chatNum==8)
            {
                update(name, "No.","","");
            }
            if(chatNum==9)
            {
                daum.removeObject(this);
            }
        }
        if(name.equals(""))
        {
            if(chatNum==0)
            {
                update("Sox", "Let's go home.","","");
            }
            if(chatNum==1)
            {
                daum.removeObject(this);
            }
        }
        if(name.equals("Credits"))
        {
            if(chatNum==0)
            {
                update("Thanks to:","Ms. D and Renee Rolos Prescilla--","and lots of other people besides!","");
            }
            if(chatNum==1)
            {
                update("References and Inspiration:","The newAsteroids and littleCrab games by Michael","Kolling; people talking on stackoverflow;","the game Starbound by Rolos.");
            }
            if(chatNum==2)
            {
                update("Music:", "'Liquid Mountains' by Ambient Frequencies","'Always With Me' by Joe Hisaishi","");
            }
            if(chatNum==3)
            {
                update("","Thanks for playing!","","");
            }
        }
    }
}
