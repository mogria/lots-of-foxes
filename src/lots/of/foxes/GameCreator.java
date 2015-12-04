package lots.of.foxes;

import lots.of.foxes.model.GameConfig;
import java.awt.Color;
import lots.of.foxes.ai.DumbAITurnHandler;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Player;

/**
 *
 * @author Moritz
 */
public class GameCreator {
    
    private GameConfig config;
    
    private Board board;
    
    public GameCreator(GameConfig config) {
        this.config = config;
        this.board = buildBoard();
    }
    
    /**
     * Creates an instance of GameController, depending on the GameConfig
     * @return an GameController instance
     */
    public GameController buildGameController() {
        switch(config.getGameType()) {
            default:
            case LOCAL_AI:
                return new GameController(config, board, buildUITurnHandler(), buildAITurnHandler());
            case REMOTE_CLIENT:
                return new GameController(config, board, buildUITurnHandler(), buildHostRemoteTurnHandler());
            case REMOTE_HOST:
                return new GameController(config, board, buildUITurnHandler(), buildClientRemoteTurnHandler());
                    
        } 
    }
    
    /**
     * Creates an instance of a Board, depending on the configured BoardSize
     * @return an Board instance
     */
    public Board buildBoard() {
        return new Board(config.getBoardSizeX(), config.getBoardSizeY());
    }
    
    /**
     * Creates an instance of UITurnHandler depending on the GameConfig
     * @return an UITurnHandler instance
     */
    public ITurnHandler buildUITurnHandler() {
        return null;
    }
    
    /**
     * Creates an instance of AITurnHandler depending on the GameConfig
     * @return an AITurnHandler instance
     */
    public ITurnHandler buildAITurnHandler() {
        return new DumbAITurnHandler(board, new Player("DumbAI", Color.yellow));
    }
    
    /**
     * Creates an instance of HostRemoteTurnHandler depending on the GameConfig
     * @return an HostRemoteTurnHandler instance
     */
    public ITurnHandler buildHostRemoteTurnHandler() {
        return null;
    }
    
    /**
     * Creates an instance of ClientRemoteTurnHandler depending on the GameConfig
     * @return an ClientRemoteTurnHandler instance
     */
    public ITurnHandler buildClientRemoteTurnHandler() {
        return null;
    }
    
}
