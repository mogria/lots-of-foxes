package lof.model;

import javax.swing.JFrame;

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
     * whether you can play first
     */
    private boolean doIStartFirst;
    
    /**
     * the type of this game
     */
    private GameType gameType;
    
    /**
     * the frame the application should be run on
     */
    private JFrame mainFrame;

    /**
     * constructor
     * 
     * @param boardSizeX
     * @param boardSizeY
     * @param gameName 
     */
    public GameConfig(int boardSizeX, int boardSizeY, String gameName) {
        this.boardSizeX = boardSizeX;
        this.boardSizeY = boardSizeY;
        this.gameName = gameName;
    }
    
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

    /**
     * Get the value of doIStartFirst
     * 
     * @return the value of doIStartFirst
     */
    public boolean getDoIStartFirst() {
        return doIStartFirst;
    }

    /**
     * Set the value of doIStartFirst
     * 
     * @param doIStartFirst 
     */
    public void setDoIStartFirst(boolean doIStartFirst) {
        this.doIStartFirst = doIStartFirst;
    }
    
    /**
     * Get the value of gameType
     * 
     * @return the value of gameType
     */
    public GameType getGameType() {
        return gameType;
    }

    /**
     * Set the value of gameType
     * 
     * @param gameType 
     */
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
    
    /**
     * 
     * @return 
     */
    public JFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * 
     * @param mainFrame 
     */
    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}
