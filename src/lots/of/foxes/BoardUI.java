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
import java.util.function.Consumer;
import javax.swing.JPanel;
import lots.of.foxes.model.Box;
import lots.of.foxes.model.Line;

/**
 *
 * @author Adrian
 */
public class BoardUI extends JPanel implements MouseListener {

    ArrayList<Line> lines;
    ArrayList<Box> boxes;
    ArrayList<LineControl> linesControls;
    ArrayList<BoxControl> boxControls;
    int lineheight;
    int boxWidth;

    public BoardUI(ArrayList<Line> lines, ArrayList<Box> boxes, int lineheight, int boxWidth) {
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
            //Line found do something
        } else {
            //Line not found Idiot
        }

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

}
