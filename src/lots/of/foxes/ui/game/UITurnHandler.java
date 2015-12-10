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

    public BoardUI getBoardUI() {
        return boardUI;
    }

    public UITurnHandler(Board board, Player player, int lineheight, int boxwidth) {
        super(board, player);
        boardUI = new BoardUI(board, 10, 50);
    }

    @Override
    public void sendTurn(Line line) {
        boardUI.repaint();
    }

    @Override
    public Line receiveTurn() {
        return boardUI.lastClickedLine();
    }

}
