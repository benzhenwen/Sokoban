package main;

public class Grid{

    // instance vars
    private GridObject[][] grid;

    // constructors
    public Grid(int initWidth, int initHeight) {
        grid = new GridObject[initHeight][initWidth];
    }

    // methods
    public void setSpace(int x, int y, GridObject g) {
        grid[y][x] = g;
    }

    public GridObject[][] getGrid() {
        return grid;
    }

    public GridObject getSpace(int x, int y) {
        return grid[y][x];
    }

    public int getWidth() {
        return grid[0].length;
    }

    public int getHeight() {
        return grid.length;
    }

}
