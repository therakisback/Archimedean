import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import util.Point3f;


public class Stage {

	private float difficulty;
	private int mobCap;
	private float bounty;		// Amount of experience needed to progress
    private String background;
	private Point3f portalSpawn;
	private Point3f playerSpawn; 
	private PhysicalGameObject portal;
    private ArrayList<PhysicalGameObject> platforms = new ArrayList<>();
	private ArrayList<PhysicalGameObject> decorations = new ArrayList<>();
	private ArrayList<Integer> potentialEnemies = new ArrayList<>();
	private Random rand = new Random();

    public Stage(int progression) {
        switch (progression) {
			case 2: stageTwo();break;
			case 3: stageThree();break;
			default:stageOne();
		}
    }
    
    private void stageOne() {
		// If you cant figure this one out step away from the computer
		background = "res/environment/bg1.png";

		// Enemies
		potentialEnemies.add(1);
		difficulty = 0.015f;
		mobCap = 3;
		bounty = 8;

		// Player
		playerSpawn = new Point3f(750, 725, 0);
		portalSpawn = new Point3f(900, 458, 0);

		// Floor
		platforms.clear();
		int position = 0;
		platforms.add(new PhysicalGameObject(2, new Point3f(position, 936, 0)));
		position += platforms.get(0).getWidth();
		platforms.add(new PhysicalGameObject(3, new Point3f(position, 936, 0)));
		position += platforms.get(1).getWidth();
		platforms.add(new PhysicalGameObject(1, new Point3f(position, 936, 0)));
		position += platforms.get(2).getWidth();
		platforms.add(new PhysicalGameObject(2, new Point3f(position, 936, 0)));
		position += platforms.get(3).getWidth();
		platforms.add(new PhysicalGameObject(3, new Point3f(position, 936, 0)));

		// Platforms
		position = 200;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 758, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 758, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 758, 0))); 

		position = 500;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 658, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 658, 0)));
		position += 64; 
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 658, 0))); 

		position = 850;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 608, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 608, 0)));
		position += 64; 
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 608, 0))); 

		position = 1000;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0)));
		position += 64; 
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0)));
		position += 64;
		position += 64;
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0))); 
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0))); 
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0))); 
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0)));

		// Decorations
		decorations.clear();
		decorations.add(new PhysicalGameObject(4, new Point3f(900,1024-64-(144*4),0)));

		
	}

	private void stageTwo() {
		background = "res/environment/bg2.png";

		// Enemies
		potentialEnemies.clear();
		potentialEnemies.add(2);
		difficulty = 1f;
		mobCap = 5;
		bounty = 12;

		// Player
		playerSpawn = new Point3f(900, 458, 0);
		portalSpawn = new Point3f(700, 808, 0);

		// Floor
		platforms.clear();
		int position = 0;
		platforms.add(new PhysicalGameObject(2, new Point3f(position, 936, 0)));
		position += platforms.get(0).getWidth();
		platforms.add(new PhysicalGameObject(3, new Point3f(position, 936, 0)));
		position += platforms.get(1).getWidth();
		platforms.add(new PhysicalGameObject(1, new Point3f(position, 936, 0)));
		position += platforms.get(2).getWidth();
		platforms.add(new PhysicalGameObject(2, new Point3f(position, 936, 0)));
		position += platforms.get(3).getWidth();
		platforms.add(new PhysicalGameObject(3, new Point3f(position, 936, 0)));

		// Platforms
		position = 200;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0)));

		position = 400;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 658, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 658, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 658, 0)));

		position = 200;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 508, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 508, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 508, 0)));

		position = 1300;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 808, 0)));

		position = 1100;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 658, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 658, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 658, 0)));

		position = 1300;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 508, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 508, 0)));
		position += 64;
		platforms.add(new PhysicalGameObject(0, new Point3f(position, 508, 0)));

		// Decorations
		decorations.clear();
		decorations.add(new PhysicalGameObject(4, new Point3f(550,1024-64-(144*4),0)));
	}

	private void stageThree() {
		background = "res/environment/bg3.png";
		potentialEnemies.clear();
		// We can influence weighting of enemy spawns by adjusting this list
		potentialEnemies.add(3);
		potentialEnemies.add(3);
		potentialEnemies.add(3);
		potentialEnemies.add(3);
		potentialEnemies.add(1);
		difficulty = 0.05f;
		mobCap = 4;
		bounty = 20;

		playerSpawn = new Point3f(900, 458, 0);
		portalSpawn = new Point3f(700, 808, 0);

		// Floor
		platforms.clear();
		int position = 0;
		platforms.add(new PhysicalGameObject(2, new Point3f(position, 936, 0)));
		position += platforms.get(0).getWidth();
		platforms.add(new PhysicalGameObject(3, new Point3f(position, 936, 0)));
		position += platforms.get(1).getWidth();
		platforms.add(new PhysicalGameObject(1, new Point3f(position, 936, 0)));
		position += platforms.get(2).getWidth();
		platforms.add(new PhysicalGameObject(2, new Point3f(position, 936, 0)));
		position += platforms.get(3).getWidth();
		platforms.add(new PhysicalGameObject(3, new Point3f(position, 936, 0)));

		// Platforms
	}

	public PhysicalGameObject spawnPortal() {return new PhysicalGameObject(6, portalSpawn);}

	public List<PhysicalGameObject> platformList() {return platforms;}

	public List<PhysicalGameObject> decorationList() {return decorations;}

	public int randomEnemy() {return potentialEnemies.get(rand.nextInt(potentialEnemies.size()));}

    public String getBackground() {return background;}

    public Point3f getPortalSpawn() {return portalSpawn;}

    public Point3f getPlayerSpawn() {return playerSpawn;}

	public float getDifficulty() {return difficulty;}

	public float getBounty() {return bounty;}

    public int getMobCap() {return mobCap;}
}
