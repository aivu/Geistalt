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

    public GreenfootSound bgm = new GreenfootSound("Clair De Lune.mp3");
    public boolean bgmOn = true;
    public boolean soundOn = true;
    private boolean dialogueDone = false;
    public Daumscape()
    {    
        super(750, 550, 1); 

        setPaintOrder(Intro.class, DialogueBox.class, HPCounter.class, Player.class, NPC.class, Laser.class, Flan.class, Portal.class, Toggle.class, Wall.class);

        //addObject(new Intro(), getWidth()/2, getHeight()/2);
        addPlayer();
        //addCounter();
        addToggle();
        addPortal();
        addWalls();

        updateMap();
        //bgm.play();
        addObject(box, getWidth()/2, getHeight()-box.height+50);
        addDialogue();
        box.chat("intro");

    }

    public void act() {        
        if (mapNum != LAST) {
            if (bgmOn) {
                if (!bgm.isPlaying()) {
                    bgm.play();
                }
            } else if (!bgmOn) {
                if (bgm.isPlaying()) {
                    bgm.stop();
                }
            }
        }
        if (mapNum==LAST) {
            if (!lastAdded) {
                lastBoxDelCount++;    
                if (lastBoxDelCount>lastBoxDelay)
                {
                    lastBoxDelCount = 0;
                    lastAdded = true;
                }
            }
            if (lastAdded && !creditsAdded) {
                if (box.visible()) //Prevents the credits from covering Sox's last line after only a small amount of time.
                {       
                    lastBoxDelCount++;
                }
                if (lastBoxDelCount>lastBoxDelay)
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

    private void addFlan() {        
        if (mapNum == 0) {
            Flan flan = new Flan();
            addObject(flan, getWidth()/2, getHeight()/2);
        }
    }

    private void addWalls() {
        Wall one = new Wall();
        addObject(one, 0, 0);
        one.addBase();
        one.addOthers();
    }

    private void removeNPCs() {
        List<NPC> npcs = getObjects(NPC.class);
        removeObjects(npcs);
    }

    private void removeFlan() {
        List<Flan> flans = getObjects(Flan.class);
        removeObjects(flans);
    }

    private void removeWalls() {
        List<Wall> walls = getObjects(Wall.class);
        removeObjects(walls);
    }

    public void checkClear() {
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

    public void changeNext() {
        checkClear();
        if (mapIsClear && mapNum < TOTALMAPS) {            
            mapNum++;
            updateMap();
        }
    }    

    public void updateMap() {
        removeNPCs();
        addNPCs();
        player.setLocation(player.getImage().getWidth(), getHeight()/2);
        dialogueDone = false;
        if (mapNum==LAST) {
            setBackground(space);
            //bgm.stop();
            removeObject(player);
            removeWalls();
            removeObject(port);
            removeObject(counter);
            mapIsClear = false;
            BackgroundObject ship = new BackgroundObject("spaceship1.png", "spaceship2.png");
            addObject(ship, getWidth()/2, getHeight()/2);
            box.chat("last");
        }
    }

    private void addNPCs() {
        if (mapNum == 0) {
            NPC millie = new NPC("Millie", "Millie Cropped.png", "Millie Cropped Left.png", "left");
            addObject(millie, getWidth()/2 + 25, getHeight()/2);        
            NPC pythia = new NPC("Pythia", "Bestie Cropped.png", "Bestie Cropped Left.png", "right");
            addObject(pythia, getWidth()/2 - 20, getHeight()/2);
            NPC dog = new NPC("Dog", "Dog Cropped.png", "Dog Cropped Left.png", "left");
            addObject(dog, 200, 200);
           
            dialogueDone = false;
        } else if (mapNum == 1) {
            NPC millie = new NPC("Millie", "Millie Cropped.png", "Millie Cropped Left.png", "right");
            addObject(millie, getWidth()/2 - 60, getHeight()/2 + 10);
            NPC pythia = new NPC("Pythia", "Bestie Cropped.png", "Bestie Cropped Left.png", "left");
            addObject(pythia, getWidth()/2, getHeight()/2);
            NPC isaac = new NPC("Isaac", "Izzy Cropped.png", "Izzy Cropped Left.png", "right");
            addObject(isaac, getWidth()/2 - 40, getHeight()/2 - 30);

            dialogueDone = false;
        } else if (mapNum == 2) {
            NPC millie = new NPC("Millie", "Millie Cropped.png", "Millie Cropped Left.png", "right");
            addObject(millie, getWidth()/2 - 100, getHeight()/2 - 50);        
            NPC isaac = new NPC("Isaac", "Izzy Cropped.png", "Izzy Cropped Left.png", "left");
            addObject(isaac, getWidth()/2 - 70, getHeight()/2 - 30);
            NPC pythia = new NPC("Pythia", "Bestie Cropped.png", "Bestie Cropped Left.png", "right");
            addObject(pythia, getWidth()/2 + 50, getHeight()/2 - 10);
            NPC simon = new NPC("Simon", "Simon Cropped.png", "Simon Cropped Left.png", "left");
            addObject(simon, getWidth()/2 + 90, getHeight()/2 - 20);
            NPC dancer = new NPC("Dancer", "Dancer Cropped.png", "Dancer Cropped Left.png", "left");
            addObject(dancer, getWidth()/2 + 180, getHeight()/2 - 150);
            
            dialogueDone = false;
        } else if (mapNum == 3) {
            NPC millie = new NPC("Millie", "Millie Cropped.png", "Millie Cropped Left.png", "right");
            addObject(millie, getWidth()/2 - 70, getHeight()/2 + 5);
            NPC pythia = new NPC("Pythia", "Bestie Cropped.png", "Bestie Cropped Left.png", "left");
            addObject(pythia, getWidth()/2, getHeight()/2);
            NPC isaac = new NPC("Isaac", "Izzy Cropped.png", "Izzy Cropped Left.png", "right");
            addObject(isaac, getWidth()/2 - 40, getHeight()/2 - 25);

            dialogueDone = false;
        }
        
    }

    public void talk (String name) {
        if (dialogueDone) { return; }
        if (mapNum == 0) {
            if (name.equals("Dog")) {
                box.chat("dog");
            }
            if (name.equals("Millie") || name.equals("Pythia")) {
                box.chat("who's home?");
                dialogueDone = true;
            }
        } else if (mapNum == 1) {
            if (name.equals("Millie") || name.equals("Pythia") || name.equals("Isaac")) {
                box.chat("drinks");
                dialogueDone = true;
            }
        } else if (mapNum == 2) {if (name.equals("Millie") || name.equals("Pythia") || name.equals("Isaac") || name.equals("Simon")) {
                box.chat("the disco");
                dialogueDone = true;
            }
        }   else if (mapNum == 3) {
            if (name.equals("Millie") || name.equals("Pythia") || name.equals("Isaac")) {
                box.chat("cya");
                dialogueDone = true;
            }
        }
    }

    private void addDialogue() {
        ArrayDeque<String> intro = new ArrayDeque();
        intro.add("Geistalt!");
        intro.add("Use the arrow keys to move.");
        intro.add("Walk to characters to see what they're saying.");
        intro.add("");
        intro.add("?");
        intro.add("What's your best friend Pythia doing over");
        intro.add("there?");
        intro.add("");
        box.add("intro", intro);
        
        ArrayDeque<String> dog = new ArrayDeque();
        dog.add("Dog");
        dog.add("Ruf!");
        dog.add("");
        dog.add("");
        
        dog.add("");
        dog.add("The dog whines for its owner.");
        dog.add("");
        dog.add("");
        box.add("dog", dog);

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
        seq2.add("Would be a bit neater without Damon's");
        seq2.add("messiness huh? Haha.");
        seq2.add("");
        seq2.add("Pythia");
        seq2.add("");
        seq2.add("...");
        seq2.add("");
        seq2.add("Millie");
        seq2.add("Oh. Well.");
        seq2.add("Maybe some more drinks?");
        seq2.add("");
        seq2.add("Damon");
        seq2.add("What the heck, Millie.");
        seq2.add("...");
        seq2.add("Anyways. What should Isaac drink?");
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
        chooseDrink.add("Mm!");
        chooseDrink.add("");
        chooseDrink.add("Pepsi-Cola");
        chooseDrink.add("*burp*");
        chooseDrink.add("Hehe.");
        chooseDrink.add("");
        box.add("chooseDrink", chooseDrink, "drinks");

        ArrayDeque<String> seq3 = new ArrayDeque();
        seq3.add("Millie");
        seq3.add("Woo!");
        seq3.add("I love disco, honestly.");
        seq3.add("");
        seq3.add("Isaac");
        seq3.add("And I love you!");
        seq3.add("");
        seq3.add("");
        seq3.add("Pythia");
        seq3.add("Sigh...");
        seq3.add("");
        seq3.add("");
        seq3.add("Simon");
        seq3.add("Not dancing with your friends? How about");
        seq3.add("with that boy over there? Might've been one of");
        seq3.add("your friend's dance partners at one time.");
        seq3.add("Pythia");
        seq3.add("Oh... I guess.");
        seq3.add("");
        seq3.add("");
        box.add("the disco", seq3);

        ArrayDeque<String> chooseDance = new ArrayDeque();
        chooseDance.add("Damon. What does she want to do?");
        chooseDance.add("(A) Go up to him to dance.");
        chooseDance.add("(B) Stay next to your friend Simon.");
        chooseDance.add("(C) Give him some dance tips.");
        chooseDance.add("Go up to him and dance.");
        chooseDance.add("You go up to him and you both dance.");
        chooseDance.add("But, even though you're both smiling,");
        chooseDance.add("you feel a bit quiet inside.");
        chooseDance.add("Stay at the counter.");
        chooseDance.add("You stand quietly in response to Simon's");
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
        box.add("chooseDance", chooseDance, "the disco");

        ArrayDeque<String> seq4 = new ArrayDeque();
        seq4.add("Isaac");
        seq4.add("You know with all that's said and done, life");
        seq4.add("isn't really too bad. Right?");
        seq4.add("");

        seq4.add("Pythia");
        seq4.add("Sure. But, you know.");
        seq4.add("");
        seq4.add("");

        seq4.add("Pythia");
        seq4.add("I'm not going to be happy right away. I");
        seq4.add("won't. And I don't feel anything right now.");
        seq4.add("");

        seq4.add("Millie");
        seq4.add("...");
        seq4.add("");
        seq4.add("Do you have someone you can ask for help?");

        seq4.add("Pythia");
        seq4.add("My problems aren't going to disappear overnight.");
        seq4.add("I need time to mourn. Not someone to 'fix' me.");
        seq4.add("I miss my best friend.");

        seq4.add("Damon");
        seq4.add("Good, sweet, beautiful Pythia!!");
        seq4.add("Jesus! If only I hadn't left this world so soon.");
        seq4.add("");
        
        seq4.add("Pythia");
        seq4.add("Alright. I'll see you later.");
        seq4.add("");
        seq4.add("");
        box.add("cya", seq4);

        ArrayDeque<String> last = new ArrayDeque();
        last.add("Tomorrow");
        last.add("Here's what happened next.");
        last.add("Pythia, my dear sweet best friend, went home.");
        last.add("She was down a lot of times. Friends helped out.");

        last.add("Tomorrow");
        last.add("That was what was needed. No pity, no shame.");
        last.add("Sympathy, only slightly important compared to");
        last.add("action.");
        
        last.add("Tomorrow");
        last.add("Friends listened, and made sure she had the basic");
        last.add("necessities while she mourned. And they kept Damon");
        last.add("in their hearts, carried him in their pockets.");

        last.add("Damon");
        last.add("Bye Pythia.");
        last.add("Anyways. Time to check out the rest of the ");
        last.add("world while I'm here.");
        
        last.add("Acknowledgments");
        last.add("Thanks to the Queer Games Workshop 2015");
        last.add("Organizers D Pozo, Chris Goetz, and the guest ");
        last.add("speakers for inspiration and feedback!");
        box.add("last", last);
        
        last.add("Credits");
        last.add("Storyline, Character Design: William Watkins");
        last.add("Music Compilation, Story Editing: Grace Gipson");
        last.add("Programming, Story Editing: Alina Vuong");
        
        last.add("Credits");
        last.add("Character Sprites, Art Assets: Leo Lin");
        last.add("");
        last.add("");
        
        last.add("Thanks for playing!");
        last.add("");
        last.add("");
        last.add("See you again soon.");
    }
}

