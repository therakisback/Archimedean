
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

        // Animations
    private int frames = 7;
    private int verticalFrame = 0;
    private int horizontalFrame = 0;
    private int spriteHeight = 22;
    private int spriteWidth = 26;
    private int spriteStartHorizontal = 13;
    private int spriteStartVertical = 26;
    private int spriteStepHorizontal = 128;
    private int spriteStepVertical = 64; 


    public Enemy(int enemyType, GameObject player) {
        // I hate that java v<25 doesn't allow flexible constructor bodies. This is a bandaid to a feature that should've been implemented LONG ago.
        super("res/enemies/Sword.png", 75, 75, new Point3f(-1000, -1000, 0));
        if (enemyType == 0) enemyType = 1;
        this.player = player;
        this.enemyType = enemyType;
        switch (enemyType) {
            case 2: {   // Bats
                super.textureLocation = "res/enemies/bat_angry_fangs.png";
                int spawnX = random.nextInt(3) * 760;
                spawn = new Point3f(spawnX, 100, 0);
                super.setCentre(spawn);
                target = player.getCentre();
                damage = 1;
                moveSpeed = 2;
                acceleration = .05f;
                health = 1;
                width = 60;
                height = 32;

                frames = 4;
                spriteWidth = 30;
                spriteHeight = 16;
                spriteStartHorizontal = 1;
                spriteStartVertical = 7;
                spriteStepHorizontal = 32;
                spriteStepVertical = 0; 
                break;
            }
            case 3: {   // Wizard?

            }
            default: {  // Sword
                int spawnX = random.nextBoolean() ?  0 : 1550;
                spawn = new Point3f(spawnX, 850, 0);
                super.setCentre(spawn);
                target = player.getCentre();
                damage = 1;
                moveSpeed = 4;
                acceleration = .1f;
                health = 1;
                
                frames = 7;
                spriteHeight = 22;
                spriteWidth = 26;
                spriteStartHorizontal = 13;
                spriteStartVertical = 26;
                spriteStepHorizontal = 128;
                spriteStepVertical = 64; 
            }
        
        }
    }

    /**
     * Does one frame of movement / calculation for an enemy
     * @return amount of damage to be dealt to the player/
     */
    public void step() {
        switch(enemyType) {
            case 2: {   // Bats
                // Movement - very simple as they dont need gravity, or ground
                target = player.getCentre();
                Vector3f path = target.MinusPoint(centre).Normal(moveSpeed);
                if(target.distance(centre) > moveSpeed) {
                    path.setY(-path.getY() * 2);
                    centre.ApplyVector(path);
                }
                break;
            }
            default: {  // Swords
                // Movement
                target = player.getCentre();
                if (target.distance(centre) > moveSpeed) {
                    // If player is above enemy, jump
                    if (target.getY() + player.getHeight() < centre.getY()) {
                        if (onGround) {
                            airtime = JUMPTIME;
                            onGround = false; 
                        }
                    }
                    // Move horizontally towards player
                    if (target.getX() < centre.getX()) {
                        if (movement.getX() > -moveSpeed) 
                            movement = movement.PlusVector(new Vector3f(-acceleration, 0, 0));
                    } else if (target.getX() > centre.getX()) {
                        if(movement.getX() < moveSpeed)
                            movement = movement.PlusVector(new Vector3f(acceleration, 0, 0));
                    }
                }

                // Gravity && jumping (easier if seperated)
                jump(movement);
                centre.ApplyVector(movement);
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

    // --- Animation ---

    public void stepAnimation() {
        horizontalFrame++;
        horizontalFrame = horizontalFrame % frames;
    }


    // --- Setters & Getters ---

    public void isOnGround() {onGround = true;}

    public void bonk() {if (airtime > GRAVITYRATE) airtime = (int) GRAVITYRATE;}

    public int getEnemyType() {return enemyType;}

    public float getDamage() {return damage;}

    public float damage(float dealt) {health -= damage;return health;}

    public float hp() {return health;}

    // Animation getters, It has to hold all this info in case I want to add another enemy type & sprite.

    public int getVerticalFrame() {return verticalFrame;}

    public int getHorizontalFrame() {return horizontalFrame;}

    public int getSpriteStepVertical() {return spriteStepVertical;}

    public int getSpriteStepHorizontal() {return spriteStepHorizontal;}

    public int getSpriteStartVertical() {return spriteStartVertical;}

    public int getSpriteStartHorizontal() {return spriteStartHorizontal;}

    public int getSpriteWidth() {return spriteWidth;}

    public int getSpriteHeight() {return spriteHeight;}
}
