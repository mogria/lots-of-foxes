/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
    Collection<LineControl> linesControls;
    Collection<BoxControl> boxControls;
    Line lastClickedLine;
    int lineheight;
    int boxWidth;

    public BoardUI(Collection<Line> lines, Collection<Box> boxes, int lineheight, int boxWidth) {
        this.lines = lines;
        this.boxes = boxes;
        this.lineheight = lineheight;
        this.boxWidth = boxWidth;

        this.setPreferredSize(new Dimension(500, 500));

        lines.stream().forEach((a) -> {
            linesControls.add(new LineControl(a, lineheight, boxWidth));
        });
        boxes.stream().forEach((a) -> {
            boxControls.add(new BoxControl(a, lineheight, boxWidth));
        });

        repaint();
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
    public void paintComponent(Graphics g) {

        linesControls.stream().forEach((lc) -> {
            lc.paintComponent(this.getGraphics());
        });
        boxControls.stream().forEach((bc) -> {
            bc.paintComponent(this.getGraphics());
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
