import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A wall.
 * 
 * Alina Vuong
 * 2.25.13
 */
public class Wall extends Actor
{
    private boolean added;
    public void act() 
    {
        checkImpact();
    }    

    public void addBase() //adding walls to the sides of the game screen
    {
        Daumscape daum = (Daumscape) getWorld();
        int mapNum = daum.getMapNum();
        if (mapNum < 17) {
            for(int i=0; i<26; i++) //top and bottom
            {
                Wall botWall = new Wall();
                daum.addObject(botWall, i*botWall.getImage().getWidth(), daum.getHeight()-botWall.getImage().getHeight()/2);

                Wall topWall = new Wall();
                daum.addObject(topWall, i*topWall.getImage().getWidth(), topWall.getImage().getHeight()/2);
            }
            for(int i=0; i<16; i++) //upper sides
            {                
                Wall rightWall = new Wall();
                daum.addObject(rightWall, daum.getWidth()-rightWall.getImage().getWidth()/2, i*rightWall.getImage().getHeight());    

                Wall leftWall = new Wall();
                daum.addObject(leftWall, leftWall.getImage().getWidth()/2, i*leftWall.getImage().getHeight());            
            }
            for(int i=0; i<16; i++) //lower sides
            {                
                Wall rightWall = new Wall();
                daum.addObject(rightWall, daum.getWidth()-rightWall.getImage().getWidth()/2, daum.getHeight()-i*rightWall.getImage().getHeight());    

                Wall leftWall = new Wall();
                daum.addObject(leftWall, leftWall.getImage().getWidth()/2, daum.getHeight()-i*leftWall.getImage().getHeight());            
            }
        }
    }

    public void addOthers() //miscellaneous arrangements of walls
    {
        Daumscape daum = (Daumscape) getWorld();
        //         if(daum.mapNum==3)
        //         {
        //             for(int i=0; i<5; i++)
        //             {
        //                 Wall wall = new Wall();
        //                 daum.addObject(wall, i*getImage().getWidth()+getImage().getWidth(), 200);
        //             }

    }

    private void checkImpact()
    {
        Daumscape daum = (Daumscape) getWorld();
        Player player = (Player) getOneIntersectingObject(Player.class);
        if(player!=null)
        {
            if(Math.abs(getX()-daum.player.getX()) > Math.abs(getY()-daum.player.getY()))
            {
                if(getX()>daum.player.getX())
                {
                    daum.player.setLocation(daum.player.getX()-1, daum.player.getY());
                }
                else if(getX()<daum.player.getX())
                {
                    daum.player.setLocation(daum.player.getX()+1, daum.player.getY());
                }
            }
            else 
            {
                if(getY()>daum.player.getY())
                {
                    daum.player.setLocation(daum.player.getX(), daum.player.getY()-1);
                }
                else if(getY()<daum.player.getY())
                {
                    daum.player.setLocation(daum.player.getX(), daum.player.getY()+1);
                }
            }
        }
    }
}
