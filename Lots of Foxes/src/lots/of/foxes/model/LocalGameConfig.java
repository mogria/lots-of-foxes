package lots.of.foxes.model;

/**
 *
 * @author Moritz
 */
public class LocalGameConfig extends GameConfig {

    private boolean saveGame;   
    
    private AIDifficulty aiDifficulty;

    public LocalGameConfig(int boardSizeX, int boardSizeY, String gameName, boolean saveGame, AIDifficulty aiDifficulty) {
        super(boardSizeX, boardSizeY, gameName);
        this.saveGame = saveGame;
        this.aiDifficulty = aiDifficulty;
    }


    /**
     * Get the value of saveGame
     *
     * @return the value of saveGame
     */
    public boolean isSaveGame() {
        return saveGame;
    }

    /**
     * Set the value of saveGame
     *
     * @param saveGame new value of saveGame
     */
    public void setSaveGame(boolean saveGame) {
        this.saveGame = saveGame;
    }

     /**
     * Get the value of aIDifficulty
     *
     * @return the value of aIDifficulty
     */
    public AIDifficulty getAIDifficulty() {
        return aiDifficulty;
    }

    /**
     * Set the value of aIDifficulty
     *
     * @param aIDifficulty new value of aIDifficulty
     */
    public void setAIDifficulty(AIDifficulty aIDifficulty) {
        this.aiDifficulty = aIDifficulty;
    }
    
    

}
