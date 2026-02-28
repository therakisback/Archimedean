import java.util.ArrayList;
import util.Point3f;


public class Stage {

    public String background;
    public ArrayList<PhysicalGameObject> platforms;

    private Stage(int progession) {
        
    }
    
    private void stageOne() {
		background = "res/environment/ss_background_layered.png";

		platforms.clear();
		int position = 0;
		platforms.add(new PhysicalGameObject(2, new Point3f(0, 936, 0)));
		position += platforms.get(0).getWidth();
		platforms.add(new PhysicalGameObject(3, new Point3f(position, 936, 0)));
		position += platforms.get(1).getWidth();
		platforms.add(new PhysicalGameObject(1, new Point3f(position, 936, 0)));
		position  = platforms.get(0).getWidth();
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0)));
		position += platforms.get(3).getWidth(); 
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0)));
		position += platforms.get(4).getWidth(); 
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0))); 

		DecorationList.clear();
		DecorationList.add(new PhysicalGameObject(4, new Point3f(600,1024-64-(144*4),0)));
	}
}
