
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener{
    
    private static boolean lmb = false;
    private static boolean rmb = false;
    private static boolean mouseOnFrame = true;

    private static final Mouse instance = new Mouse();

    public static Mouse getInstance() {
        return instance;
    }

    // I have no need for this function.
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if(!mouseOnFrame) return;
        switch (e.getButton()) 
		{
			case 1:setLMBPressed(true);break;  
			case 2:setRMBPressed(true);break; 
		    default:
		    	//System.out.println("Controller test:  Unknown key pressed");
		        break;
		} 
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) 
		{
			case 1:setLMBPressed(false);break;  
			case 2:setRMBPressed(false);break; 
		    default:
		    	//System.out.println("Controller test:  Unknown key pressed");
		        break;
		} 
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseOnFrame = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseOnFrame = false;
    }

    private void setLMBPressed(boolean val) {lmb = val;}
    private void setRMBPressed(boolean val) {rmb = val;}
    public boolean isLMBPressed() {return lmb;}
    public boolean isRMBPressed() {return rmb;}

    


}
