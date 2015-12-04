/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import javax.swing.JPanel;
import lots.of.foxes.model.Box;
import lots.of.foxes.model.Line;
import lots.of.foxes.model.Player;

/**
 *
 * @author Adrian
 */
public class BoardUI extends JPanel implements MouseListener, ITurnHandler {

    Collection<Line> lines;
    Collection<Box> boxes;
    Collection<LineControl> linesControls = new ArrayList<>();
    Collection<BoxControl> boxControls = new ArrayList<>();

    Line lastClickedLine;
    int lineheight;
    int boxWidth;

    public BoardUI(Collection<Line> lines, Collection<Box> boxes, int lineheight, int boxWidth) {
        this.lines = lines;
        this.boxes = boxes;
        this.lineheight = lineheight;
        this.boxWidth = boxWidth;
        this.setBackground(Color.BLACK);

       

        for (Line l : lines) {
            LineControl lc = new LineControl(l, lineheight, boxWidth);
            linesControls.add(lc);
            this.add(lc);
        }
        int boxrow =0 ;
        int boxcol = 0;
        for (Box b : boxes) {
            BoxControl bc = new BoxControl(b, lineheight, boxWidth);
            boxControls.add(bc);
            boxrow = b.getRow() > boxrow ? b.getRow() : boxrow;
            boxcol = b.getColumn() > boxcol ? b.getColumn() : boxcol;        
            this.add(bc);
        }
        
        Dimension d = new Dimension();
        d.height = ((boxrow-1) * boxWidth) - lineheight;// + ((boxcol + 2) * lineheight);
        d.width = ((boxcol-1) * boxWidth) - lineheight;// + ((boxrow + 2) * lineheight);

        this.setPreferredSize(d);
        
        addMouseListener(this);
        
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Line l = null;
        for (LineControl lc : linesControls) {
            if (lc.contains(e.getX(), e.getY())) {
                l = lc.getLine();
                break;
            }
        }

        if (l != null) {
            this.lastClickedLine = l;
        } else {
            this.lastClickedLine = null;
        }

    }

    public Line lastClickedLine() {
        return this.lastClickedLine;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        linesControls.stream().forEach((lc) -> {
            lc.paint(this.getGraphics());
        });
        boxControls.stream().forEach((bc) -> {
            bc.paint(this.getGraphics());
        });

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

}
