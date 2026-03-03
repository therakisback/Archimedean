

import util.GameObject;
import util.Point3f;
import util.Vector3f;


/*
 * Using this class to differentiate between normal game objects
 * like a player or platform, and bullets. Which have a different 
 * type of collision, alongside different mechanisms of movement.
 */
public class Attack extends GameObject{

    private GameObject following;
    private Point3f offset;
    private Vector3f movementVector;
    private int duration;
    private boolean playerMade = false;
    private float damage;
    // Needs to store gameobject to follow, or vector to move. Not both.
    // Needs all the basic gameobject things as well.
    // The two types of attacks implemented here + adding a default to whether the object is player made makes overloading the constructor quite a lot of code

    /**
     * Creates a new attack that originates at start and moves following movement
     * @param textureLocation String
     * @param width int
     * @param height int
     * @param start Point3f
     * @param movement Vector3f
     * @param duration int
     * @param playerMade boolean
     * @param damage float
     */
    public Attack(String textureLocation,int width,int height,Point3f start,Vector3f movement, int duration, boolean playerMade, float damage) {
        super(textureLocation, width, height, start);
        movementVector = movement;
        this.duration = duration;
        this.playerMade = playerMade;
        this.damage=damage;
    }

    /**
     * Creates a new attack that originates at start and moves following movement
     * Assuming enemy-made constructor, add boolean for whether it is made by a player after int duration
     * @param textureLocation String
     * @param width int
     * @param height int
     * @param start Point3f
     * @param movement Vector3f
     * @param duration int
     * @param damage float
     */
    public Attack(String textureLocation,int width,int height,Point3f start,Vector3f movement, int duration, float damage) {
        this(textureLocation, width, height, start, movement, duration, false, damage);
    }

    public void step() {
        if (following == null) this.getCentre().ApplyVector(movementVector);
        else if (movementVector == null) this.setCentre(following.getCentre().add(offset));
        duration--;
    }

    // --- Setters & Getters ---
    public int getDuration() {return duration;}

    public void setDuration(int duration) {this.duration = duration;}

    public float getDamage() {return damage;}

    public boolean isPlayerMade() {return playerMade;}

    public Vector3f getMovementVector() {return movementVector;}
}
