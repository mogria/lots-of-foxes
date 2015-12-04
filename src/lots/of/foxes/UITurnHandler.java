
package lots.of.foxes;

import javax.swing.JPanel;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Line;
import lots.of.foxes.model.Player;

/**
 *
 * @author Adrian
 */
public class UITurnHandler extends AbstractTurnHandler{
    
    private BoardUI boardUI;

    public BoardUI getBoardUI() {
        return boardUI;
    }

    public UITurnHandler(Board board, Player player,int lineheight,int boxwidth) {
        super(board, player);
        boardUI = new BoardUI(board.getLines(), board.getBoxes(), lineheight, boxwidth);
    }
    
    @Override
    public void sendTurn(Line line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Line receiveTurn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}