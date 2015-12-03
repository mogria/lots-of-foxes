package lots.of.foxes.model;

/**
 *
 * @author Moritz
 */
public class Line {
    
    /**
     * unique id of this line
     */
    private int lineId;
    
    
    /**
     * boxes adjacent to this line
     * if this line is at the border of the Board, it only has 1 adjacent box,
     * else it always has 2;
     */
    private Box[] adjacentBoxes = new Box[2];
        
    /**
     * the player who drew this line.
     * null if the line is not yet drewn
     */
    private Player owner = null;

    /**
     * Get the value of lineId
     *
     * @return the value of lineId
     */
    public int getLineId() {
        return lineId;
    }

    /**
     * Get the value of owner
     *
     * @return the value of owner
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Set the value of owner
     *
     * @param owner new value of owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }
    

    /**
     * Get the value of adjacentBoxes
     *
     * @return the value of adjacentBoxes
     */
    public Box[] getAdjacentBoxes() {
        return adjacentBoxes;
    }



}
