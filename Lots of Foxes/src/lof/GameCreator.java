package lof;

import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import lof.ai.DumbAITurnHandler;
import lof.ai.NotSoDumbAITurnHandler;
import lof.model.Board;
import lof.model.GameConfig;
import lof.model.GameType;
import lof.model.LocalGameConfig;
import lof.model.Player;
import lof.model.RemoteGameConfig;
import lof.savegame.SaveFile;
import lof.ui.game.UITurnHandler;

/**
 *
 * @author Moritz
 */
public final class GameCreator {


    
    public class GameCreationException extends Exception {

        public GameCreationException() {
        }

        public GameCreationException(String message) {
            super(message);
        }

        public GameCreationException(Throwable cause) {
            super(cause);
        }

        public GameCreationException(String message, Throwable cause) {
            super(message, cause);
        }    
    }
    
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
     * @throws lof.GameCreator.GameCreationException
     */
    public GameCreator(GameConfig config) throws GameCreationException {
        this.config = config;
        this.board = buildBoard();
    }
    
    private RemoteGameConfig getRemoteConfig() throws GameCreationException {
        if(!(config instanceof RemoteGameConfig)) {
            throw new GameCreationException("invalid configuration for remote game");
        }
        
        return (RemoteGameConfig)config;
    }

    private LocalGameConfig getLocalConfig() throws GameCreationException {
        if(!(config instanceof LocalGameConfig)) {
            throw new GameCreationException("invalid configuration for local game");
        }
        
        return (LocalGameConfig)config;
    }
    
    /**
     * Creates an instance of GameController, depending on the GameConfig
     * @return an GameController instance
     * @throws lof.GameCreator.GameCreationException
     */
    public GameController buildGameController(ServerBroadcast... sb) throws GameCreationException {
        switch(config.getGameType()) {
            default:
            case LOCAL_AI:
                // build AI turn handler first, because board is modified
                ITurnHandler aihandler = buildAITurnHandler();
                return new GameController(config, board, buildUITurnHandler(), aihandler);
            case REMOTE_HOST:
                return new GameController(config, board, buildUITurnHandler(), buildHostRemoteTurnHandler(sb[0]));
            case REMOTE_CLIENT:
                return new GameController(config, board, buildUITurnHandler(), buildClientRemoteTurnHandler());
                    
        } 
    }
    
    /**
     * Creates an instance of a Board, depending on the configured BoardSize
     * @return an Board instance
     * @throws lof.GameCreator.GameCreationException
     */
    public Board buildBoard() throws GameCreationException {
        if(config.getGameType() == GameType.LOCAL_AI) {
            LocalGameConfig localConfig = getLocalConfig();
            if(localConfig.isSaveGame()) {
                SaveFile saveFile = new SaveFile(localConfig.getGameName());
                try {
                    return saveFile.load();
                } catch (IOException ex) {
                    throw new GameCreationException("couldn't load board from save file", ex);
                }
            }
        }
        return new Board(config.getBoardSizeX(), config.getBoardSizeY());
    }
    
    /**
     * Creates an instance of UITurnHandler depending on the GameConfig
     * @return an UITurnHandler instance
     */
    public ITurnHandler buildUITurnHandler() {
        if(config.getMainFrame() == null) { 
            throw new IllegalArgumentException("no frame to place UI handler on");
        }
        return new UITurnHandler(config.getMainFrame(), board, board.getPlayer(0), 20, 100, java.lang.Thread.currentThread(), config.getDoIStartFirst());
    }
    
    /**
     * Creates an instance of AITurnHandler depending on the GameConfig
     * @return an AITurnHandler instance
     * @throws lof.GameCreator.GameCreationException
     */
    public ITurnHandler buildAITurnHandler() throws GameCreationException {
        ITurnHandler aiTurnHandler;
        Player aiPlayer;
        
        LocalGameConfig localConfig = getLocalConfig();
        switch(localConfig.getAIDifficulty()) {
            default:
            case LOW:
                aiPlayer = new Player(1, "DumbAI", Color.blue);
                aiTurnHandler = new DumbAITurnHandler(board, aiPlayer);
                break;
            case MEDIUM:
                aiPlayer = new Player(1, "NotSoDumbAI", Color.blue);
                aiTurnHandler = new NotSoDumbAITurnHandler(board, aiPlayer);
        }
        
        board.setPlayer(aiPlayer);
        
        return aiTurnHandler; 
    }
    
    /**
     * Creates an instance of HostRemoteTurnHandler depending on the GameConfig
     * @return an HostRemoteTurnHandler instance
     * @throws lof.GameCreator.GameCreationException
     */
    public ITurnHandler buildHostRemoteTurnHandler(ServerBroadcast sb) throws GameCreationException {
        RemoteGameConfig remoteConfig = getRemoteConfig();
        return new HostRemoteTurnHandler(remoteConfig.getPort(), board, board.getPlayer(1),sb);
    }
    
    /**
     * Creates an instance of ClientRemoteTurnHandler depending on the GameConfig
     * @return an ClientRemoteTurnHandler instance
     * @throws lof.GameCreator.GameCreationException
     */
    public ITurnHandler buildClientRemoteTurnHandler() throws GameCreationException {
        RemoteGameConfig remoteConfig = getRemoteConfig();
        InetAddress ip;
        try {
            ip = InetAddress.getByName(remoteConfig.getServerIP());
            return new ClientRemoteTurnHandler(board, board.getPlayer(1),ip, remoteConfig.getPort());
        } catch (UnknownHostException ex) {
            throw new GameCreationException("Could not resolve host in buildClientRemoteTurnHandler.", ex);
        }
        
    }
    
}
