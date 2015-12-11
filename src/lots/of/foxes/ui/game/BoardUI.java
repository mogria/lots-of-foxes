/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes.ui.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JPanel;
import lots.of.foxes.ITurnHandler;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Box;
import lots.of.foxes.model.Line;
import lots.of.foxes.model.Player;

/**
 *
 * @author Adrian
 */
public class BoardUI extends JPanel implements MouseListener, ITurnHandler, Runnable {

   private Collection<LineControl> linesControls = new ArrayList<>();
    private Collection<BoxControl> boxControls = new ArrayList<>();

    private Line lastClickedLine;
    private int lineheight;
    private int boxWidth;
    private int gridX;
    private int gridY;
    private int pointMultiplicator = 2;
    private Object turnLock;

    public BoardUI(Board b, int lineheight, int boxWidth, Object turnLock) {
        this.lineheight = lineheight;
        this.boxWidth = boxWidth;
        this.gridX = b.getGridSizeX() / 2 + 1;
        this.gridY = b.getGridSizeX() / 2 + 1;
        this.turnLock = turnLock;

        InitializeBoard();
        b.getLines().stream().map((l) -> new LineControl(l, lineheight, boxWidth, pointMultiplicator)).map((lc) -> {
            linesControls.add(lc);
            return lc;
        }).forEach((lc) -> {
            this.add(lc);
        });

        b.getBoxes().stream().map((bo) -> new BoxControl(bo, lineheight, boxWidth, pointMultiplicator)).map((bc) -> {
            boxControls.add(bc);
            return bc;
        }).forEach((bc) -> {
            this.add(bc);
        });
        
    }

    /**
     * Initialize the board, sets the Dimension and adds the Mouselistener
     */
    private void InitializeBoard() {
        this.setLayout(new GridBagLayout());
        Dimension d = new Dimension();
        d.height = ((this.gridY) * (boxWidth + lineheight)) - boxWidth + (lineheight * pointMultiplicator / 2);
        d.width = ((this.gridX) * (boxWidth + lineheight)) - boxWidth + (lineheight * pointMultiplicator / 2);

        setPreferredSize(d);
        setSize(d);
        addMouseListener(this);
    }

    /**
     * Paints the Dots on the Board
     *
     * @param Graphics object to paint on
     */
    private void paintDots(Graphics g) {
        int r = (int) (lineheight * pointMultiplicator);
        //Loop through all lines
        for (int a = 0; a < this.gridY; a++) {
            g.fillOval(0, a * (boxWidth + lineheight), r, r);
            for (int i = 0; i < this.gridX; i++) {
                g.fillOval(i * (boxWidth + lineheight), a * (boxWidth + lineheight), r, r);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.     
    }

    /**
     * Returns the last clicked Row
     *
     * @return last clicked Row
     */
    public Line GetlastClickedLine() {
        return this.lastClickedLine;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.paintDots(g);
        /*
        linesControls.stream().forEach((lc) -> {
            lc.paintComponent(this.getGraphics());
        });
        boxControls.stream().forEach((bc) -> {
            bc.paintComponent(this.getGraphics());
        });
*/
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        Line l = null;

        for (LineControl lc : linesControls) {
            if (lc.containsPoint(e.getPoint())) {
                l = lc.getLine();
                break;
            }
        }

        if (l != null) {
            
            this.lastClickedLine = l;
            this.repaint();
            synchronized(turnLock) {
                turnLock.notifyAll();
            }
         
        } else {
            this.lastClickedLine = null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sendTurn(Line line) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Line receiveTurn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Player getPlayer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        this.repaint();
        this.setVisible(true);
    }

}
