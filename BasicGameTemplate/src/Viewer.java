import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
   
   (MIT LICENSE ) e.g do what you want with this :-) 
 
 * Credits: Kelly Charles (2020)
 */ 
public class Viewer extends JPanel {
	private long currentAnimationTime= 0; 
	
	Model gameworld; 
	private Image playerSprites;
	private Image background;
	private HashMap<String, Image> objectSprites = new HashMap<>();
	private HashMap<String, Image> enemySprites = new HashMap<>();
	 
	public Viewer(Model World) {
		this.gameworld=World;
		// I don't know why we loaded the textures every frame, its a huge hit to performance.
		File playerTexture = new File(gameworld.getPlayer().getTexture()); 
		File bgTexture = new File(gameworld.getBackground());
		try {
			playerSprites = ImageIO.read(playerTexture); 
			background = ImageIO.read(bgTexture);

		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public Viewer(LayoutManager layout) {
		super(layout);
	}

	public Viewer(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public Viewer(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public void updateview() {
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		currentAnimationTime++; // runs animation time step 
		
		//Draw background 
		drawBackground(g);
		  
		//Draw Bullets 
		// change back 
		gameworld.getBullets().forEach((temp) -> 
		{ 
			drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);	 
		}); 
		
		//Draw Enemies   
		gameworld.getEnemies().forEach((temp) -> 
		{
			drawEnemies(temp,g);	 
		 
	    });
		
		//Draw Platforms
		gameworld.getPlatforms().forEach((temp) ->
		{
			drawPlatforms(temp, g);
		});

		gameworld.getDecorations().forEach((temp) ->
		{ 
			drawPlatforms(temp, g);
		});

		//Draw player
		drawPlayer(gameworld.getPlayer() ,g);
	}
	
	private void drawEnemies(Enemy e, Graphics g) {
		// Load sprites and keep them in memory so we arent constantly rereading the file and image.
		if (!enemySprites.containsKey(e.getTexture())) {
			File eSprite = new File(e.getTexture());
			try {
				enemySprites.put(e.getTexture(), ImageIO.read(eSprite));
			} catch (IOException err) {
				err.printStackTrace();
			} 
		}

		// Now Draw
		int currentPositionInAnimation= ((int) (currentAnimationTime%4 )*32); //slows down animation so every 10 frames we get another frame so every 100ms 
		int x = (int) e.getCentre().getX();
		int y = (int) e.getCentre().getY();
		g.drawImage(enemySprites.get(e.getTexture()), x, y, x+e.getWidth(), y+e.getHeight(), currentPositionInAnimation  , 0, currentPositionInAnimation+31, 32, null); 


	}

	private void drawBackground(Graphics g)
	{
		g.drawImage(background, 0,0, 1000, 1000, 0 , 0, 180, 180, null); 
	}
	
	private void drawBullet(int x, int y, int width, int height, String texture,Graphics g)
	{
		// TODO Move to constructor after adding new sprites
		File TextureToLoad = new File(texture);  
		try {
			Image myImage = ImageIO.read(TextureToLoad); 
			//64 by 128 
			 g.drawImage(myImage, x,y, x+width, y+height, 0 , 0, 63, 127, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private void drawPlayer(Player p, Graphics g) { 
		if (currentAnimationTime % 4 == 0) p.stepAnimation();
		int xAnim = p.getHorizontalFrame() * 288 + 117; 
		int yAnim = p.getVerticalFrame() * 128 + 77;
		int x = (int) p.getCentre().getX();
		int y = (int) p.getCentre().getY();
		g.drawImage(playerSprites, x, y, x+p.getWidth(), y+p.getHeight(), xAnim, yAnim, xAnim+60, yAnim+50, null); 
	}

	private void drawPlatforms(PhysicalGameObject p, Graphics g) {
		// Load sprites and keep them in memory so we arent constantly rereading the file and image.
		if (!objectSprites.containsKey(p.getTexture())) {
			File TextureToLoad = new File(p.getTexture());  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
			try {
				objectSprites.put(p.getTexture(), ImageIO.read(TextureToLoad));
			} catch (IOException e) {e.printStackTrace();}
		}
		// Now Draw
		int x = (int) p.getCentre().getX();
		int y = (int) p.getCentre().getY();
		g.drawImage(objectSprites.get(p.getTexture()), x,y, x+p.getWidth(), y+p.getHeight(), 0 , 0, p.spriteWidth, p.spriteHeight, null);	
	}
}


/*
 * 
 * 
 *              VIEWER HMD into the world                                                             
                                                                                
                                      .                                         
                                         .                                      
                                             .  ..                              
                               .........~++++.. .  .                            
                 .   . ....,++??+++?+??+++?++?7ZZ7..   .                        
         .   . . .+?+???++++???D7I????Z8Z8N8MD7I?=+O$..                         
      .. ........ZOZZ$7ZZNZZDNODDOMMMMND8$$77I??I?+?+=O .     .                 
      .. ...7$OZZ?788DDNDDDDD8ZZ7$$$7I7III7??I?????+++=+~.                      
       ...8OZII?III7II77777I$I7II???7I??+?I?I?+?+IDNN8??++=...                  
     ....OOIIIII????II?I??II?I????I?????=?+Z88O77ZZO8888OO?++,......            
      ..OZI7III??II??I??I?7ODM8NN8O8OZO8DDDDDDDDD8DDDDDDDDNNNOZ= ......   ..    
     ..OZI?II7I?????+????+IIO8O8DDDDD8DNMMNNNNNDDNNDDDNDDNNNNNNDD$,.........    
      ,ZII77II?III??????DO8DDD8DNNNNNDDMDDDDDNNDDDNNNDNNNNDNNNNDDNDD+.......   .
      7Z??II7??II??I??IOMDDNMNNNNNDDDDDMDDDDNDDNNNNNDNNNNDNNDMNNNNNDDD,......   
 .  ..IZ??IIIII777?I?8NNNNNNNNNDDDDDDDDNDDDDDNNMMMDNDMMNNDNNDMNNNNNNDDDD.....   
      .$???I7IIIIIIINNNNNNNNNNNDDNDDDDDD8DDDDNM888888888DNNNNNNDNNNNNNDDO.....  
       $+??IIII?II?NNNNNMMMMMDN8DNNNDDDDZDDNN?D88I==INNDDDNNDNMNNMNNNNND8:..... 
   ....$+??III??I+NNNNNMMM88D88D88888DDDZDDMND88==+=NNNNMDDNNNNNNMMNNNNND8......
.......8=+????III8NNNNMMMDD8I=~+ONN8D8NDODNMN8DNDNNNNNNNM8DNNNNNNMNNNNDDD8..... 
. ......O=??IIIIIMNNNMMMDDD?+=?ONNNN888NMDDM88MNNNNNNNNNMDDNNNMNNNMMNDNND8......
........,+++???IINNNNNMMDDMDNMNDNMNNM8ONMDDM88NNNNNN+==ND8NNNDMNMNNNNNDDD8......
......,,,:++??I?ONNNNNMDDDMNNNNNNNNMM88NMDDNN88MNDN==~MD8DNNNNNMNMNNNDND8O......
....,,,,:::+??IIONNNNNNNDDMNNNNNO+?MN88DN8DDD888DNMMM888DNDNNNNMMMNNDDDD8,.... .
...,,,,::::~+?+?NNNNNNNMD8DNNN++++MNO8D88NNMODD8O88888DDDDDDNNMMMNNNDDD8........
..,,,,:::~~~=+??MNNNNNNNND88MNMMMD888NNNNNNNMODDDDDDDDND8DDDNNNNNNDDD8,.........
..,,,,:::~~~=++?NMNNNNNNND8888888O8DNNNNNNMMMNDDDDDDNMMNDDDOO+~~::,,,.......... 
..,,,:::~~~~==+?NNNDDNDNDDNDDDDDDDDNNND88OOZZ$8DDMNDZNZDZ7I?++~::,,,............
..,,,::::~~~~==7DDNNDDD8DDDDDDDD8DD888OOOZZ$$$7777OOZZZ$7I?++=~~:,,,.........   
..,,,,::::~~~~=+8NNNNNDDDMMMNNNNNDOOOOZZZ$$$77777777777II?++==~::,,,......  . ..
...,,,,::::~~~~=I8DNNN8DDNZOM$ZDOOZZZZ$$$7777IIIIIIIII???++==~~::,,........  .  
....,,,,:::::~~~~+=++?I$$ZZOZZZZZ$$$$$777IIII?????????+++==~~:::,,,...... ..    
.....,,,,:::::~~~~~==+?II777$$$$77777IIII????+++++++=====~~~:::,,,........      
......,,,,,:::::~~~~==++??IIIIIIIII?????++++=======~~~~~~:::,,,,,,.......       
.......,,,,,,,::::~~~~==+++???????+++++=====~~~~~~::::::::,,,,,..........       
.........,,,,,,,,::::~~~======+======~~~~~~:::::::::,,,,,,,,............        
  .........,.,,,,,,,,::::~~~~~~~~~~:::::::::,,,,,,,,,,,...............          
   ..........,..,,,,,,,,,,::::::::::,,,,,,,,,.,....................             
     .................,,,,,,,,,,,,,,,,.......................                   
       .................................................                        
           ....................................                                 
               ....................   .                                         
                                                                                
                                                                                
                                                                 GlassGiant.com
                                                                 */
