package maze;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * CSCI-142 Computer Science 2 Recitation Presentation
 * 08-GraphIntro
 * The Shortest Path
 *
 * This is the representation of a node in a graph.  A node is composed of a
 * unique name, and a list of neighbor Node's.
 *
 * @author RIT CS
 */
public class Node {
    /** The name, or treasure, associated with this node */
    private String name = "."; // empty(.), home(*), treasure(A, B, C, ...)
    /** The location associated with this node */
    private final Coordinates location;
    /** Neighbors of this node are stored as a set (adjacency list) */
    private final Set<Coordinates> neighbors;

    /**
     * Constructor initializes Node with the inputted location
     * and an empty list of neighbors.
     */
    public Node(Coordinates location) {
        this.location = location;
        this.neighbors = new LinkedHashSet<>();
    }

    /**
     * Get the String name associated with this object.
     *
     * @return name.
     */
    public String getName() {
        return name;
    }

    /** Sets the name associated with this object. */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method to return the adjacency list for this node containing all
     * of its neighbors.
     *
     * @return the list of neighbors of the given node
     */
    public Collection<Coordinates> getNeighbors() {
        return Collections.unmodifiableSet(this.neighbors);
    }

    /**
     * Add a neighbor to this node. Checks if already present, and does not
     * duplicate in this case.
     */
    public void addNeighbor(Coordinates coordinate) {
        if(!neighbors.contains(coordinate)) neighbors.add(coordinate);
    }

    /**
     * Method to generate a string associated with the node, including the
     * name of the node followed by its location and the
     * locations of its neighbors.
     * Overrides Object toString method.
     *
     * @return string associated with the node.
     */
    @Override
    public String toString() {
        String result = this.name + ": ";
        for (Coordinates neighbor: this.neighbors) {
            result = location.toString() + "\nNeighbors: " +
                    neighbor.toString();
        }
        return result;
    }

    /**
     *  Two Nodes are equal if they have the same name.
     *
     *  @param other The other object to check equality with
     *  @return true if equal; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof Node n) {
            result = this.name.equals(n.name);
        }
        return result;
    }

    /**
     * The hash code of a Node is just the hash code of the name, since no
     * two nodes can have the same name, and it is consistent with equals()
     */
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
