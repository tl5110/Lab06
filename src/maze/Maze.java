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
    /** maze(graph) is represented using a 2D-Node */
    private final Node[][] graph;
    /** total number of rows in the maze */
    private final int rows;
    /** total number of rows in the maze */
    private final int columns;
    /** coordinates, or location of home */
    private final Coordinates home;
    /** list of all the treasures in the maze */
    private final List<Treasure> treasures = new ArrayList<>();

    /**
     * Create a new maze from a file.
     * @param filename the name of the maze file
     * @throws IOException if a problem is encountered reading the file
     */
    public Maze(String filename) throws IOException, MazeException{
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            // first line is: rows cols
            String[] dimensions = in.readLine().split("\\s+");
            // e.g. to read number of rows: Integer.parseInt(fields[0]);
            this.rows = Integer.parseInt(dimensions[0]);
            // e.g. to read number of columns: Integer.parseInt(fields[1]);
            this.columns = Integer.parseInt(dimensions[1]);
            if(rows <= 0){
                throw new MazeException("rows must be greater than zero: " + rows);
            } else if(columns <= 0){
                throw new MazeException("columns must be greater than zero: " + columns);
            }
            graph = new Node[rows][columns];
            for(int r = 0; r < rows; r++){
                String[] row = in.readLine().split("\\s+");
                for(int c = 0; c < columns; c++){
                    graph[r][c] = new Node(new Coordinates(r,c));
                    for(char neighbor : row[c].toCharArray()){
                        String ch = String.valueOf(neighbor);
                        switch(ch){
                            case NORTH -> {
                                if((r-1) < 0){
                                    throw new MazeException
                                            ("cannot have a north neighbor: " + new Coordinates(r, c));
                                }
                                graph[r][c].addNeighbor(new Coordinates(r-1, c));
                            }
                            case SOUTH -> {
                                if((r+1) >= this.rows){
                                    throw new MazeException
                                            ("cannot have a south neighbor: " + new Coordinates(r, c));
                                }
                                graph[r][c].addNeighbor(new Coordinates(r+1, c));
                            }
                            case EAST -> {
                                if((c+1) >= this.columns){
                                    throw new MazeException
                                            ("cannot have a east neighbor: " + new Coordinates(r, c));
                                }
                                graph[r][c].addNeighbor(new Coordinates(r, c+1));
                            }
                            case WEST -> {
                                if((c-1) < 0){
                                    throw new MazeException
                                            ("cannot have a west neighbor: " + new Coordinates(r, c));
                                }
                                graph[r][c].addNeighbor(new Coordinates(r, c-1));
                            }
                        }
                    }
                }
            }
            String[] start = in.readLine().split("\\s+");
            if(start.length < 2){
                throw new MazeException("home position not specified!");
            }
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

    /**
     * Get the number of rows in the maze.
     *
     * @return number of rows
     */
    @Override
    public int getRows() {
        return rows;
    }

    /**
     * Get the number of columns in the maze.
     *
     * @return number of columns
     */
    @Override
    public int getCols() {
        return columns;
    }

    /**
     * Is a location in the maze valid or not?
     *
     * @param location the location
     * @return whether the cell is valid or not
     */
    @Override
    public boolean hasCoordinates(Coordinates location) {
        return location.row() <= rows && location.col() <= columns;
    }

    /**
     * Is dest a neighbor of src in the maze?
     *
     * @param src the source cell
     * @param dest the destination cell
     * @return whether they are neighbors or not
     */
    @Override
    public boolean isNeighbor(Coordinates src, Coordinates dest) {
        for(Coordinates cell : graph[src.row()][src.col()].getNeighbors()){
            if(cell.row() == dest.row() && cell.col() == dest.col()){
                return true;
            }
        }
        return false;
    }

    /**
     * Get the string value of the cell at a location in the maze.
     *
     * @param location the location
     * @return the string at that location
     */
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

    /**
     * Get the home position (must exist).
     *
     * @return home position
     */
    @Override
    public Coordinates getHome() {
        return home;
    }

    /**
     * Is there a treasure at a specific location?
     *
     * @param location the location
     * @return whether there is a treasure at this location or not
     */
    @Override
    public boolean hasTreasure(Coordinates location) {
        return graph[location.row()][location.col()].getName().matches("^[A-Z].*");
    }

    /**
     * Get the collection of all treasures in the maze.
     *
     * @return the treasure collection
     */
    @Override
    public Collection<Treasure> getTreasures() {
        return this.treasures;
    }

    /**
     * Get the neighbors of a cell in the maze.
     *
     * @param location the cell
     * @return the cell's neighbors
     */
    @Override
    public Collection<Coordinates> getNeighbors(Coordinates location) {
        return graph[location.row()][location.col()].getNeighbors();
    }
}