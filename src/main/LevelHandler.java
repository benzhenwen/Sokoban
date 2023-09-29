package main;

public class LevelHandler {

    // instance vars
    private int currentLevel;

    // constructors
    public LevelHandler() {
        currentLevel = 0;
    }

    public LevelHandler(int l) {
        currentLevel = l;
    }

    // methods
    public void incrementLevel() {
        if(currentLevel < Globals.getLevelStringsLength() - 1) {
            currentLevel++;
        }
    }

    public void decrementLevel() {
        if(currentLevel > 0) {
            currentLevel--;
        }
    }

    public int getLevel() {
        return currentLevel;
    }

    public void setLevel(int l) {
        if(l >= 0 && l < Globals.getLevelStringsLength()) {
            currentLevel = l;
        }
    }

    public String getString() {
        return Globals.getLevelString(currentLevel);
    }

    public int getHighestLevel() {
        return Globals.getLevelStringsLength() - 1;
    }
}
