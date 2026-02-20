import java.util.Random;
import util.GameObject;
import util.Point3f;
import util.Vector3f;

public class Player extends GameObject {

    private static Player player = new Player();
    // Constants
    private final float GRAVITY_RATE = 8;
    private final float JUMP_SPEED = 8;
    private final float[] BREAK_POINTS = {0f, 10f, 20f, 30f, 40f, 50f, 60f, 70f, 80f, 90f};
    private final int JUMP_TIME = 60;
    // Attacks
    Random diceGen = new Random();
    private float damage = 1f;
    private int attackWidth = 32;
    private int attackHeight = 32;
    private int attackDuration = 50;
    private int attackSpeed = 10;
    private int playerWidth = 50;
    private int playerHeight = 50;
    private boolean hasDiceUpgrade = false;
    private boolean hasUkeleleUpgrade = false;
    // Player stats
    private int firstAbility = 0;
    private int secondAbility = 0;
    private int thirdAbility = 0;
    private float xpMult = 1f;
    private float experience = 0;
    private int maxHealth = 4;
    private int hp = maxHealth; 
    // Movement
    private float moveSpeed = 4;
    private int facing = -1;
    private boolean onGround = false;
    private boolean jumpReset = true;
    // Cooldowns
    private int pAttackCooldown = 0;
    private int airtime = 0;

    private Player() {
        super("res/Lightning.png",50,50,new Point3f(500,800,0));
    }

    public static Player getPlayer() {
        return player;
    }

    // --- Movement ---

    public Vector3f jump() {
        Vector3f velocity = new Vector3f(0, -GRAVITY_RATE, 0);
        if (onGround && jumpReset) {
				velocity.setY(JUMP_SPEED);
				onGround = false;
				jumpReset = false;
				airtime = JUMP_TIME;
			} else if (airtime > 0) {
				// Momentum is used here to allow a jump to be high without being insanely fast
				// It essentially delays and dampens gravity while the player holds w
				float momentum = Math.min(1f, (airtime/JUMP_SPEED));
				velocity.setY(JUMP_SPEED * momentum);
			}
        if (airtime > 0) airtime--;
        return velocity;
    }

    public Vector3f fall() {
        Vector3f velocity = new Vector3f(0, -GRAVITY_RATE, 0);
        if (airtime > JUMP_SPEED) airtime = (int)JUMP_SPEED;
        if (airtime > 0 ) {
            float momentum = airtime/JUMP_SPEED;
            velocity.setY(JUMP_SPEED * momentum);
        }
        if (airtime > 0 ) airtime--;
        jumpReset = true;
        return velocity;
    }

    public Vector3f moveLeft() {
        facing = -1;
		return new Vector3f(-moveSpeed, 0, 0);
    }

    public Vector3f moveRight() {
        facing = 1;
        return new Vector3f(moveSpeed, 0, 0);
    }

    public void bonk() {if (airtime > JUMP_SPEED) airtime = (int) JUMP_SPEED;}

    // --- Attacks ---

    public Attack fire() {
        Point3f spawn = this.getCentre().add(new Point3f(32, 0, 0));
        Vector3f movement = new Vector3f(facing * attackSpeed, 0, 0);
        if(hasDiceUpgrade) {
            // If the player hase 'Dice' we randomize the damage :)
            return new Attack("res/Bullet.png", 32, 32, spawn, movement, 
                                attackDuration, true, rtd());
        } else {
            return new Attack("res/Bullet.png", 32, 32, spawn, movement, 
                                attackDuration, true, damage);
        } 
        
    
    }

    // --- Levelling ---
    public int level(float xp) {
        experience += xp;
        for (int i = BREAK_POINTS.length-1; i > 0; i--) {
            if (experience >= BREAK_POINTS[i]) return i;
        }
        return 0;
    }

    // Abilities
        // Passives
    private float rtd() {
        return diceGen.nextFloat() * damage;
    }
        // Actives
    public void heal(int amount) {
        if (amount >= maxHealth-hp) hp = maxHealth;
        else hp += amount;
    }

    public void passive(int passiveID) {
        // TODO implement passive id selection and levelling
    }

    public void active(int activeID) {
        // TODO Unsure if active level-up is necessary here but we will see.
    }

    public void isOnGround() {onGround = true;}

    public void isOnGround(boolean val) {onGround = val;}

    public float getDamage() {return damage;}

    
    
}
