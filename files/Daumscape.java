import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayDeque;
/**
 * 
 * @Alina Vuong 
 * 5.9.13
 */
public class Daumscape extends World
{
    //fields
    public Player player;
    private Portal port = new Portal();
    private HPCounter counter = new HPCounter();
    private Toggle toggle = new Toggle();
    private DialogueBox box = new DialogueBox();
    private boolean mapIsClear;
    private int mapNum = 0;
    static int TOTALMAPS = 5;
    static int LAST = TOTALMAPS - 1;

    private String[] counts = new String[18]; //the numbers are boss, interns and NPC counts, respectively

    private GreenfootImage space = new GreenfootImage("space.jpg");
    private int lastBoxDelCount = 0;
    private int lastBoxDelay = 250;
    private boolean boxAdded, lastAdded, creditsAdded; //keeps certain boxes from being added funny at different parts of the game

    public GreenfootSound bgm = new GreenfootSound("LiquidMountains.mp3");
    public boolean bgmOn = true;
    public boolean soundOn = true;
    private boolean dialogueDone = false;
    public Daumscape()
    {    
        super(750, 550, 1); 

        setPaintOrder(Intro.class, DialogueBox.class, HPCounter.class, Player.class, Laser.class, Flan.class, Portal.class, Toggle.class, Wall.class);

        //addObject(new Intro(), getWidth()/2, getHeight()/2);
        addPlayer();
        //addCounter();
        addToggle();
        addPortal();
        addWalls();

        updateMap();
        //bgm.play();
        addObject(box, getWidth()/2, getHeight()-box.height+50);
        box.chat("intro");

        ArrayDeque<String> seq1 = new ArrayDeque();
        seq1.add("Millie");
        seq1.add("Pythia? Hey Pythia!");
        seq1.add("How are you? I haven't seen you around much");
        seq1.add("lately. Is everything okay?");
        seq1.add("Pythia");
        seq1.add("Yeah. I just haven't been around.");
        seq1.add("Why do you ask?");
        seq1.add("");
        seq1.add("Millie");
        seq1.add("I dunno, you just seem a little down lately.");
        seq1.add("");
        seq1.add("");
        seq1.add("Pythia");
        seq1.add("Well, I'm fine.");
        seq1.add("");
        seq1.add("");
        seq1.add("Millie");
        seq1.add("Oh.");
        seq1.add("Wanna get food sometime?");
        seq1.add("Any time that works for you, I'm flexible.");
        seq1.add("Pythia");
        seq1.add("Maybe. I don't really feel like going out.");
        seq1.add("It's expensive.");
        seq1.add("");
        seq1.add("Millie");
        seq1.add("Okay... I don't want to force you.");
        seq1.add("It's just that Isaac and I miss hanging out with");
        seq1.add("you. Let me know, okay?");
        seq1.add("Pythia");
        seq1.add("I can call you after work and let you know.");
        seq1.add("");
        seq1.add("");
        seq1.add("Millie");
        seq1.add("Alright. Talk to you later.");
        seq1.add("");
        seq1.add("");
        box.add("who's home?", seq1);

        ArrayDeque<String> seq2 = new ArrayDeque();
        seq2.add("Isaac");
        seq2.add("...And then everyone's phones go off! It was");
        seq2.add("like this big secret society flash mob just");
        seq2.add("poofed into existence. So extreme, I tell you.");
        seq2.add("Millie");
        seq2.add("Yeah! It was like the whole was in on it!");
        seq2.add("...Oh. Are you okay, Pythia?");
        seq2.add("What's on your mind?");
        seq2.add("Pythia");
        seq2.add("Huh? Oh, sorry, what were you saying?");
        seq2.add("");
        seq2.add("");
        seq2.add("Millie");
        seq2.add("It's okay, it was just a silly story. We've");
        seq2.add("been talking all night. What've you been");
        seq2.add("up to lately? Still looking for a roommate?");
        seq2.add("Pythia");
        seq2.add("Nothing really.");
        seq2.add("And yeah, maybe. I might move out. The place");
        seq2.add("doesn't feel right and the landlord is a jerk.");
        seq2.add("Millie");
        seq2.add("Man, did we have good times at your place.");
        seq2.add("Do you want us to ask around if anyone's");
        seq2.add("looking for a roommate?");
        seq2.add("Pythia");
        seq2.add("No, I think I need my own place.");
        seq2.add("");
        seq2.add("");
        seq2.add("Millie");
        seq2.add("Would be a bit neater huh?");
        seq2.add("Haha.");
        seq2.add("");
        seq2.add("Pythia");
        seq2.add("");
        seq2.add("...");
        seq2.add("");
        seq2.add("Millie");
        seq2.add("Ah, well.");
        seq2.add("Maybe some more drinks?");
        seq2.add("");
        seq2.add("Damon");
        seq2.add("What should Isaac drink~?");
        seq2.add("");
        seq2.add("Hm!");
        box.add("drinks", seq2);

        ArrayDeque<String> chooseDrink = new ArrayDeque();
        chooseDrink.add("Press the key to choose a drink for Isaac");
        chooseDrink.add("(A) whisky");
        chooseDrink.add("(B) butterbeer");
        chooseDrink.add("(C) Pepsi-Cola");
        chooseDrink.add("Whisky");
        chooseDrink.add("*burp*");
        chooseDrink.add("I don't think I liked that too much.");
        chooseDrink.add("");
        chooseDrink.add("Butterbeer");
        chooseDrink.add("*burp*");
        chooseDrink.add("I don't think I liked that too much.");
        chooseDrink.add("");
        chooseDrink.add("Pepsi-Cola");
        chooseDrink.add("*burp*");
        chooseDrink.add("I don't think I liked that too much.");
        chooseDrink.add("");
        box.add("chooseDrink", chooseDrink, "drinks");

        ArrayDeque<String> seq3 = new ArrayDeque();
        seq3.add("Millie");
        seq3.add("Woo!");
        seq3.add("I love disco, honestly.");
        seq3.add("");
        seq3.add("Isaac");
        seq3.add("And I love you!!!");
        seq3.add("");
        seq3.add("");
        seq3.add("Pythia");
        seq3.add("Sigh...");
        seq3.add("");
        seq3.add("");
        seq3.add("Simon");
        seq3.add("Not dancing with your friends? How ");
        seq3.add("about with that cute boy over there? Might've");
        seq3.add("been one of your friend's dance partners at one time.");
        seq3.add("Pythia");
        seq3.add("Oh... I guess.");
        seq3.add("");
        seq3.add("");
        box.add("the disco", seq3);

        ArrayDeque<String> chooseDance = new ArrayDeque();
        chooseDance.add("Damon. What does she want to do?");
        chooseDance.add("(A) Goes up to him to dance.");
        chooseDance.add("(B) Stays at the counter.");
        chooseDance.add("(C) Gives him some dance tips.");
        chooseDance.add("Go up to him and dance.");
        chooseDance.add("You go up to him and you both dance.");
        chooseDance.add("But, even though you're both smiling,");
        chooseDance.add("you feel a bit quiet inside.");
        chooseDance.add("Stay at the counter.");
        chooseDance.add("You sit quietly in response to Simon's");
        chooseDance.add("suggestion. Some chaotic thoughts almost come back");
        chooseDance.add("to you. But you let the music drown them out.");
        chooseDance.add("Give him some dance tips.");
        chooseDance.add("You go up to him and tells him he's off beat.");
        chooseDance.add("\"Aren't you Damon's friend? I haven't seen");
        chooseDance.add("him lately. Also, you're not even dancing, girl.\"");
        chooseDance.add("Millie");
        chooseDance.add("I know. I know I haven't.");
        chooseDance.add("");
        chooseDance.add("");
        box.add("chooseDance", seq3);

    }

