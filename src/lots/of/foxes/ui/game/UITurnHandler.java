package lots.of.foxes.ui.game;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import lots.of.foxes.AbstractTurnHandler;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Line;
import lots.of.foxes.model.Player;

/**
 *
 * @author Adrian
 */
public class UITurnHandler extends AbstractTurnHandler {

    private BoardUI boardUI;
    private GameInfo gameInfo;
    
    private Thread parentThread;
    private Thread uiThread;
    
    private final Object turnLock = new Object();


    public UITurnHandler(JFrame frame, Board board, Player player, int lineheight, int boxwidth, Thread parentThread, boolean doIStartFirst) {
        super(board, player);
        this.parentThread = parentThread;
        boardUI = new BoardUI(board);
        gameInfo = new GameInfo(board);
        java.awt.EventQueue.invokeLater(() -> {
            BorderLayout layout = new BorderLayout();
            frame.setLayout(layout);
            frame.add(boardUI, BorderLayout.CENTER);
            frame.add(gameInfo, BorderLayout.EAST);
            frame.revalidate();
            frame.repaint();
        });
        uiThread = new Thread(boardUI);
        uiThread.start();
        gameInfo.setEnemy();
        if(doIStartFirst){
            gameInfo.setYou();
            gameInfo.repaint();
        }
    }
    
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public void sendTurn(Line line) {
        java.awt.EventQueue.invokeLater(() -> boardUI.repaint());
    }

    @Override
    public Line receiveTurn() {

        gameInfo.setYou();
        
        Line lineClicked = null;
        try {
            lineClicked = boardUI.waitForLineClick();
        } catch (InterruptedException ex) {
            Logger.getLogger(UITurnHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        gameInfo.setEnemy();
        
        return lineClicked;
    }

}
