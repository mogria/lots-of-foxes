package lots.of.foxes.model;

import java.util.Collection;
import java.util.HashMap;

/**
 * Represents the game board.
 * 
 * The coordinate system used on this board can be used the address Boxes
 * as well as lines. There are also invalid coordinates, which would represent
 * the dots on the board. Because points are not required to represent the game
 * board, these are just invalid coordinates.
 * 
 * The layout of this grid looks like this for sizeX = 1, sizeY = 1:
 * 
 *        p(0,0) l(1,0) p(2,0) 
 *           *------------*
 *           |            |
 *     l(0,1)|            |l(1,1)
 *           |  b(1,1)    |
 *           |            |
 *           *------------*
 *        p(0,2) l(1,2) p(2,2) 
 * 
 * the letters before the coordinates (x, y) say what kind of object it is:
 *  
 *  * b - it is a box
 *  * l - it is a line
 *  * p - is is a point (and not represented in this data model)
 * 
 * @author Moritz
 */
public class Board {
    
    /**
     * stores all Boxes on the board
     * the key is generated out of the coordinates by genId()
     */
    private HashMap<Integer, Box> boxes = new HashMap<>();
    
    /**
     * stores all Lines on the board
     * the key is generated out of the coordinates by genId()
     */
    private HashMap<Integer, Line> lines = new HashMap<>();
    
    /**
     * the size of the grid in the x dimension
     */
    private int gridSizeX;
    
    /**
     * the size of the grid in the y dimension
     */
    private int gridSizeY;
    
    /**
     * Creates a game board, all the boxes & lines
     * 
     * @param sizeX the number of boxes on the board in the X coordinate
     * @param sizeY the number of boxes on the board in the Y coordinate
     */
    public Board(int sizeX, int sizeY) {
        this.gridSizeX = sizeX * 2 + 1;
        this.gridSizeY = sizeY * 2 + 1;
        for(int i = 0; i < sizeX * sizeY; i++) {
            boxes.put(1 + i * 2, new Box());
        }
        
        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                boxes.put(genId(sizeX * 2 + 1, sizeY * 2 + 1), new Box());
            }
        }
        
        for(int x = 0; x < gridSizeX; x++) {
            for(int y = 0; y < gridSizeY; y++) {
                if(isLineCoordinate(x, y)) { // valid line coordinate
                    Box box1;
                    Box box2;
                    
                    if(y % 2 == 0) { // is an horizontal line
                        box1 = getBoxByCoordinate(x, y - 1);
                        box2 = getBoxByCoordinate(x, y + 1);
                    } else { // is an vertical line
                        box1 = getBoxByCoordinate(x - 1, y);
                        box2 = getBoxByCoordinate(x + 1, y);
                    }
                    Line line = new Line(genId(x, y), box1, box2);
                    lines.put(line.getLineId(), line);
                }
            }
        }
    }
    
    /**
     * generates an unique id out of the two coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     * @return an unique id
     */
    private int genId(int x, int y) {
        return (x & 0xFFFF) << 16 | y & 0xFFFF + 1;
    }
    
    /**
     * checks whether x and y are valid coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if valid coordinates are passed
     */
    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && y >= 0 && x < gridSizeX && y < gridSizeY;
    }
    
    /**
     * checks whether x and y are coordinates for a line
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if x anx y are line coordinates
     */
    private boolean isLineCoordinate(int x, int y) {
        return isValidCoordinate(x, y) && (x + y) % 2 == 1;
    }
    
    /**
     * checks whether x and y are coordinates for a box
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if x and y are box coordinates
     */
    private boolean isBoxCoordinate(int x, int y) {
        return isValidCoordinate(x, y) && x % 2 == 1 && y % 2 == 1;
    }
    
    /**
     * get a Box object by coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     * @return a Box object, null if x and y are not Box coordinates
     */
    private Box getBoxByCoordinate(int x, int y) {
        return isBoxCoordinate(x, y) ? boxes.get(genId(x, y)) : null;
    }
    
    /**
     * get all the line objects
     * @return all the lines object as a collection
     */
    public Collection<Line> getLines() {
        return lines.values();
    }
    
    /**
     * assign a line to a player by lineId
     * if the line has already been played false is returned
     * @param player
     * @param lineId 
     * @return true if successful, false if line is already played
     */
    public boolean playLine(Player player, int lineId) {
        Line line = lines.get(lineId);
        return playLine(player, line);
    }
    
    /**
     * assign a line to a player
     * if the line has already been played false is returned
     * @param player
     * @param lineId 
     * @return true if successful, false if line is already played
     */
    public boolean playLine(Player player, Line line) {
        if(line == null) return false;
        if(line.getOwner() != null) return false;
        
        line.setOwner(player);
        return true;
    }
    
    /**
     * whether all lines on the board are occupied
     * @return true if all lines are used, false if not
     */
    public boolean isBoardFull() {
        return lines.values().stream().allMatch(line -> line.getOwner() != null);
    }
}