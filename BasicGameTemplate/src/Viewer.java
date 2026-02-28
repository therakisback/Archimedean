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
	private final int ANIMATION_STEP_RATE = 6;
	private long currentAnimationTime= 0; 
	
	Model gameworld; 
	private Image blank;
	private Image playerSprites;
	private Image playerBullet;
	private Image background;
	private HashMap<String, Image> objectSprites = new HashMap<>();
	private HashMap<String, Image> enemySprites = new HashMap<>();
	 
	public Viewer(Model World) {
		this.gameworld=World;
		// I don't know why we loaded the textures every frame, its a huge hit to performance.
		File blankTexture = new File("res/blankSprite.png");
		File playerTexture = new File(gameworld.getPlayer().getTexture()); 
		File pBulTexture = new File("res/player/Moving.png");
		File bgTexture = new File(gameworld.getBackground());
		try {
			blank = ImageIO.read(blankTexture);
			playerSprites = ImageIO.read(playerTexture); 
			playerBullet = ImageIO.read(pBulTexture);
			background = ImageIO.read(bgTexture);

		} catch (IOException e) {
			playerSprites = blank;
			background = blank;
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
			drawBullet(temp, g);	 
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
		if (currentAnimationTime % ANIMATION_STEP_RATE == 0) e.stepAnimation();
		int xAnim = e.getHorizontalFrame() * e.getSpriteStepHorizontal() + e.getSpriteStartHorizontal(); 
		int yAnim = e.getVerticalFrame() * e.getSpriteStepVertical() + e.getSpriteStartVertical();
		int x = (int) e.getCentre().getX();
		int y = (int) e.getCentre().getY();
		g.drawImage(enemySprites.get(e.getTexture()), x, y, x+e.getWidth(), y+e.getHeight(), xAnim  , yAnim, xAnim + e.getSpriteWidth(), yAnim + e.getSpriteHeight(), null); 


	}

	private void drawBackground(Graphics g)
	{
		g.drawImage(background, 0,0, 1000, 1000, 0 , 0, 180, 180, null); 
	}
	
	private void drawBullet(Attack a, Graphics g)
	{
		if (a.isPlayerMade()) {
			// I want to try having the animation for the bullet here, as its simple for a player bullet
			int animationStep = (int) (currentAnimationTime / 6) % 4;	// Steps every 6 frames
			int xAnim, xOffset;
			if (a.getMovementVector().getX() > 0) {
				xAnim = animationStep * 50 + 7;
				xOffset = 30;
			} else {
				xAnim = animationStep * 50 + 37;
				xOffset = -30;
			}
			int x = (int) a.getCentre().getX();
			int y = (int) a.getCentre().getY();
			g.drawImage(playerBullet, x, y, x + a.getWidth(), y + a.getHeight(), xAnim, 20, xAnim + xOffset, 33, null); 
		}
	}
	

	private void drawPlayer(Player p, Graphics g) { 
		if (currentAnimationTime % p.getAnimationRate() == 0) p.stepAnimation();
		int xAnim = p.horizontalFrame * 140 + p.spriteStartHorizontal; 
		int yAnim = p.verticalFrame * 140 + p.spriteStartVertical;
		// The subtraction has to be done so that there is a disconnect between hitbox (getWidth()) and sprite
		int x = (int) p.getCentre().getX() - (p.drawWidth - p.getWidth()) + p.drawOffsetX;
		int y = (int) p.getCentre().getY() - (p.drawHeight - p.getHeight());
		int x2 = (int) p.getCentre().getX() + p.drawWidth - (p.drawWidth - p.getWidth()) + p.drawOffsetX;		
		int y2 = (int) p.getCentre().getY() + p.drawHeight - (p.drawHeight - p.getHeight());
		// This draws the players hitbox -> g.drawImage(blank, (int) p.getCentre().getX(), (int) p.getCentre().getY(), (int) p.getCentre().getX() + p.getWidth(), (int) p.getCentre().getY() + p.getHeight(), 0, 0, 15, 15, null);
		g.drawImage(playerSprites, x, y, x2, y2, xAnim, yAnim, xAnim+p.spriteWidth, yAnim+p.spriteHeight, null); 
	}

	private void drawPlatforms(PhysicalGameObject p, Graphics g) {
		// Load sprites and keep them in memory so we arent constantly rereading the file and image.
		// It would be better if this was done in the constructor, but this works for now.
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