    public void act()
    {        
        if(mapNum!=LAST) {
            if(bgmOn) {
                if(!bgm.isPlaying()) {
                    bgm.play();
                }
            } else if(!bgmOn) {
                if(bgm.isPlaying()) {
                    bgm.stop();
                }
            }
        }
        if(mapNum==LAST) {
            if(!lastAdded) {
                lastBoxDelCount++;    
                if(lastBoxDelCount>lastBoxDelay)
                {
                    lastBoxDelCount = 0;
                    lastAdded = true;
                }
            }
            if(lastAdded && !creditsAdded)
            {
                if(box.visible()) //Prevents the credits from covering Sox's last line after only a small amount of time.
                {       
                    lastBoxDelCount++;
                }
                if(lastBoxDelCount>lastBoxDelay)
                {
                    //no need to reset delay now
                    creditsAdded = true;
                }
            }
        }
    }

    public int getMapNum() {
        return mapNum;
    }

    public boolean noChat() {
        return !box.visible();
    }

    public boolean dialogueDone() {
        return dialogueDone;
    }

    private void addPlayer() {
        player = new Player(box); //for reference among classes
        addObject(player, player.getImage().getWidth(), getHeight()/2); 
    }

    private void addCounter() //will only be added at the beginning of the game.
    {        
        addObject(counter, 70, getHeight()-30);
    }

    private void addToggle()
    {
        addObject(toggle, getWidth()-15, 20);
    }

