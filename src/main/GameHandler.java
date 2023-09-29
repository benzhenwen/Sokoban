package main;

import main.Globals.PROPERTY;
import main.Globals.SPRITEATTACHMENTS;


/* load string is formatted as follows:
 * 
 * there are 2 major sections divided by a "#". The first major section represents the objects on the board
 * e - empty space
 * w - wall
 * b - pushable box
 * p - the player object. there should only be one of these.
 * 
 * the second major section on the opther side of the "#" represents the objectives for the player to complete
 * e - empty
 * b - target for a box to cover
 * p - target for the player to cover
 * 
 * each section is formatted identically using the characters left to right as a space, and a "/" to represent a new line.
 * 
 * for example, a 5x5 area surrounded by walls, a player at (1, 1), and a player objective in the center would look like:
 * 
 * "wwwww/wpeew/weeew/weeew/wwwww#eeeee/eeeee/eefee/eeeee/eeeee"
 * |----------section 1----------|----------section 2----------|
 * 
 * unidentified characters will be interpreted as an "e"
 */


public class GameHandler {
    
    // instance vars
    private Grid objectGrid;
    private Grid objectiveGrid;

    // constructors
    public GameHandler(String loadString) {
        loadBoard(loadString);
    }

    public GameHandler() {

    }

    // methods
    public boolean loadBoard(String loadString) {
        int width = loadString.split("#")[0].indexOf("/");
        int height = loadString.split("#")[0].split("/").length;

        String[] objectlines = loadString.split("#")[0].split("/");
        String[] objectiveLines = loadString.split("#")[1].split("/");

        try {
            objectGrid = new Grid(width, height);

            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    char c = objectlines[i].charAt(j);

                    switch(c) {
                        case 'e':
                            objectGrid.setSpace(j, i, new GridObject());
                            break;
                        case 'w':
                            objectGrid.setSpace(j, i, new GridObject(true, false, SPRITEATTACHMENTS.WALL));
                            break;
                        case 'b':
                            objectGrid.setSpace(j, i, new GridObject(true, true, SPRITEATTACHMENTS.BOX));
                            break;
                        case 'p':
                            objectGrid.setSpace(j, i, new GridObject(false, false, PROPERTY.PLAYER, SPRITEATTACHMENTS.PLAYER));
                            break;
                        default:
                            objectGrid.setSpace(j, i, new GridObject());
                    }
                }
            }
            
            objectiveGrid = new Grid(width, height);

            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    char c = objectiveLines[i].charAt(j);

                    switch(c) {
                        case 'e':
                            objectiveGrid.setSpace(j, i, new GridObject());
                            break;
                        case 'b':
                            objectiveGrid.setSpace(j, i, new GridObject(PROPERTY.BOX_TARGET, SPRITEATTACHMENTS.BOX_TARGET));
                            break;
                        case 'p':
                            objectiveGrid.setSpace(j, i, new GridObject(PROPERTY.PLAYER_TARGET, SPRITEATTACHMENTS.PLAYER_TARGET));
                            break;
                        default:
                            objectiveGrid.setSpace(j, i, new GridObject());
                    }
                }
            }

            return true;
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return false;
    }

    // attemps to move the player character, returns true if the move was executed or false if blocked
    public boolean move(int xOffset, int yOffset) {
        Coordinate playerPos = getPropertyPos(objectGrid, PROPERTY.PLAYER);

        int i;
        for(i = 1; true; i++) {
            int nextX = playerPos.x + xOffset*i;
            int nextY = playerPos.y + yOffset*i;

            if(nextX < 0 || nextX >= objectGrid.getWidth() || nextY < 0 || nextY >= objectGrid.getHeight()) {
                return false;
            }

            GridObject nextGridObject = objectGrid.getSpace(nextX, nextY);

            if(nextGridObject.getInteractable() == false) {
                break;
            }
            else if(nextGridObject.getPushable() == false) {
                return false;
            }
        }

        for(; i > 0; i--) {
            int thisX = playerPos.x + xOffset*i;
            int thisY = playerPos.y + yOffset*i;

            int nextX = playerPos.x + xOffset*(i-1);
            int nextY = playerPos.y + yOffset*(i-1);

            objectGrid.setSpace(thisX, thisY, objectGrid.getSpace(nextX, nextY));
        }

        objectGrid.setSpace(playerPos.x, playerPos.y, new GridObject());

        return true;
    }

    public boolean checkWinState() {
        for(int i = 0; i < objectiveGrid.getWidth(); i++) {
            for(int j = 0; j < objectiveGrid.getHeight(); j++) {
                if(objectiveGrid.getSpace(i, j).getProperty() == PROPERTY.BOX_TARGET && objectGrid.getSpace(i, j).getInteractable() == false) {
                    return false;
                }
                if(objectiveGrid.getSpace(i, j).getProperty() == PROPERTY.PLAYER_TARGET && objectGrid.getSpace(i, j).getProperty() != PROPERTY.PLAYER) {
                    return false;
                }
            }
        }

        return true;
    }

    public Grid getObjectGrid() {
        return objectGrid;
    }

    public Grid getObjectiveGrid() {
        return objectiveGrid;
    }

    private Coordinate getPropertyPos(Grid g, PROPERTY property) {
        for(int i = 0; i < g.getWidth(); i++) {
            for(int j = 0; j < g.getHeight(); j++) {
                if(g.getSpace(i, j).getProperty() == property) {
                    return new Coordinate(i, j);
                }
            }
        }

        return new Coordinate(-1, -1);
    }

}
