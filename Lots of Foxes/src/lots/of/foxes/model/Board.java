package lots.of.foxes.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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
public final class Board {
    
    /**
     * stores all Boxes on the board
     * the key is generated out of the coordinates by genId()
     */
    final private HashMap<Integer, Box> boxes = new HashMap<>();
    
    /**
     * stores all Lines on the board
     * the key is generated out of the coordinates by genId()
     */
    final private HashMap<Integer, Line> lines = new HashMap<>();
    
    /**
     * the size of the grid in the x dimension
     */
    final private int sizeX;
    
    /**
     * the size of the grid in the y dimension
     */
    final private int sizeY;
    
    /**
     * whether the last move caused a box to be filled 
     */
    private boolean boxFilled = false;
    
    /**
     * the two players playing on this board
     */
    private Player[] players = new Player[2];
    
    /**
     * Creates a game board, all the boxes & lines
     * 
     * @param sizeX the number of boxes on the board in the X coordinate
     * @param sizeY the number of boxes on the board in the Y coordinate
     */
    public Board(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        final int gridSizeX = getGridSizeX();
        final int gridSizeY = getGridSizeY();
        
        setPlayer(new Player(0, "Player 1", Color.red));
        setPlayer(new Player(1, "Player 2", Color.blue));
        
        for(int x = 0; x < sizeX; x++) {
            for(int y = 0; y < sizeY; y++) {
                Box box = new Box(genId(x * 2 + 1, y * 2 + 1));
                boxes.put(box.getId(), box);
            }
        }
        
        for(int x = 0; x < gridSizeX; x++) {
            for(int y = 0; y < gridSizeY; y++) {
                if(isLineCoordinate(x, y)) {
                    Line line = new Line(genId(x, y));
                    lines.put(line.getId(), line);
                    setAdjacentBoxesForLine(line);
                }
            }
        }
    }
    
    /**
     * Create a board out of a collection of played lines
     * 
     * @param sizeX the number of boxes on the board in the X coordinate
     * @param sizeY the number of boxes on the board in the Y coordinate
     * @param player1 a player
     * @param player2 a player
     * @param playedLines a collection of lines already played. The owner needs to be set correctly.
     */
    public Board(int sizeX, int sizeY, Player player1, Player player2, Collection<Line> playedLines) {
        this(sizeX, sizeY);
        setPlayer(player1);
        setPlayer(player2);
        playedLines.stream().forEach(line ->  {
            setAdjacentBoxesForLine(line);
            lines.put(line.getId(), line);
            playLine(getPlayer(line.getOwnerPlayerNum()), line);
        });
        boxFilled = false;
    }
    
    /**
     * generates an unique id out of the two coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     * @return an unique id
     */
    private int genId(int x, int y) {
        return (x & 0xFFFF) << 16 | y & 0xFFFF;
    }
    
    /**
     * checks whether x and y are valid coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if valid coordinates are passed
     */
    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && y >= 0 && x < getGridSizeX() && y < getGridSizeY();
    }
    
    /**
     * Helper method to determine which boxes are adjacent to a line.
     * These boxes are then added as to the line object itself.
     * @param line the line to set the adjacent boxes for
     */
    private void setAdjacentBoxesForLine(Line line) {
        Box box1;
        Box box2;

        final int x = line.getColumn();
        final int y = line.getRow();
        if(line.isHorizontal()) { // is an horizontal line
            box1 = getBoxByCoordinate(x, y - 1);
            box2 = getBoxByCoordinate(x, y + 1);
        } else { // is an vertical line
            box1 = getBoxByCoordinate(x - 1, y);
            box2 = getBoxByCoordinate(x + 1, y);
        }
        
        line.setAdjacentBoxes(box1, box2);
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
    public Box getBoxByCoordinate(int x, int y) {
        return isBoxCoordinate(x, y) ? boxes.get(genId(x, y)) : null;
    }
    
    /**
     * get a Line object by coordinates
     * @param x the x coordinate
     * @param y the y coordinate
     * @return a Box object, null if x and y are not Box coordinates
     */
    public Line getLineByCoordinate(int x, int y) {
        return isLineCoordinate(x, y) ? lines.get(genId(x, y)) : null;
    }
    
    /**
     * get all the Box objects
     * @return a Collection of Box objects
     */
    public Collection<Box> getBoxes() {
        return boxes.values();
    }
    
    /**
     * get all the Line objects
     * @return all the lines object as a collection
     */
    public Collection<Line> getLines() {
        return lines.values();
    }
    
    public Collection<GridElement> getPointCoordinates() {
        List<GridElement> list = new ArrayList<>();
        for(int x = 0; x < getGridSizeX(); x += 2) {
            for(int y = 0; y < getGridSizeY(); y += 2) {
                list.add(new GridElement(genId(x, y)));
            }
        }
        return list;
    }
    
    /**
     * returns the number of boxes in the x dimension
     * @return returns the number of boxes  in the x dimension
     */   
    public int getSizeX() {
        return sizeX;
    }
    
    
    /**
     * returns the number of boxes in the y dimension
     * @return returns the number of boxes  in the y dimension
     */   
    public int getSizeY() {
        return sizeY;
    }
    
    /**
     * returns the grid size in the x dimension
     * @return the grid size in the x dimension
     */
    public int getGridSizeX() {
        return sizeX * 2 + 1;
    }

    /**
     * returns the grid size in the y dimension
     * @return the grid size in the y dimension
     */
    public int getGridSizeY() {
        return sizeY * 2 + 1;
    }
      
    /**
     * Get the value of players
     *
     * @return the value of players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Get the value of players at specified index
     *
     * @param index the index of players
     * @return the value of players at specified index
     */
    public Player getPlayer(int playerNum) {
        return this.players[playerNum];
    }

    /**
     * Set a player object

     * @param player set a new player, the player with the same Player num is overridden
     */
    public void setPlayer(Player player) {
        if(player == null) throw new IllegalArgumentException("player cannot be null");
        this.players[player.getPlayerNum()] = player;
    }
    
    /**
     * assign a line to a player by lineId
     * if the line has already been played false is returned
     * @param player the player who should own the line
     * @param lineId the lineId of the line played
     * @return true if successful, false if line is already played
     */
    public boolean playLine(Player player, int lineId) {
        Line line = lines.get(lineId);
        return playLine(player, line);
    }
    
    /**
     * assign a line to a player
     * if the line has already been played false is returned
     * @param player  the player who should own the line
     * @param line
     * @return true if successful, false if line is already played
     */
    public boolean playLine(Player player, Line line) {
        if(getPlayer(player.getPlayerNum()) != player)
            throw new IllegalArgumentException("this player is not associated with this board");
        boxFilled = false;
        if(line == null) return false;
        if(line.getOwner() != null) return false;
        
        boxFilled = line.setOwner(player);
        return true;
    }
    
    /**
     * whether the last turn filled a box on the board
     * @return true if the last turn filled a box on the board
     */
    public boolean getBoxFilled() {
        return boxFilled;
    }
    
    /**
     * whether all lines on the board are occupied
     * @return true if all lines are used, false if not
     */
    public boolean isBoardFull() {
        return lines.values().stream().allMatch(line -> line.getOwner() != null);
    }
}
