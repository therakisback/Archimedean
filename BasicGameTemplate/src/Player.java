
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
    // Animations - A lot of these are public as to make them simpler to access, I dont care if encapsulation is perfect for animations
    private int frames = 12;
    public int verticalFrame = 0;
    public int horizontalFrame = 0;
    public int spriteHeight = 60;
    public int spriteWidth = 75;
    public int drawWidth = 175;
    public int drawHeight = 120;
    public int drawOffsetX = 120;
    public int spriteStartHorizontal = 53;
    public int spriteStartVertical = 37;
    private int animationRate = 6;    // How many ticks between the next frame
    // Attacks
    Random diceGen = new Random();
    private float damage = 1f;
    private int attackWidth = 87;
    private int attackHeight = 32;
    private int attackDuration = 20;
    private int attackSpeed = 3;
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
    private float maxHealth = 5;
    private float hp = maxHealth; 
    // Movement
    private float moveSpeed = 4;
    private int facing = 1;
    private boolean onGround = false;
    private boolean jumpReset = true;
    // Cooldowns
    private int airtime = 0;

    private Player() {
        super("res/player/Witch.png",30,100,new Point3f(0,0,0));
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
        Vector3f movement = new Vector3f(facing * 20, 0, 0);
        if(hasDiceUpgrade) {
            // If the player hase 'Dice' we randomize the damage :)
            return new Attack("res/player/Moving.png", attackWidth, attackHeight, spawn, movement, 
                                attackDuration, true, rtd());
        } else {
            return new Attack("res/player/Moving.png", attackWidth, attackHeight, spawn, movement, 
                                attackDuration, true, damage);
        } 
    }

    // --- Levelling ---    - No longer used for gaining abilities
    public void level(float xp) {
        experience += xp * xpMult;
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
        // TODO Implement passives
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

    // --- Animations ---
    public void stepAnimation() {
        horizontalFrame++;
        horizontalFrame = horizontalFrame % frames;
    }

    /**
     * Sets up a different type of animation based on Index. Unfortunately due to the framework of this game, some of this will have to be handled by model.
     * @param animationIndex refers to the type of animation to do, 0 & undef is idle, 1 is running, 2 is jumping, 3 is falling,
     *  4 is shooting, 5 is getting hit, 6 is dieing
     */
    public void changeAnimation(int animationIndex) {
        if (verticalFrame != animationIndex) horizontalFrame = 0;
        switch(animationIndex) {
            case 1: {   // Run
                frames = 8;
                verticalFrame = 1;
                animationRate = 6;
                break;
            }
            case 2: {   // jump
                frames = 3;
                verticalFrame = 2;
                animationRate = 6;
                break;
            }
            case 3: {   // fall
                frames = 3;
                verticalFrame = 3;
                animationRate = 6;
                break;
            }
            case 4: {  // shooting 
                frames = 3;
                verticalFrame = 4;
                animationRate = (60 / attackSpeed) / 3;
                break;
            }
            case 5: {   // hit
                frames = 3;
                verticalFrame = 5;
                animationRate = 11;
                break;
            }
            case 6: {   // die
                frames = 18;
                verticalFrame = 6;
                animationRate = 8;
                break;
            }
            default: {  // idle
                frames = 10;
                verticalFrame = 0;
                animationRate = 10;
            }
        }
        // Handle flipping of sprites
        if (facing == 1) {
            spriteHeight = 60;
            spriteWidth = 75;
            spriteStartHorizontal = 53;
            spriteStartVertical = 37;
            drawOffsetX = 120;
        }  else if (facing == -1) {
            spriteHeight = 60;
            spriteWidth = -75;
            drawOffsetX = 20;
            spriteStartHorizontal = 53 + 75;
            spriteStartVertical = 37;
        }
    }

    // --- Getters & Setters

    public boolean isOnGround() {return onGround;}

    public void isOnGround(boolean val) {onGround = val;}

    public float getDamage() {return damage;}

    public float damage(float dealt) {hp -= dealt;return hp;}

    public int getIFrames() {return iFrames;}

    public float attackSpeed() {return attackSpeed;}

    public float getXP() {return experience;}

    // --- Animation getters ---

    public int getVerticalFrame() {return verticalFrame;}

    public int getHorizontalFrame() {return horizontalFrame;}

    public int getSpriteStartVertical() {return spriteStartVertical;}

    public int getSpriteStartHorizontal() {return spriteStartHorizontal;}

    public int getSpriteWidth() {return spriteWidth;}

    public int getSpriteHeight() {return spriteHeight;}

    public int getAnimationRate() {return animationRate;}

    public int getDrawWidth() {return drawWidth;}

    public int getDrawHeight() {return drawHeight;}
}
