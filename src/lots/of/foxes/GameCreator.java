package lots.of.foxes;

import lots.of.foxes.model.Board;

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
    
    public Board buildBoard() {
        return new Board(config.getBoardSizeX(), config.getBoardSizeY());
    }
    
    public ITurnHandler buildUITurnHandler() {
        return null;
    }
    
    public ITurnHandler buildAITurnHandler() {
        return null;
    }
    
    public ITurnHandler buildHostRemoteTurnHandler() {
        return null;
    }
    
    public ITurnHandler buildClientRemoteTurnHandler() {
        return null;
    }
    
}
