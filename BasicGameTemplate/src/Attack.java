
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
    // Needs to store gameobject to follow, or vector to move. Not both.
    // Needs all the basic gameobject things as well.
    public Attack(String textureLocation,int width,int height,GameObject follow, Point3f offset, int duration, boolean playerMade) {
        super(textureLocation, width, height, follow.getCentre().add(offset));
        following = follow;
        this.offset = offset;
        this.duration = duration;
        this.playerMade = playerMade;
    }

    public Attack(String textureLocation,int width,int height,Point3f start,Vector3f movement, int duration, boolean playerMade) {
        super(textureLocation, width, height, start);
        movementVector = movement;
        this.duration = duration;
        this.playerMade = playerMade;
    }

    public Attack(String textureLocation,int width,int height,GameObject follow, Point3f offset, int duration) {
        super(textureLocation, width, height, follow.getCentre().add(offset));
        following = follow;
        this.offset = offset;
        this.duration = duration;
    }

    public Attack(String textureLocation,int width,int height,Point3f start,Vector3f movement, int duration) {
        super(textureLocation, width, height, start);
        movementVector = movement;
        this.duration = duration;
    }

    public void step() {
        if (following == null) this.getCentre().ApplyVector(movementVector);
        else if (movementVector == null) this.setCentre(following.getCentre().add(offset));
        duration--;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
