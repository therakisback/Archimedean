
import java.util.List;
import java.util.Random;
import util.GameIO;
import util.GameObject;
import util.Point3f;
import util.Vector3f;

/**
 * This class holds the vast majority of the player characters information, as such this class is mostly variables
 * and data. The class is also a singleton, I doubt and hope player will not be called anywhere other than
 * model, but it being singleton is just an insurance, as this is a singleplayer game and accidentally
 * creating two players would cause disconnects and take up a lot of memory.
 */
public class Player extends GameObject {

    private static final Player player = new Player();
    // Constants
    private final float GRAVITY_RATE = 8;
    private final float JUMP_SPEED = 8;
    private final float[] BREAK_POINTS = {0f, 10f, 20f, 30f, 40f, 50f, 60f, 70f, 80f, 90f};
    private final int JUMP_TIME = 30;
    private final GameIO io = GameIO.getInstance();
    // Attacks
    Random diceGen = new Random();
    private float damage = 1f;
    private int attackWidth = 32;
    private int attackHeight = 32;
    private int attackDuration = 50;
    private int attackSpeed = 10;
    private int playerWidth = 50;
    private int playerHeight = 50;
    private int iFrames = 30;    // TODO could be modified by upgrades
    private boolean hasDiceUpgrade = false;
    // Player stats
    private int firstAbility = 0;
    private int secondAbility = 0;
    private int thirdAbility = 0;
    private float xpMult = 1f;
    private float experience = 0;
    private float maxHealth = 4;
    private float hp = maxHealth; 
    // Movement
    private float moveSpeed = 4;
    private int facing = -1;
    private boolean onGround = false;
    private boolean jumpReset = true;
    // Cooldowns
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
            float momentum = Math.min(1f, (airtime/(JUMP_SPEED * 2)));
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
            return new Attack("res/Bullet.png", attackWidth, attackHeight, spawn, movement, 
                                attackDuration, true, rtd());
        } else {
            return new Attack("res/Bullet.png", attackWidth, attackHeight, spawn, movement, 
                                attackDuration, true, damage);
        } 
    }

    // --- Levelling ---
    public int level(float xp) {
        experience += xp * xpMult;
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
        if (firstAbility == 2) {
            if (amount >= maxHealth-hp) hp = maxHealth;
            else hp += amount;
        }
    }

    public void flight(){

    }

    public void passive(int passiveID) {
        // Dice upgrade is special, more of these would be annoying to implement
        if (passiveID == 8) hasDiceUpgrade = true;
        List<Float> mods = io.getPassiveModifiers(passiveID);
        moveSpeed *= mods.get(0);
        damage *= mods.get(1);
        attackSpeed *= mods.get(2);
        playerWidth *= mods.get(3);
        playerHeight *= mods.get(4);
        attackWidth *= mods.get(5);
        attackHeight *= mods.get(6);
        attackDuration *= mods.get(7);
        xpMult *= mods.get(8);
        maxHealth *= mods.get(9);
        // Apply changes now
        hp = maxHealth;
        this.setWidth(playerWidth);
        this.setHeight(playerHeight);
    }

    public void active(int activeID) {
        // TODO Unsure if active level-up is necessary here but we will see.
        switch(io.getKeyByID(activeID)) {
            case 'q': {firstAbility = activeID;}
            case 'e': {secondAbility = activeID;}
            case 'r': {thirdAbility = activeID;}
            default: {}
        }
    }

    // --- Getters & Setters


    public void isOnGround() {onGround = true;}

    public void isOnGround(boolean val) {onGround = val;}

    public float getDamage() {return damage;}

    public float damage(float dealt) {hp -= dealt;return hp;}

    public int getIFrames() {return iFrames;}

    public float attackSpeed() {return attackSpeed;}
}
