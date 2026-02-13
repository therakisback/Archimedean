import util.GameObject;
import util.Point3f;
import util.Vector3f;

public class Player extends GameObject {

    private final float GRAVITY_RATE = 8;
    private final float JUMP_SPEED = 8;
    private final float MOVE_SPEED = 4;
    private final float[] BREAK_POINTS = {0f, 10f, 20f, 30f, 40f, 50f, 60f, 70f, 80f, 90f};
    private final int JUMP_TIME = 60;
    private final int ATTACK_SPEED = 10;

    private float experience = 0;
    private int facing = -1;
    private int pAttackCooldown = 0;
    private int airtime = 0;
    private boolean onGround = false;
    private boolean jumpReset = true;


    public Player() {
        super("res/Lightning.png",50,50,new Point3f(500,800,0));
    }

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
		return new Vector3f(-MOVE_SPEED, 0, 0);
    }

    public Vector3f moveRight() {
        facing = 1;
        return new Vector3f(MOVE_SPEED, 0, 0);
    }

    public void isOnGround() {onGround = true;}

    public void isOnGround(boolean val) {onGround = val;}

    public void bonk() {if (airtime > JUMP_SPEED) airtime = (int) JUMP_SPEED;}


    public Attack fire() {
        Point3f spawn = this.getCentre().add(new Point3f(32, 0, 0));
        Vector3f movement = new Vector3f(facing * ATTACK_SPEED, 0, 0);
        return new Attack("res/Bullet.png", 32, 32, spawn, movement, 120, true);
    
    }

    public int level(float xp) {
        experience += xp;
        for (int i = BREAK_POINTS.length; i > 0; i++) {
            if (experience >= BREAK_POINTS[i]) return i;
        }
        return 0;
    }
    
}
