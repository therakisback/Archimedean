
import util.GameObject;
import util.Point3f;

public class Platform extends GameObject {
    
    int spriteWidth, spriteHeight;

    public Platform(int platformType, Point3f position) {
        super("res/blankSprite.png", 16, 16, position);
        switch(platformType) {
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
