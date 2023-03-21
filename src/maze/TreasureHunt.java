package maze;

import java.io.IOException;
import java.util.*;

/**
 * The main program for the treasure hunt.  It is run on the command
 * line with the maze file, e.g.:
 *
 * $ java TreasureHunt data/maze-3.txt
 *
 *
 * @author RIT CS
 * @author Tiffany Lee
 */
public class TreasureHunt {
    /** the maze */
    private final IMaze maze;

    /**
     * Create the instance and construct the maze from the file.
     *
     * @param filename the maze file
     * @throws IOException if there is a problem reading the file
     */
    public TreasureHunt(String filename) throws IOException, MazeException{
        this.maze = new Maze(filename);
        this.maze.display();
    }


    /**
     * Find all the treasures in the maze. For each treasure,
     * find the shortest path from the home position to the treasure
     * position and collect the treasure. While collecting
     * treasures, the total number of steps taken to collect
     * each treasure and return it home is computed.
     */
    public void findTreasures() {
        Coordinates start = maze.getHome();
        int totalSteps = 0;

        LinkedList<Coordinates> queue = new LinkedList<>();
        queue.add(start);

        for(Treasure treasure : maze.getTreasures()){
            System.out.println("Collecting: " + treasure);
            Map<Coordinates, Coordinates> predecessors = new HashMap<>();
            Coordinates end = treasure.getLocation();
            // BFS
            while(!queue.isEmpty()){
                Coordinates current = queue.remove();
                Treasure cell = new Treasure(maze.getCell(current), current);
                if(current == end){
                    break;
                } else if (treasure.equals(cell)){
                    treasure.collect();
                    maze.getCell(current);
                }
                for(Coordinates nbr : maze.getNeighbors(current)){
                    if(!predecessors.containsKey(nbr)){
                        predecessors.put(nbr, current);
                        queue.add(nbr);
                    }
                }
            }
            // Path Construction
            List<Coordinates> path = new LinkedList<>();
            int steps = 0;
            if(predecessors.containsKey(end)){
                Coordinates curr = end;
                steps += 1;
                while(curr != start){
                    path.add(0, curr);
                    steps += 2;
                    curr = predecessors.get(curr);
                }
                path.add(0, curr);
            }
            //Printing Results
            if(path.isEmpty()){
                System.out.println("\tNo path!");
            } else {
                System.out.println("\tPath: " + path);
                System.out.println("\tSteps: " + steps);
                totalSteps += steps;
            }
            maze.display();
            queue.add(start);
        }
        System.out.println("Total Steps: " + totalSteps);
    }

    /**
     * The main method.
     *
     * @param args command line arguments (maze file)
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java TreasureHunt maze-file");
        } else {
            try {
                // construct the maze
                TreasureHunt treasureHunt = new TreasureHunt(args[0]);
                // perform searches over our maze
                treasureHunt.findTreasures();
            } catch (IOException | MazeException ioe) {
                System.err.println(ioe.getMessage());
            }
        }
    }
}
