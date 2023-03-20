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
 * @author YOUR NAME HERE
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
    public TreasureHunt(String filename) throws IOException {
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
        Coordinates end = new Coordinates(maze.getRows(), maze.getCols());
        int steps = 1;

        List<Coordinates> path = new LinkedList<>();
        LinkedList<Coordinates> queue = new LinkedList<>();
        queue.add(start);

        Map<Coordinates, Collection<Coordinates>> predecessors = new HashMap<>();

        while(!queue.isEmpty()){
            Coordinates current = queue.remove();
            if(current == end){
                break;
            } else if(maze.hasTreasure(current)){
                System.out.println("Collecting: " + maze.getCell(current) + current);
                break;
            }
            for(Coordinates nbr : maze.getNeighbors(current)){
                if(!predecessors.containsKey(nbr)){
                    predecessors.put(nbr, maze.getNeighbors(current));
                    queue.add(nbr);
                }
            }
        }

        if(predecessors.containsKey(end)){
            Coordinates curr = end;
            while(curr != start){
                path.add(curr);
                Collection<Coordinates> neighbors = predecessors.get(curr);
                curr = start;
                steps++;
            }
            path.add(curr);
        }
        System.out.println("Path: " + path);
        System.out.println("Steps: " + (steps + steps-1));
    }

//    for(int r = 0; r < maze.getRows(); r++){
//        for(int c = 0; c < maze.getCols(); c++){
//            Coordinates loc = new Coordinates(r, c);
//            if(maze.hasTreasure(current.getLocation())){
//
//            }
//        }
//    }

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
            } catch (IOException ioe) {
                System.err.println(ioe.getMessage());
            }
        }
    }
}
