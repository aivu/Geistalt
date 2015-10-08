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
    public Jim jim; //for reference among classes
    private Portal port;
    private HPCounter counter;
    public Sox sox;
    private Collaborator larimen, calico, gunther; //for reference among this class's methods
    private Toggle toggle;

    private boolean mapIsClear;
    public int mapNum; //for reference in classes NPC, DialogueBox, Sox, and Jim
    public int totalMaps; //for endgame sequence, and invocation in Portal class

    public int bossCount; //will track the bosses in the map
    public int internCount; //will track the interns remaining in the map
    public int npcCount; //will track remaining NPC interactions the player needs in order to move on
    //important to remember: number in array must align with number actually added to world

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

        setPaintOrder(Intro.class, DialogueBox.class, HPCounter.class, Sox.class, Jim.class, Penguin.class, Laser.class, Flan.class, Portal.class, Toggle.class, Wall.class);

        //addObject(new Intro(), getWidth()/2, getHeight()/2);
        addJim();
        addCounter();
        addToggle();
        addPortal();
        addWalls();

        mapNum = 0;      
        totalMaps = counts.length-1; //for the endgame sequence
        counts[0] = "005"; //Ask Around
        counts[1] = "001"; //Regimund's Warning--save point
        counts[2] = "040"; //First Foray
        counts[3] = "141"; //2nd Wave With Larimen
        counts[4] = "001"; //Galea's Word--save point
        counts[5] = "040"; //Cell on the 6th Floor
        counts[6] = "050";
        counts[7] = "050";
        counts[8] = "060";
        counts[9] = "060";
        counts[10] = "071";//Sox
        counts[11] = "001"; 
        counts[12] = "040"; 
        counts[13] = "151";//Calico's Obstruction
        counts[14] = "040"; //To the Basement
        counts[15] = "101"; //Gunther
        counts[16] = "000";
        counts[17] = "000"; //Escape on a Starship
        bossCount = Integer.parseInt(counts[mapNum].substring(0,1)); //makes for a lot of hardcoding but I wanted to use this method
        internCount = Integer.parseInt(counts[mapNum].substring(1,2)); //honestly you could just use List<type> objects = getObjects(type) to retrieve the mob counts
        npcCount = Integer.parseInt(counts[mapNum].substring(2,3)); //not the NPC conversation counts though.
        setCounts(); //this method must be called after mapNum and the counts array are set

        changeMap(); //this method must be called AFTER the mapNum and counts are set.
        //bgm.play();
    }

    public void act()
    {        
        if(mapNum==0 && !boxAdded)
        {
            List<Intro> intros = getObjects(Intro.class);
            if(intros.size()<1)
            {
                boxAdded = true;
                addBox();
            }
        }
        if(mapNum!=17)
        {
            if(bgmOn)
            {
                if(!bgm.isPlaying())
                {
                    bgm.play();
                }
            }
            else if(!bgmOn)
            {
                if(bgm.isPlaying())
                {
                    bgm.stop();
                }
            }
        }
        if(mapNum==17)
        {
            if(!lastAdded)
            {
                lastBoxDelCount++;    
                if(lastBoxDelCount>lastBoxDelay)
                {
                    lastBoxDelCount = 0;
                    lastAdded = true;
                    DialogueBox box = new DialogueBox("",0);
                    addObject(box, getWidth()/2, getHeight()-box.height+50);
                }
            }
            if(lastAdded && !creditsAdded)
            {
                List<DialogueBox> boxes = getObjects(DialogueBox.class);
                if(boxes.size()<1) //Prevents the credits from covering Sox's last line after only a small amount of time.
                {       
                    lastBoxDelCount++;
                }
                if(lastBoxDelCount>lastBoxDelay)
                {
                    //no need to reset delay now
                    creditsAdded = true;
                    DialogueBox box = new DialogueBox("Credits",0);
                    addObject(box, getWidth()/2, getHeight()-box.height+50);
                }
            }
        }
    }

    private void addJim()
    //Jim will only be added once, at the beginning of the game; 
    //thereafter map changes will simply move him back to his initial position.
    {
        jim = new Jim();
        addObject(jim, jim.getImage().getWidth(), getHeight()/2); 
    }

    private void addCounter() //will only be added at the beginning of the game.
    {        
        counter = new HPCounter(); //add HPCounter after Jim because Jim needs to be initialized for the health field to exist
        addObject(counter, 70, getHeight()-30);
    }

    private void addToggle()
    {
        toggle = new Toggle();
        addObject(toggle, getWidth()-15, 20);
    }

    private void addPortal() //will only be added at the beginning of the game.
    {        
        port = new Portal();
        addObject(port, getWidth()-port.getImage().getWidth()/2-5, getHeight()/2);
    }

    private void setCounts() //will be invoked after every map change
    {
        bossCount = Integer.parseInt(counts[mapNum].substring(0,1)); //this isn't "mapNum-1" b.c. mapNum will start at 0 for simplicity
        internCount = Integer.parseInt(counts[mapNum].substring(1,2)); //same as above
        npcCount = Integer.parseInt(counts[mapNum].substring(2,3)); 
    }

    private void addBox() //will only be added at the beginning of the game; explains keyboard controls
    {
        DialogueBox box = new DialogueBox("Instructions:", 0);
        addObject(box, getWidth()/2, getHeight()-box.height+50);        
    }    

    private void addInterns() //will be called after every map change
    {
        for(int i=0; i<internCount; i++)
        {
            Intern intern = new Intern();
            addObject(intern, Greenfoot.getRandomNumber(getWidth()-300)+200, Greenfoot.getRandomNumber(getHeight()-100)+50);
        }     
    }

    private void addNPCs() //will be called after every map change
    {
        if(mapNum==0)
        {
            for(int i=0; i<npcCount-1; i++) //to account for Jim's dialogue in first map
            {
                NPC npc = new NPC();
                addObject(npc, Greenfoot.getRandomNumber(getWidth()-200)+100, Greenfoot.getRandomNumber(getHeight()-200)+100);
            }
        }
        if(mapNum==1)
        {
            NPC regimund = new NPC("Regimund");
            addObject(regimund, Greenfoot.getRandomNumber(getWidth()-200)+100, Greenfoot.getRandomNumber(getHeight()-200)+100);
        }
        if(mapNum==4)
        {
            NPC galea = new NPC("Galea");
            addObject(galea, Greenfoot.getRandomNumber(getWidth()-200)+100, Greenfoot.getRandomNumber(getHeight()-200)+100);
        }
        if(mapNum==10)
        {
            sox = new Sox();
            addObject(sox, 600, 150);
        }
    }

    private void addBosses()  //will be called after every map change
    {
        if(mapNum == 3)
        {
            larimen = new Collaborator(2, 100, 20, 150);
            addObject(larimen, 500, getHeight()/2);            
        }
        if(mapNum == 13)
        {
            calico = new Collaborator(3, 150, 30, 100);
            addObject(calico, 500, getHeight()/2);        
        }
        if(mapNum == 15)
        {
            gunther = new Collaborator(4, 200, 35, 75);
            addObject(gunther, 500, getHeight()/2);        
        }
    }

    public void addBossNPCs(int x, int y)  //will be invoked by the Collaborator class
    {
        if(mapNum == 3)
        {
            NPC larimen = new NPC("Larimen");
            addObject(larimen, x, y);
        }
        if(mapNum == 13)
        {
            NPC calico = new NPC("Calico");
            addObject(calico, x, y);
        }
        if(mapNum == 15)
        {
            NPC gunther = new NPC("Gunther");
            addObject(gunther, x, y);
        }
    }

    private void addFlan() //fix coordinates
    {        
        if(mapNum==3 || mapNum==11 || mapNum==14)
        {
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
        if((internCount+bossCount)==0 && npcCount<=0)
        {
            mapIsClear = true;
        }
        else
        {
            mapIsClear = false;
        }
    }

    public void changeNext() //to be invoked by the Portal class
    {
        checkClear();
        if(mapIsClear && mapNum<totalMaps)
        {            
            mapNum++;
            changeMap();
        }
    }    

    public void changeMap()
    {
        if(mapNum<totalMaps)
        {
            removeNPCs();
            //             removeWalls(); //no differing setups.
            removeFlan();
            mapIsClear = false;
            jim.realized = false; //used for various bits of plot dialogue
            jim.realizeDelCount = 0; //same as above
            setCounts();
            jim.setLocation(jim.getImage().getWidth(), getHeight()/2);
            port.setImage(port.closed);

            //Make sure all mapNum-sensitive methods come AFTER the mapNum change!!
            if(mapNum>10)
            { 
                List<Sox> soxes = getObjects(Sox.class);
                if(soxes.size()<1) //for testing purposes
                {
                    sox = new Sox(); //prevents NullPointerException if Sox isn't in the world when testing
                    addObject(sox, sox.getImage().getWidth()/2, getHeight()/2);
                }
                else
                {
                    sox.setLocation(sox.getImage().getWidth()/2, getHeight()/2);
                }
                sox.talkedTo = false;
            }
            if(jim.health<0) //for revival purposes
            {
                jim.health = 0;
            }        
            //addWalls();
            addInterns();
            addNPCs();
            addBosses();
            addFlan();
        }
        if(mapNum==totalMaps)
        {
            setBackground(space);
            bgm.stop();

            List<Sox> soxes = getObjects(Sox.class); //can't just removeObject(sox) for some reason
            removeObjects(soxes);
            removeObject(jim);

            removeNPCs();
            removeWalls();
            removeObject(port);
            removeObject(counter);

            mapIsClear = false;
            setCounts();
            jim.setLocation(jim.getImage().getWidth(), getHeight()/2);

            Greenfoot.playSound("Always With Me.mp3");

            Spaceship ship = new Spaceship();
            addObject(ship, getWidth()/2, getHeight()/2);
        }
    }
}

