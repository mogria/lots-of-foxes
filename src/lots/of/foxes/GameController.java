package lots.of.foxes;

import lots.of.foxes.model.GameConfig;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Line;

/**
 * Responsible for turn management and ending the game
 * @author Moritz
 */
public class GameController implements Runnable {
    
    private final Board board;
    private final GameConfig config;
    
    private final ITurnHandler[] handlers = new ITurnHandler[2];
    private int currentHandlerIndex = 0;
    
    public GameController(GameConfig config, Board board, ITurnHandler me, ITurnHandler enemy) {
        if(board == null) throw new IllegalArgumentException("board cannot be null");
        if(config == null) throw new IllegalArgumentException("config cannot be null");
        if(me == null) throw new IllegalArgumentException("me cannot be null");
        if(enemy == null) throw new IllegalArgumentException("enemy cannot be null");
        
        this.board = board;
        this.config = config;
        handlers[0] = me;
        handlers[1] = enemy;
        
        currentHandlerIndex = config.getDoIStartFirst() ? 0 : 1;
    }
    
    @Override
    public void run() {
        while(!hasGameEnded()) { 
            Line turnLine = getCurrent().receiveTurn();
            if(turnLine != null) {
                board.playLine(getCurrent().getPlayer(), turnLine);
                getCurrent().sendTurn(turnLine);
                // only swap players if no box has been filled
                if(!board.getBoxFilled()) {
                    swap();
                }
            }
        }
    }
    
    public void swap() {
        currentHandlerIndex = (currentHandlerIndex + 1) % handlers.length;
    }
    
    public ITurnHandler getCurrent() {
        return handlers[currentHandlerIndex];
    }

    private boolean hasGameEnded() {
        return board.isBoardFull();
    }
}
