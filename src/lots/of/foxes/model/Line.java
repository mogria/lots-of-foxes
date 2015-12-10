package lots.of.foxes.model;

import java.io.Serializable;

/**
 * represents a line on the Board
 *
 * @author Moritz
 */
public class Line extends GridElement implements Serializable {

    /**
     * boxes adjacent to this line if this line is at the border of the Board,
     * it only has 1 adjacent box, else it always has 2;
     */
    private transient Box[] adjacentBoxes = new Box[2];

    /**
     * the player who drew this line. null if the line is not yet drewn
     */
    private transient Player owner = null;
    
    /**
     * the number of the player owning this line.
     * 
     * this required for serializing lines and creating the save game
     */
    private int ownerPlayerNum = -1;

    public Line(int lineId, Box adjacentBox1, Box adjacentBox2) {
        super(lineId);
        adjacentBoxes = new Box[2];
        setAdjacentBoxes(adjacentBox1, adjacentBox2);
    }
    
    public Line(int lineId) {
        this(lineId, null, null);
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
     * Get the cached player number of the owner
     * 
     * @return the cached player number of the owner
     */
    public int getOwnerPlayerNum() {
        return ownerPlayerNum;
    }

    /**
     * Set the owner of this line. The Boxes adjacent to this line are notified
     * about this change.
     *
     * @param owner new value of owner
     */
    public boolean setOwner(Player owner) {
        if (owner == null) {
            throw new IllegalArgumentException("cannot reset owner of a line");
        }

        this.owner = owner;
        this.ownerPlayerNum = owner.getPlayerNum();

        boolean ownerSet = false;
        for (Box box : adjacentBoxes) {
            if (box != null) {
                ownerSet = ownerSet || box.addLine(owner);
            }
        }
        
        return ownerSet;
    }

    /**
     * Get the adjacent Boxes
     *
     * @return the adjacent Boxes
     */
    public Box[] getAdjacentBoxes() {
        return adjacentBoxes;
    }
    
    /**
     * Set the adjacent Boxes
     * @param adjacentBox1 the first adjacent box, can be null
     * @param adjacentBox2 the second adjacent box, can be null
     */
    final public void setAdjacentBoxes(Box adjacentBox1, Box adjacentBox2) {
        adjacentBoxes = new Box[2];
        adjacentBoxes[0] = adjacentBox1;
        adjacentBoxes[1] = adjacentBox2;
    }

    /**
     * whether the line is a horizontal line or not
     *
     * @return true if the line is horizontal
     */
    public boolean isHorizontal() {
        return getRow() % 2 == 0;
    }

    /**
     * whether the line is a vertical line or not
     *
     * @return true if the line is vertical
     */
    public boolean isVertical() {
        return !isHorizontal();
    }

    public LineDirection getDirection() {
        return this.getRow() % 2 == 0 ? LineDirection.Horizontal : LineDirection.Vertical;
    }
}
