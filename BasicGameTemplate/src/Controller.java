import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
 */ 

//Singeton pattern
public class Controller implements KeyListener {

	private static boolean KeyAPressed= false;
	private static boolean KeyDPressed= false;
	private static boolean KeyWPressed= false;
	private static boolean KeyQPressed= false;
	private static boolean KeyEPressed= false;
	private static boolean KeyRPressed= false;
	private static boolean KeySpacePressed= false;

	private static final Controller instance = new Controller();

	public Controller() {}
	 
	public static Controller getInstance(){
	        return instance;
	}
	   
	@Override
	// Key pressed , will keep triggering 
	public void keyTyped(KeyEvent e) {}

	// TODO need to implement extra listening for active abilities
	@Override
	public void keyPressed(KeyEvent e) 
	{ 
		switch (e.getKeyChar()) 
		{
			case 'a':setKeyAPressed(true);break;  
			case 'w':setKeyWPressed(true);break;
			case 'd':setKeyDPressed(true);break;
			case 'q':setKeySpacePressed(true);break; 
			case 'e':setKeySpacePressed(true);break;  
			case 'r':setKeySpacePressed(true);break;
			case ' ':setKeySpacePressed(true);break;   
		    default:
		    	//System.out.println("Controller test:  Unknown key pressed");
		        break;
		}  
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{ 
		switch (e.getKeyChar()) 
		{
			case 'a':setKeyAPressed(false);break;  
			case 'w':setKeyWPressed(false);break;
			case 'd':setKeyDPressed(false);break;
			case 'q':setKeySpacePressed(false);break; 
			case 'e':setKeySpacePressed(false);break;  
			case 'r':setKeySpacePressed(false);break;    
			case ' ':setKeySpacePressed(false);break;   
		    default:

		    	System.out.println("Controller test:  Unknown key pressed");
		        break;
		}  
		 //upper case 
	
	}

	public boolean isKeyAPressed() {return KeyAPressed;}
	public void setKeyAPressed(boolean keyAPressed) {KeyAPressed = keyAPressed;}


	public boolean isKeyDPressed() {return KeyDPressed;}
	public void setKeyDPressed(boolean keyDPressed) {KeyDPressed = keyDPressed;}


	public boolean isKeyWPressed() {return KeyWPressed;}
	public void setKeyWPressed(boolean keyWPressed) {KeyWPressed = keyWPressed;}


	public boolean isKeySpacePressed() {return KeySpacePressed;}
	public void setKeySpacePressed(boolean keySpacePressed) {KeySpacePressed = keySpacePressed;} 


	public boolean isKeyQPressed() {return KeyQPressed;}
	public void setKeyQPressed(boolean keyPressed) {KeyQPressed = keyPressed;}


	public boolean isKeyEPressed() {return KeyEPressed;}
	public void setKeyEPressed(boolean keyPressed) {KeyEPressed = keyPressed;}


	public boolean isKeyRPressed() {return KeyRPressed;}
	public void setKeyRPressed(boolean keyPressed) {KeyRPressed = keyPressed;}
}

/*
 * 
 * KEYBOARD :-) . can you add a mouse or a gamepad 

 *@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ @@@@@@@@@@@@@@@

  @@@     @@@@    @@@@    @@@@    @@@@     @@@     @@@     @@@     @@@     @@@  

  @@@     @@@     @@@     @@@@     @@@     @@@     @@@     @@@     @@@     @@@  

  @@@     @@@     @@@     @@@@    @@@@     @@@     @@@     @@@     @@@     @@@  

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

@     @@@     @@@     @@@      @@      @@@     @@@     @@@     @@@     @@@     @

@     @@@   W   @@@     @@@      @@      @@@     @@@     @@@     @@@     @@@     @

@@    @@@@     @@@@    @@@@    @@@@    @@@@     @@@     @@@     @@@     @@@     @

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@N@@@@@@@@@@@@@@@@@@@@@@@@@@@

@@@     @@@      @@      @@      @@      @@@     @@@     @@@     @@@     @@@    

@@@   A   @@@  S     @@  D     @@      @@@     @@@     @@@     @@@     @@@     @@@    

@@@@ @  @@@@@@@@@@@@ @@@@@@@    @@@@@@@@@@@@    @@@@@@@@@@@@     @@@@   @@@@@   

    @@@     @@@@    @@@@    @@@@    $@@@     @@@     @@@     @@@     @@@     @@@

    @@@ $   @@@      @@      @@ /Q   @@ ]M   @@@     @@@     @@@     @@@     @@@

    @@@     @@@      @@      @@      @@      @@@     @@@     @@@     @@@     @@@

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

@       @@@                                                @@@       @@@       @

@       @@@              SPACE KEY       @@@        @@ PQ     

@       @@@                                                @@@        @@        

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * 
 * 
 * 
 * 
 * 
 */
