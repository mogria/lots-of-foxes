package lots.of.foxes;

import lots.of.foxes.model.GameConfig;
import java.awt.Color;
import lots.of.foxes.ai.DumbAITurnHandler;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.GameType;
import lots.of.foxes.model.LocalGameConfig;
import lots.of.foxes.model.Player;
import lots.of.foxes.model.RemoteGameConfig;

/**
 *
 * @author Moritz
 */
public final class GameCreator {
    
    /**
     * configuration on how to make a game.
     * 
     * This may also be an instance of LocalGameConfig or RemoteGameConfig
     * depending on the gameType attribute.
     */
    private GameConfig config;
    
    /**
     * the board created the game later takes place on.
     */
    private Board board;
    
    /**
     * create an instance of GameCreate
     * @param config how the game creator should create a game
     */
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
        if(config.getGameType() == GameType.LOCAL_AI) {
            LocalGameConfig localConfig = (LocalGameConfig)config;
            if(localConfig.isSaveGame()) {
                new SaveFile(localConfig.getGameName())
            }
        }
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
        ITurnHandler aiTurnHandler;
        Player aiPlayer;
        
        LocalGameConfig localConfig = (LocalGameConfig)config;
        switch(localConfig.getAIDifficulty()) {
            default:
            case LOW:
                aiPlayer = new Player(1, "DumbAI", Color.blue);
                aiTurnHandler = new DumbAITurnHandler(board, aiPlayer);
        }
        
        board.setPlayer(aiPlayer);
        
        return aiTurnHandler; 
    }
    
    /**
     * Creates an instance of HostRemoteTurnHandler depending on the GameConfig
     * @return an HostRemoteTurnHandler instance
     */
    public ITurnHandler buildHostRemoteTurnHandler() {
        RemoteGameConfig remoteConfig = (RemoteGameConfig)config;
        return new HostRemoteTurnHandler(remoteConfig.getPort(), board, board.getPlayer(0));
    }
    
    /**
     * Creates an instance of ClientRemoteTurnHandler depending on the GameConfig
     * @return an ClientRemoteTurnHandler instance
     */
    public ITurnHandler buildClientRemoteTurnHandler() {
        RemoteGameConfig remoteConfig = (RemoteGameConfig)config;
        return new ClientRemoteTurnHandler(board, board.getPlayer(1), null, remoteConfig.getPort());
    }
    
}
