package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Globals {
    public static enum PROPERTY {PLAYER, BOX_TARGET, PLAYER_TARGET, NONE}
    public static enum SPRITEATTACHMENTS {PLAYER, PLAYER_COMPLETE, BOX_TARGET, PLAYER_TARGET, EMPTY, BOX, BOX_COMPLETE, WALL, LEVELICON}

    public static enum PANELDISPLAY {GAME, LEVELS}
    public static Color BGColor =  new Color(0, 0, 70);

    private static Map<SPRITEATTACHMENTS, BufferedImage> iconMap = new HashMap<SPRITEATTACHMENTS, BufferedImage>();
    private static String[] levelStrings;
    
    static {
        try{
            String spritePath = new File(".").getCanonicalPath() + "\\sprites\\";
            System.out.println(spritePath);
            String levelPath = new File(".").getCanonicalPath() + "\\levels\\";
            System.out.println(levelPath);

            // icon loading
            iconMap.put(SPRITEATTACHMENTS.PLAYER, ImageIO.read(new File(spritePath + "player.png")));
            iconMap.put(SPRITEATTACHMENTS.PLAYER_COMPLETE, ImageIO.read(new File(spritePath + "player_complete.png")));
            iconMap.put(SPRITEATTACHMENTS.BOX_TARGET, ImageIO.read(new File(spritePath + "box_target.png")));
            iconMap.put(SPRITEATTACHMENTS.PLAYER_TARGET, ImageIO.read(new File(spritePath + "player_target.png")));
            iconMap.put(SPRITEATTACHMENTS.EMPTY, ImageIO.read(new File(spritePath + "empty.png")));
            iconMap.put(SPRITEATTACHMENTS.BOX, ImageIO.read(new File(spritePath + "box.png")));
            iconMap.put(SPRITEATTACHMENTS.BOX_COMPLETE, ImageIO.read(new File(spritePath + "box_complete.png")));
            iconMap.put(SPRITEATTACHMENTS.WALL, ImageIO.read(new File(spritePath + "wall.png")));
            iconMap.put(SPRITEATTACHMENTS.LEVELICON, ImageIO.read(new File(spritePath + "level_icon.png")));


            
            // level loading
            int levelCnt = 0;
            while(true) {
                if(new File(levelPath + "" + (levelCnt) + ".txt").exists() == false) {
                    break;
                }
                levelCnt++;
            }

            levelStrings = new String[levelCnt];
            for(int i = 0; i < levelCnt; i++) {
                Scanner scanner = new Scanner(new File(levelPath + "" + i + ".txt"));
                levelStrings[i] = scanner.nextLine();
            }
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }

    public static BufferedImage getImage(SPRITEATTACHMENTS sprite) {
        return iconMap.get(sprite);
    }

    public static int getLevelStringsLength() {
        return levelStrings.length;
    }

    public static String getLevelString(int i) {
        return levelStrings[i];
    }
}