    private void addPortal() {        
        addObject(port, getWidth()-port.getImage().getWidth()/2-5, getHeight()/2);
    }

    private void addNPCs() {
        if (mapNum == 0) {
            NPC millie = new NPC("Millie", "Millie Cropped.png", "Millie Cropped Left.png", "left");
            addObject(millie, getWidth()-200, getHeight()/2);        
            NPC pythia = new NPC("Pythia", "Bestie Cropped.png", "Bestie Cropped Left.png", "right");
            addObject(pythia, getWidth()-250, getHeight()/2);
            dialogueDone = false;
        } else if (mapNum == 1) {
            NPC millie = new NPC("Millie", "Millie Cropped.png", "Millie Cropped Left.png", "left");
            addObject(millie, getWidth()-230, getHeight()/2 - 10);        
            NPC pythia = new NPC("Pythia", "Bestie Cropped.png", "Bestie Cropped Left.png", "right");
            addObject(pythia, getWidth()-280, getHeight()/2 - 30);
            NPC isaac = new NPC("Isaac", "Izzy Cropped.png", "Izzy Cropped Left.png", "left");
            addObject(isaac, getWidth()-250, getHeight()/2 - 50);

            dialogueDone = false;
        } else if (mapNum == 2) {
            NPC millie = new NPC("Millie", "Millie Cropped.png", "Millie Cropped Left.png", "right");
            addObject(millie, getWidth()-400, getHeight()/2 - 10);        
            NPC pythia = new NPC("Pythia", "Bestie Cropped.png", "Bestie Cropped Left.png", "left");
            addObject(pythia, getWidth()-200, getHeight()/2 - 30);
            NPC isaac = new NPC("Isaac", "Izzy Cropped.png", "Izzy Cropped Left.png", "left");
            addObject(isaac, getWidth()-350, getHeight()/2 - 50);
            Wall one = new Wall();
            addObject(one, getWidth()-330, getHeight()/2 - 50);
            NPC simon = new NPC("Simon", "Simon Cropped.png", "Simon Cropped Left.png", "right");
            addObject(simon, getWidth()-250, getHeight()/2 - 50);

            dialogueDone = false;
            
        }
    }

    private void addFlan() {        
        if (mapNum == 0) {
            Flan flan = new Flan();
            addObject(flan, getWidth()/2, getHeight()/2);
        }
    }

    private void addWalls() //will be called after every map change
    { //create one wall and then have that wall create all the others
        Wall one = new Wall();
        addObject(one, 0, 0);
        one.addBase();
        one.addOthers();
    }

    private void removeNPCs() //will be called after every map change. Removes all objects from the NPC class from the world
    {
        List<NPC> npcs = getObjects(NPC.class);
        removeObjects(npcs);
    }

    private void removeFlan()
    {
        List<Flan> flans = getObjects(Flan.class);
        removeObjects(flans);
    }

    private void removeWalls()
    {
        List<Wall> walls = getObjects(Wall.class);
        removeObjects(walls);
    }

    public void checkClear() //to be used for changeNext() in this class and will also be invoked in class Sox
    {
        List<Mob> mobs = getObjects(Mob.class);
        if (mobs.size() == 0) {
            mapIsClear = true;
        } else {
            mapIsClear = false;
        }
    }

    public boolean mapIsClear() {
        return mapIsClear;
    }

    public void changeNext() //to be invoked by the Portal class
    {
        checkClear();
        if(mapIsClear && mapNum<TOTALMAPS)
        {            
            mapNum++;
            updateMap();
        }
    }    

    public void updateMap() {
        removeNPCs();
        addNPCs();
        player.setLocation(player.getImage().getWidth(), getHeight()/2);
        if(mapNum==LAST) {
            setBackground(space);
            bgm.stop();
            removeObject(player);
            removeWalls();
            removeObject(port);
            removeObject(counter);
            mapIsClear = false;
            //Greenfoot.playSound("Always With Me.mp3");
            Spaceship ship = new Spaceship();
            addObject(ship, getWidth()/2, getHeight()/2);
        }
    }

    public void talk (String name) {
        if (dialogueDone) { return; }
        if (mapNum == 0) {
            if (name.equals("Millie") || name.equals("Pythia")) {
                box.chat("who's home?");
                dialogueDone = true;
            }
        } else if (mapNum == 1) {if (name.equals("Millie") || name.equals("Pythia")) {
                box.chat("drinks");
                dialogueDone = true;
            }
        } else if (mapNum == 2) {if (name.equals("Millie") || name.equals("Pythia") || name.equals("Isaac") || name.equals("Simon")) {
                box.chat("the disco");
                dialogueDone = true;
            }
        }
    }
}

