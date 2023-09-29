package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import main.Globals.PANELDISPLAY;
import main.Globals.SPRITEATTACHMENTS;


public class Handler {

    // instance vars
    private GameHandler gameHandler;
    private LevelHandler levelHandler;
    private Frame frame;

    private PANELDISPLAY p;
    
    // constructors
    public Handler() {
        gameHandler = new GameHandler();
        levelHandler = new LevelHandler();
        frame = new Frame(this);

        p = PANELDISPLAY.LEVELS;

        runLoadCycle();
    }

    // key input handling
    protected void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if(p == PANELDISPLAY.GAME) { if(move(0, -1)) p = PANELDISPLAY.LEVELS; } // move returns if the game is won
                else if(p == PANELDISPLAY.LEVELS) levelHandler.decrementLevel();
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if(p == PANELDISPLAY.GAME) { if(move(-1, 0)) p = PANELDISPLAY.LEVELS; }
                else if(p == PANELDISPLAY.LEVELS) levelHandler.decrementLevel();
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if(p == PANELDISPLAY.GAME) { if(move(0, 1)) p = PANELDISPLAY.LEVELS; }
                else if(p == PANELDISPLAY.LEVELS) levelHandler.incrementLevel();
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if(p == PANELDISPLAY.GAME) { if(move(1, 0)) p = PANELDISPLAY.LEVELS; }
                else if(p == PANELDISPLAY.LEVELS) levelHandler.incrementLevel();
                break;
            case KeyEvent.VK_ENTER:
                if(p == PANELDISPLAY.LEVELS) p = PANELDISPLAY.GAME;
                break;
            case KeyEvent.VK_R:
                if(p == PANELDISPLAY.GAME) gameHandler.loadBoard(levelHandler.getString());
                break;
            case KeyEvent.VK_ESCAPE:
                if(p == PANELDISPLAY.GAME) p = PANELDISPLAY.LEVELS;
                else if(p == PANELDISPLAY.LEVELS) System.exit(0);
            default:
                System.out.println("not a keybind");
        }

        if(p == PANELDISPLAY.GAME) draw(); // bad to reload the board if the player is playing!
        else if(p == PANELDISPLAY.LEVELS) runLoadCycle();
    }

    // methods for logic
    private boolean move(int xOffset, int yOffset) {
        gameHandler.move(xOffset, yOffset);
        return gameHandler.checkWinState();
    }

    // methods for drawing functions
    private void runLoadCycle() {
        gameHandler.loadBoard(levelHandler.getString());
        draw();
    }

    protected void draw() {
        try {
            switch(p) {
                case GAME:
                    frame.getPanel().setLastFrame(drawGameBoard(frame.getPanel().getWidth(), frame.getPanel().getHeight()));
                    break;
                case LEVELS:
                    frame.getPanel().setLastFrame(drawLevelSelect(frame.getPanel().getWidth(), frame.getPanel().getHeight()));
                    break;
                default:
                    System.out.println("something went wrong: Handler draw()");
            }

            frame.getPanel().repaint();
        }
        catch(Exception e) {
            // System.out.println("protected void draw in Handler: " + e);
        }
    }

    // drawing functions
    private BufferedImage drawGameBoard(int width, int height) {
        Grid objectGrid = gameHandler.getObjectGrid();
        Grid objectiveGrid = gameHandler.getObjectiveGrid();

        // calculate grid bounds
        int gridX, gridY, spaceSize;
        
        int gridSizeWidth = width / objectGrid.getWidth();
        int gridSizeHeight = height / objectGrid.getHeight();

        spaceSize = Math.min(gridSizeWidth, gridSizeHeight);

        int xMarginsSize = width - spaceSize * objectGrid.getWidth();
        int yMarginsSize = height - spaceSize * objectGrid.getHeight();

        gridX = xMarginsSize / 2;
        gridY = yMarginsSize / 2;

        // image buffer
        BufferedImage b = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) b.getGraphics();
        g2.setColor(Globals.BGColor);
        g2.fillRect(0, 0, width, height);

        // print background layer
        for(int i = 0; i < objectGrid.getWidth(); i++) {
            for(int j = 0; j < objectGrid.getHeight(); j++) {
                SPRITEATTACHMENTS sprite = objectiveGrid.getSpace(i, j).getSprite();
                g2.drawImage(Globals.getImage(sprite), gridX + spaceSize*i, gridY + spaceSize*j, spaceSize, spaceSize, null);
            }
        }

        // print front layer
        for(int i = 0; i < objectGrid.getWidth(); i++) {
            for(int j = 0; j < objectGrid.getHeight(); j++) {
                SPRITEATTACHMENTS sprite = objectGrid.getSpace(i, j).getSprite();
                
                if(sprite != SPRITEATTACHMENTS.EMPTY) {
                    if(sprite == SPRITEATTACHMENTS.PLAYER && objectiveGrid.getSpace(i, j).getSprite() == SPRITEATTACHMENTS.PLAYER_TARGET) {
                        sprite = SPRITEATTACHMENTS.PLAYER_COMPLETE;
                    }
                    else if(sprite == SPRITEATTACHMENTS.BOX && objectiveGrid.getSpace(i, j).getSprite() == SPRITEATTACHMENTS.BOX_TARGET) {
                        sprite = SPRITEATTACHMENTS.BOX_COMPLETE;
                    }
                    g2.drawImage(Globals.getImage(sprite), gridX + spaceSize*i, gridY + spaceSize*j, spaceSize, spaceSize, null);
                }
            }
        }

        return b;
    }

    private BufferedImage drawLevelSelect(int width, int height) {
        final int sideBarWidth = (int) (width * 0.2);
        final int horizontalMargin = (int) (width * 0.1);
        final int verticalMargin = (int) (height * 0.1);

        final int sideBarHorizontalMargin = (int) (sideBarWidth * 0.3);
        final int sideBarHorizontalPosition = width - sideBarWidth;

        final int levelIconSize = sideBarWidth - sideBarHorizontalMargin*2;
        final int levelIconSpacing = (int) (levelIconSize * (1.3));
        final int levelIconRootY = (height / 2) - (levelIconSize / 2); 
        final int levelIconRootX = sideBarHorizontalPosition + sideBarWidth/2 - levelIconSize/2;

        final int previewWidth = width - sideBarWidth - horizontalMargin*2;
        final int previewHeight = height - verticalMargin*2;

        BufferedImage previewImage = drawGameBoard(previewWidth, previewHeight);
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) buffer.getGraphics();

        g2.setColor(Globals.BGColor);
        g2.fillRect(0, 0, width, height);
        g2.setColor(Color.black);
        g2.fillRect(sideBarHorizontalPosition, 0, sideBarWidth, height);

        g2.drawImage(previewImage, horizontalMargin, verticalMargin, null);

        g2.setStroke(new BasicStroke(levelIconSize/5));
        g2.setColor(Color.green);
        g2.drawRect(levelIconRootX, levelIconRootY, levelIconSize, levelIconSize);

        for(int level = 0; level <= levelHandler.getHighestLevel(); level++) {
            g2.drawImage(drawIconSquare(level), levelIconRootX, levelIconRootY + (level - levelHandler.getLevel()) * levelIconSpacing, levelIconSize, levelIconSize, null);
        }

        return buffer;
    }

    private BufferedImage drawIconSquare(int level) {
        BufferedImage buffer = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB); //500 x 500
        Graphics2D g2 = (Graphics2D) buffer.getGraphics();
        g2.drawImage(Globals.getImage(SPRITEATTACHMENTS.LEVELICON), 0, 0, null);

        g2.setFont(new Font("Ariel", Font.PLAIN, 200));
        g2.setColor(Color.black);
        
        String text = String.valueOf(level);
        int textWidth = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        
        g2.drawString(text, 250 - textWidth/2, 250 + (5*textHeight/18));

        return buffer;
    }
}

