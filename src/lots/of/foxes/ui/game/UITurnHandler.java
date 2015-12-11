package lots.of.foxes.ui.game;

import java.awt.BorderLayout;
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


    public UITurnHandler(JFrame frame, Board board, Player player, int lineheight, int boxwidth, Thread parentThread) {
        super(board, player);
        this.parentThread = parentThread;
        boardUI = new BoardUI(board, 10, 50, turnLock);
        gameInfo = new GameInfo();
        frame.setLayout(new BorderLayout());
        frame.add(boardUI, BorderLayout.WEST);
        frame.add(gameInfo, BorderLayout.EAST);
        uiThread = new Thread(boardUI);
        uiThread.start();
    }
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public void sendTurn(Line line) {
        gameInfo.setEnemy();
        gameInfo.repaint();
        boardUI.repaint();
    }

    @Override
    public Line receiveTurn() {
        try {
            synchronized(turnLock) {
                turnLock.wait();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(UITurnHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        gameInfo.setYou();
        gameInfo.repaint();
        return boardUI.GetlastClickedLine();
    }

}
