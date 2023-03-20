package maze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Student implementation of the maze.
 *
 * @author Tiffany Lee
 */
public class Maze implements IMaze {
    private final Node[][] graph;
    private final int rows;
    private final int columns;
    private final Coordinates home;
    private final List<Treasure> treasures = new ArrayList<>();

    /**
     * Create a new maze from a file.
     * @param filename the name of the maze file
     * @throws IOException if a problem is encountered reading the file
     */
    public Maze(String filename) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            // first line is: rows cols
            String[] dimensions = in.readLine().split("\\s+");
            // e.g. to read number of rows: Integer.parseInt(fields[0]);
            this.rows = Integer.parseInt(dimensions[0]);
            // e.g. to read number of columns: Integer.parseInt(fields[1]);
            this.columns = Integer.parseInt(dimensions[1]);
            graph = new Node[rows][columns];
            for(int r = 0; r < rows; r++){
                String[] row = in.readLine().split("\\s+");
                for(int c = 0; c < columns; c++){
                    graph[r][c] = new Node(new Coordinates(r,c));
                    for(char neighbor : row[c].toCharArray()){
                        String ch = String.valueOf(neighbor);
                        switch(ch){
                            case NORTH -> graph[r][c].addNeighbor(new Coordinates(r-1, c));
                            case SOUTH -> graph[r][c].addNeighbor(new Coordinates(r+1, c));
                            case EAST -> graph[r][c].addNeighbor(new Coordinates(r, c+1));
                            case WEST -> graph[r][c].addNeighbor(new Coordinates(r, c-1));
                        }
                    }
                }
            }
            String[] start = in.readLine().split("\\s+");
            this.home = new Coordinates(start[1]);
            graph[home.row()][home.col()].setName(start[0]);

            String totalTreasures = in.readLine();
            int numTreasures = Integer.parseInt(totalTreasures);

            for(int t = 0; t < numTreasures; t++){
                String[] treasure = in.readLine().split("\\s+");
                Coordinates loc = new Coordinates(treasure[1]);
                graph[loc.row()][loc.col()].setName(treasure[0]);
                this.treasures.add(new Treasure(treasure[0], loc));
            }
        } // any exceptions generated will get thrown to the main program
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getCols() {
        return columns;
    }

    @Override
    public boolean hasCoordinates(Coordinates location) {
        return location.row() <= rows && location.col() <= columns;
    }

    @Override
    public boolean isNeighbor(Coordinates src, Coordinates dest) {
        for(Coordinates cell : graph[src.row()][src.col()].getNeighbors()){
            if(cell.row() == dest.row() && cell.col() == dest.col()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getCell(Coordinates location) {
        for(Treasure treasure : treasures){
            if(treasure.getLocation().equals(location) && treasure.isCollected()){
                graph[location.row()][location.col()].setName(".");
                return graph[location.row()][location.col()].getName();
            }
        }
        return graph[location.row()][location.col()].getName();
    }

    @Override
    public Coordinates getHome() {
        return home;
    }

    @Override
    public boolean hasTreasure(Coordinates location) {
        return graph[location.row()][location.col()].getName().matches("^[A-Z].*");
    }

    @Override
    public Collection<Treasure> getTreasures() {
        return this.treasures;
    }

    @Override
    public Collection<Coordinates> getNeighbors(Coordinates location) {
        return graph[location.row()][location.col()].getNeighbors();
    }
}