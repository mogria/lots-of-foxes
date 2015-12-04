package lots.of.foxes.model;

/**
 * represents a line on the Board
 *
 * @author Moritz
 */
public class Line {

    /**
     * unique id of this line
     */
    private int lineId;

    /**
     * boxes adjacent to this line if this line is at the border of the Board,
     * it only has 1 adjacent box, else it always has 2;
     */
    private Box[] adjacentBoxes = new Box[2];

    /**
     * the player who drew this line. null if the line is not yet drewn
     */
    private Player owner = null;

    public Line(int lineId, Box adjacentBox1, Box adjacentBox2) {
        if (adjacentBox1 == null && adjacentBox2 == null) {
            throw new IllegalArgumentException("a line must at least have 1 adjacent box");
        }
        adjacentBoxes[0] = adjacentBox1;
        adjacentBoxes[1] = adjacentBox2;
    }

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
     * Set the owner of this line. The Boxes adjacent to this line are notified
     * about this change.
     *
     * @param owner new value of owner
     */
    public void setOwner(Player owner) {
        if (owner == null) {
            throw new IllegalArgumentException("cannot reset owner of a line");
        }

        this.owner = owner;

        for (Box box : adjacentBoxes) {
            if (box != null) {
                box.addLine(owner);
            }
        }
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
     * get the row this line lies in the grid.
     *
     * @return the row this line lies in the grid.
     */
    public int getRow() {
        return lineId & 0xFFFF;
    }

    /**
     * get the column this line lies in the grid.
     *
     * @return the column this line lies in the grid.
     */
    public int getColumn() {
        return (lineId >> 16) & 0xFFFF;
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
        return this.getRow() % 2 == 0 ? LineDirection.Vertical : LineDirection.Horizontal;
    }
}
