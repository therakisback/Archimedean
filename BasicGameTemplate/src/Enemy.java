
import java.util.Random;
import util.GameObject;
import util.Point3f;
import util.Vector3f;

/* To me it doesn't make sense to treat all enemies as simply a gameobject.
 * I believe it will simply be easier if it is self contained, and changeable.
 * I am going to make this class handle most types of enemies.
 */ 
public class Enemy extends GameObject {

    private final float GRAVITYRATE = 4;
	private final int JUMPTIME = 40;
    private final float ATTACKDELAY = 5;

    Vector3f movement = new Vector3f();
    private Point3f spawn;
    private Point3f target;
    private GameObject player;
    private float moveSpeed;
    private float acceleration;
    private float damage;
    private float attackCooldown = 0;
    private float health;
    private int airtime = 0;
    private int enemyType = 0; 
    private boolean onGround;
    private Random random = new Random();

    public Enemy(int enemyType, GameObject player) {
        // I hate that java v<25 doesn't allow flexible constructor bodies. This is a bandaid to a feature that should've been implemented LONG ago.
        super("res/UFO.png", 50, 50, new Point3f(-1000, -1000, 0));
        this.player = player;
        this.enemyType = enemyType;
        switch (enemyType) {
            default: {
                int spawnX = random.nextBoolean() ?  0 : -950;
                spawn = new Point3f(spawnX, 850, 0);
                super.setCentre(spawn);
                target = player.getCentre();
                damage = 1;
                moveSpeed = 4;
                acceleration = .1f;
                health = 1;
                break;
            }
        }
    }

    /**
     * Does one frame of movement / calculation for an enemy
     * @return amount of damage to be dealt to the player/
     */
    public void step() {
        switch(enemyType) {
            default: {
                // Movement
                target = player.getCentre();
                if (target.distance(this.centre) > moveSpeed) {
                    // If player is above enemy, jump
                    if (target.getY() + player.getHeight() < this.centre.getY()) {
                        if (onGround) {
                            airtime = JUMPTIME;
                            onGround = false; 
                        }
                    }
                    // Move horizontally towards player
                    if (target.getX() < this.centre.getX()) {
                        if (movement.getX() > -moveSpeed) 
                            movement = movement.PlusVector(new Vector3f(-acceleration, 0, 0));
                    } else if (target.getX() > this.centre.getX()) {
                        if(movement.getX() < moveSpeed)
                            movement = movement.PlusVector(new Vector3f(acceleration, 0, 0));
                    }
                }

                // Gravity && jumping (easier if seperated)
                jump(movement);
                this.centre.ApplyVector(movement);
                }
            }
        }

    private void jump(Vector3f movementVector) {
        // Moving up
        if (airtime > 0) {
            float momentum = Math.min(1f, (airtime/(GRAVITYRATE * 2)));
			movementVector.setY(GRAVITYRATE * momentum);
            airtime--;
        } else if (movement.getY() > -GRAVITYRATE) {
            movement = movement.PlusVector(new Vector3f(0, -GRAVITYRATE, 0));
        }
    }

    public void isOnGround() {onGround = true;}

    public void bonk() {if (airtime > GRAVITYRATE) airtime = (int) GRAVITYRATE;}

    public int getEnemyType() {return enemyType;}

    public float getDamage() {return damage;}

    public float damage(float dealt) {health -= damage;return health;}

    public float hp() {return health;}
}
