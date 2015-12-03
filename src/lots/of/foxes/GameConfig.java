package lots.of.foxes;

/**
 * Stores the configuration for a Game
 * 
 * @author Moritz
 */
public class GameConfig {
    
    /**
     * size of the board in the X dimension
     */
    private int boardSizeX;
    
    /**
     * size of the board in the Y dimension
     */
    private int boardSizeY;
    
    /**
     * name of the game that appears in the server list
     */
    private String gameName;

    /**
     * Get the value of boardSizeX
     *
     * @return the value of boardSizeX
     */
    public int getBoardSizeX() {
        return boardSizeX;
    }

    /**
     * Set the value of boardSizeX
     *
     * @param boardSizeX new value of boardSizeX
     */
    public void setBoardSizeX(int boardSizeX) {
        this.boardSizeX = boardSizeX;
    }

    /**
     * Get the value of boardSizeY
     *
     * @return the value of boardSizeY
     */
    public int getBoardSizeY() {
        return boardSizeY;
    }

    /**
     * Set the value of boardSizeY
     *
     * @param boardSizeY new value of boardSizeY
     */
    public void setBoardSizeY(int boardSizeY) {
        this.boardSizeY = boardSizeY;
    }
    
    /**
     * Get the value of gameName
     *
     * @return the value of gameName
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Set the value of gameName
     *
     * @param gameName new value of gameName
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
    
}
