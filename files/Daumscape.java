import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @Alina Vuong 
 * 5.9.13
 */
public class Daumscape extends World
{
    //fields
    public Player player = new Player(); //for reference among classes
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

    public GreenfootSound bgm = new GreenfootSound("Liquid Mountains.mp3");
    public boolean bgmOn = true;
    public boolean soundOn = true;
    public Daumscape()
    {    
        super(750, 550, 1); 

        setPaintOrder(Intro.class, DialogueBox.class, HPCounter.class, Sox.class, Player.class, Penguin.class, Laser.class, Flan.class, Portal.class, Toggle.class, Wall.class);

        //addObject(new Intro(), getWidth()/2, getHeight()/2);
        addPlayer();
        addCounter();
        addToggle();
        addPortal();
        addWalls();

        counts[0] = "005";
        counts[1] = "001";
        updateMap(); //this method must be called AFTER the mapNum and counts are set.
        //bgm.play();
        addObject(box, getWidth()/2, getHeight()-box.height+50);
        box.chat("intro");
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
        return box.visible();
    }

    private void addPlayer() {
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

    private void addPortal() //will only be added at the beginning of the game.
    {        
        addObject(port, getWidth()-port.getImage().getWidth()/2-5, getHeight()/2);
    }
    
    private void addNPCs() //will be called after every map change
    {
        NPC npc = new NPC();
        addObject(npc, Greenfoot.getRandomNumber(getWidth()-200)+100, Greenfoot.getRandomNumber(getHeight()-200)+100);        
    }
    private void addFlan() //fix coordinates
    {        
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

    public void updateMap()
    {
        if(mapNum==TOTALMAPS)
        {
            setBackground(space);
            bgm.stop();
            removeObject(player);
            removeNPCs();
            removeWalls();
            removeObject(port);
            removeObject(counter);
            mapIsClear = false;
            player.setLocation(player.getImage().getWidth(), getHeight()/2);
            //Greenfoot.playSound("Always With Me.mp3");
            Spaceship ship = new Spaceship();
            addObject(ship, getWidth()/2, getHeight()/2);
        }   
    }
    public void talk (String name) {
        if (mapNum == 0) {
            if (name.equals("Researcher")) {
                box.chat("who's home?");
            }
        }
    }
}

