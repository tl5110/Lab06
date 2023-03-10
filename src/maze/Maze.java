package maze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Student implementation of the maze.
 *
 * @author YOUR NAME HERE
 */
public class Maze implements IMaze {
    // TODO

    /**
     * Create a new maze from a file.
     * @param filename the name of the maze file
     * @throws IOException if a problem is encountered reading the file
     */
    public Maze(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            // first line is: rows cols
            String line = in.readLine();
            String[] fields = line.split("\\s+");
            // e.g. to read number of rows: Integer.parseInt(fields[0]);
            // e.g. to read number of columns: Integer.parseInt(fields[1]);

            // TODO
        } // any exceptions generated will get thrown to the main program
    }

    @Override
    public int getRows() {
        // TODO
        return 0;
    }

    @Override
    public int getCols() {
        // TODO
        return 0;
    }

    @Override
    public boolean hasCoordinates(Coordinates location) {
        // TODO
        return false;
    }

    @Override
    public boolean isNeighbor(Coordinates src, Coordinates dest) {
        // TODO
        return false;
    }

    @Override
    public String getCell(Coordinates location) {
        // TODO
        return null;
    }

    @Override
    public Coordinates getHome() {
        // TODO
        return null;
    }

    @Override
    public boolean hasTreasure(Coordinates location) {
        // TODO
        return false;
    }

    @Override
    public Collection<Treasure> getTreasures() {
        // TODO
        return new ArrayList<>();
    }

    @Override
    public Collection<Coordinates> getNeighbors(Coordinates location) {
        // TODO
        return new ArrayList<>();    }
}
