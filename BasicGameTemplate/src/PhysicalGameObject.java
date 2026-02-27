
import util.GameObject;
import util.Point3f;

/**
 * In this case, PhysicalGameObject is being expanded to 
 */
public class PhysicalGameObject extends GameObject {
    
    int spriteWidth, spriteHeight;

    public PhysicalGameObject(int objectType, Point3f position) {
        super("res/blankSprite.png", 16, 16, position);
        switch (objectType) {
            case 1: {
                super.textureLocation = "res/environment/springstar_surface_1.png";
                spriteWidth = 112; spriteHeight = 16;break;
            }
            case 2: {
                super.textureLocation = "res/environment/springstar_surface_2.png";
                spriteWidth = 96; spriteHeight = 16;break;
            }
            case 3: {
                super.textureLocation = "res/environment/springstar_surface_pond.png";
                spriteWidth = 80; spriteHeight = 16;break;
            }
            case 4: {
                super.textureLocation = "res/environment/springstar_tree.png";
                spriteWidth = 128; spriteHeight = 144;break;
            }
            case 5: {
                super.textureLocation = "res/environment/springstar_boulder.png";
                spriteWidth = 32; spriteHeight = 32;break;
            }
            default: {
                super.textureLocation = "res/environment/springstar_middle_platform.png";
                spriteWidth = 16; spriteHeight = 5;break;
            }
        }
    }

    public int getSpriteHeight() {return spriteHeight;}
    public int getSpriteWidth() {return spriteWidth;}

    @Override
    public int getHeight() {return spriteHeight * 4;}
    @Override
    public int getWidth() {return spriteWidth * 4;}


}
