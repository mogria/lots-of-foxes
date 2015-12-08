/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;

import java.awt.Color;
import java.awt.Component;
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
    int gridX;
    int gridY;

    public BoardUI(Collection<Line> lines, Collection<Box> boxes, int lineheight, int boxWidth,int gridX,int gridY) {
        this.lines = lines;
        this.boxes = boxes;
        this.lineheight = lineheight;
        this.boxWidth = boxWidth;
        this.gridX = gridX /2+1;
        this.gridY = gridY/2+1;
        
        
       
        Dimension d = new Dimension();
        d.height = ((this.gridY) *(boxWidth+lineheight)) -boxWidth;
        d.width = ((this.gridX) *(boxWidth+lineheight))-boxWidth;

        this.setPreferredSize(d);
        this.setSize(d);

        for (Line l : lines) {
            LineControl lc = new LineControl(l, lineheight, boxWidth);
            linesControls.add(lc);
            this.add(lc);
        }
        int boxrow = 0;
        int boxcol = 0;
        for (Box b : boxes) {
            BoxControl bc = new BoxControl(b, lineheight, boxWidth);
            boxControls.add(bc);
            boxrow = b.getRow() > boxrow ? b.getRow() : boxrow;
            boxcol = b.getColumn() > boxcol ? b.getColumn() : boxcol;
            this.add(bc);
        }


        addMouseListener(this);

    }

    private void paintDots(Graphics g) {
        int r = (int) (lineheight);
        //Loop through all lines
        for (int a = 0; a < this.gridY;a++){
            g.fillOval(0, a*(boxWidth+lineheight), r, r);
            for (int i =0;i < this.gridX;i++){
                g.fillOval(i*(boxWidth+lineheight), a*(boxWidth+lineheight), r, r);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      
    }

    public Line lastClickedLine() {
        return this.lastClickedLine;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.paintDots(g);
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
         Line l = null;
         int x = e.getX();
        int y = e.getY();
        
        
        for (LineControl lc : linesControls) {
            int lx = lc.getX();
            int ly = lc.getY();
            
            if (lc.containsPoint(e.getPoint())) {
                l = lc.getLine();
                break;
            }
        }
        Component temp = findComponentAt(e.getPoint());
        
       /* LineControl lc = (LineControl)e.getSource();
        l = lc.getLine();*/
        
        if (l != null) {
            this.lastClickedLine = l;
        } else {
            this.lastClickedLine = null;
        }
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
