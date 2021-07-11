/**
 *
 */
package automata;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


/**
 * Conway's Game of Life on a 10x10 grid.
 *
 * @author Sam Wu
 *
 */
public class GameOfLife {
    private int LENGTH = 10;
    private BooleanProperty[][] grid;

    public GameOfLife() {
        // set 10 x 10 2D array to false
        grid = new BooleanProperty[LENGTH][LENGTH];
        for (int i = 0; i < LENGTH; ++i) {
            for (int j = 0; j < LENGTH; ++j) {
                BooleanProperty p = new SimpleBooleanProperty();
                p.set(false);
                grid[i][j] = p;
            }
        }
    }

    public void ensureAlive(int x, int y) {
        grid[y][x].set(true);
    }

    public void ensureDead(int x, int y) {
        grid[y][x].set(false);
    }

    public boolean isAlive(int x, int y) {
        return grid[y][x].getValue();
    }

    public BooleanProperty cellProperty(int x, int y) {
        return grid[y][x];
    }

    /**
     * One tick of the game, progresses one generation
     * */
    public void tick() {
        // temp arr storing grid, not pointing to reference
        BooleanProperty[][] temp = new BooleanProperty[LENGTH][LENGTH];

        for (int i = 0; i < LENGTH; ++i) {
            for (int j = 0; j < LENGTH; ++j) {
                BooleanProperty p = new SimpleBooleanProperty();
                p.set(grid[i][j].getValue());
                temp[i][j] = p;
            }
        }

        // loop through temp and determine if alive or dead
        for (int y = 0; y < LENGTH; ++y) {
            for (int x = 0; x < LENGTH; ++x) {
                // get number of neighbours
                int neighbours = temp[y][x].getValue() ? -1 : 0;

                for (int nY = y - 1; nY < y + 2; ++nY) {
                    for (int nX = x - 1; nX < x + 2; ++nX) {
                        if (temp[((nY % 10) + 10) % 10][((nX % 10) + 10) % 10].getValue())
                            neighbours++;
                    }
                }

                // cell is alive if was dead and has 3 neighbours
                if (!temp[y][x].getValue() && neighbours == 3) {
                    ensureAlive(x, y);
                // cell is alive if was alive and has 2 or 3 neighbours
                } else if (temp[y][x].getValue() && neighbours < 4 && neighbours > 1) {
                    ensureAlive(x, y);
                } else {
                    ensureDead(x, y);
                }
            }
        }
    }
}
