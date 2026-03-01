import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import util.Point3f;


public class Stage {

    private String background;
	private Point3f portalSpawn;
	private Point3f playerSpawn; 
    private ArrayList<PhysicalGameObject> platforms = new ArrayList<>();
	private ArrayList<PhysicalGameObject> decorations = new ArrayList<>();
	private ArrayList<Integer> potentialEnemies = new ArrayList<>();
	private Random rand = new Random();

    public Stage(int progression) {
        switch (progression) {
			case 1: stageOne();
		}
    }
    
    private void stageOne() {
		background = "res/environment/ss_background_layered.png";

		// Floor
		platforms.clear();
		int position = 0;
		platforms.add(new PhysicalGameObject(2, new Point3f(0, 936, 0)));
		position += platforms.get(0).getWidth();
		platforms.add(new PhysicalGameObject(3, new Point3f(position, 936, 0)));
		position += platforms.get(1).getWidth();
		platforms.add(new PhysicalGameObject(1, new Point3f(position, 936, 0)));

		// Platforms
		position  = 200;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0)));
		position += platforms.get(3).getWidth(); 
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0)));
		position += platforms.get(4).getWidth(); 
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0))); 

		position = 500;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 708, 0)));
		position += platforms.get(3).getWidth(); 
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 708, 0)));
		position += platforms.get(4).getWidth(); 
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 708, 0))); 

		// Decorations
		decorations.clear();
		decorations.add(new PhysicalGameObject(4, new Point3f(600,1024-64-(144*4),0)));

		potentialEnemies.add(1);
	}

	public List<PhysicalGameObject> platformList() {return platforms;}

	public List<PhysicalGameObject> decorationList() {return decorations;}

	public int randomEnemy() {
		return potentialEnemies.get(rand.nextInt() % potentialEnemies.size());
	}

    public String getBackground() {
        return background;
    }

    public Point3f getPortalSpawn() {
        return portalSpawn;
    }

    public Point3f getPlayerSpawn() {
        return playerSpawn;
    }
}
