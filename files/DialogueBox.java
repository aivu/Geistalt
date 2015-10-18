import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.awt.Font;
import java.util.Calendar;
import java.util.ArrayDeque;
import java.util.HashMap;

/**
 * The dialogue box holds the dialogue of a speaker.
 * It shows the speaker's name and up to 3 lines of text per box.
 * 
 * Alina Vuong
 */
public class DialogueBox extends Actor
{
    private float nameSize = 20.0f; //the font size of the name
    private float textSize = 20.0f; //the font size of the dialogue text
    private float instrSize = 12.0f; //the font size of the "press enter" text

    private int width = 650;
    public int height = 200; //will be referenced by the NPC class.
    private GreenfootImage image = new GreenfootImage(width, height);

    private int boxDelay = 10;
    private int boxDelCount = 0; //otherwise pressing enter would stream through all the text
    private String sequence;
    private boolean choice = false;
    private boolean visible = true;
    private ArrayDeque<String> content;
    private HashMap<String, ArrayDeque> lines = new HashMap();
    private HashMap<String, String> options = new HashMap();

    public DialogueBox() {
        ArrayDeque<String> intro = new ArrayDeque();
        intro.add("Introduction");
        intro.add("Use the arrow keys to move.");
        intro.add("Walk to characters to see what they're saying.");
        intro.add("");
        intro.add("");
        intro.add("What's your best friend Pythia doing over");
        intro.add("there?");
        intro.add("");
        lines.put("intro", intro);
    }

    public void act() {
        boxDelCount++;
        checkPress();
    }

    public boolean add(String sequence, ArrayDeque<String> content) {
        if (content.size() % 4 == 0) {
            lines.put(sequence, content);
            return true;
        }
        return false;
    }

    public boolean add(String sequence, ArrayDeque<String> content, String preceding) {
        options.put(preceding, sequence);
        return add(sequence, content);
    }

    public boolean visible() {
        return visible;
    }

    public void chat(String seq) {
        Daumscape daum = (Daumscape) getWorld();
        sequence = seq;
        int mapNum = daum.getMapNum();
        content = lines.get(sequence);
        if (content == null) {
            System.out.println(seq + " not a defined sequence.");
            return;
        }
        if (content.isEmpty() && options.containsKey(sequence)) {
            sequence = options.get(sequence);
            content = lines.get(sequence);
            choice = true;
        }
        update(content);
    }

    private void update (ArrayDeque<String> content) {
        Daumscape daum = (Daumscape) getWorld();
        image.clear();
        if (content.isEmpty()) {
            visible = false;
            return;
        }
        visible = true;
        String name = content.poll();

        image.setColor(new Color(200, 70, 10, 128)); //outer rectangle
        image.fillRect(0, 0, width, height);
        image.setColor(new Color(0, 0, 0, 128)); //inner rectangle
        image.fillRect(5, 5, width-10, height-10);
        Font font = new java.awt.Font("Courier New", Font.PLAIN, 14);
        font = font.deriveFont(nameSize);
        image.setFont(font);
        image.setColor(new Color(150, 150, 150));
        image.drawString(name, 30, 40);
        image.setColor(Color.WHITE);
        image.drawString(content.poll(), 30, 80);
        image.drawString(content.poll(), 30, 105);
        image.drawString(content.poll(), 30, 130);

        font = font.deriveFont(instrSize);
        image.setFont(font);
        image.setColor(new Color(150,150,150));
        if (!choice) {
            image.drawString("Press enter to continue.", width-190, height-20);
        }

        setImage(image);
    }

    private void update(ArrayDeque content, String seq) {
    }

    private void checkPress() {
        if(boxDelCount > boxDelay)
        {
            boxDelCount = 0;
            if (choice) {
                int option = -1;
                while (option == -1) {
                    if (Greenfoot.isKeyDown("a")) {
                        option = 0;
                    } else if (Greenfoot.isKeyDown("b")) {
                        option = 1;
                    } else if (Greenfoot.isKeyDown("c")) {
                        option = 2;
                    }
                }
                for (int i = 0; i < option*4; i++) {
                    content.poll();
                }
                choice = false;
                update(content);
                content.clear();
            }
            if (!choice && Greenfoot.isKeyDown("enter")) {
                chat(sequence);
            }
        }
    }
}
