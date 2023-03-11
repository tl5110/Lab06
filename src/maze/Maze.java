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
    private Node[][] graph;

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
            int rows = Integer.parseInt(fields[0]);
            // e.g. to read number of columns: Integer.parseInt(fields[1]);
            int columns = Integer.parseInt(fields[1]);
            graph = new Node[rows][columns];
            for(int r = 0; r < rows; r++){
                String[] row = in.readLine().split("\\s+");
                for(int c = 0; c < rows; c++){
                    graph[r][c] = new Node(new Coordinates(r,c));
                    if(row[c].contains(NORTH)){
                        graph[r][c].addNeighbor(new Coordinates(r-1, c));
                    } else if(row[r].contains(IMaze.SOUTH)){
                        graph[r][c].addNeighbor(new Coordinates(r+1, c));
                    } else if(row[r].contains(IMaze.EAST)){
                        graph[r][c].addNeighbor(new Coordinates(r, c+1));
                    } else if(row[r].contains(IMaze.WEST)){
                        graph[r][c].addNeighbor(new Coordinates(r, c-1));
                    }
                }
            }
            String[] start = in.readLine().split("\\s+");
            Coordinates s = new Coordinates(start[1]);

//            int numTreasures = in.readLine();


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
