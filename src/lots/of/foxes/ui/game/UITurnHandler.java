package lots.of.foxes.ui.game;

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
    Thread thread;


    public UITurnHandler(Board board, Player player, int lineheight, int boxwidth, Thread threadtoNotify) {
        super(board, player);
        boardUI = new BoardUI(board, 10, 50, threadtoNotify);
        thread = new Thread(boardUI);
        thread.start();
    }
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public void sendTurn(Line line) {
        boardUI.repaint();
    }

    @Override
    public Line receiveTurn() {
        return boardUI.GetlastClickedLine();
    }

}
