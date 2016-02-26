/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes.ui.game;

import lots.of.foxes.model.LineDirection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.Timer;
import lots.of.foxes.model.Line;

/**
 *
 * @author Adrian
 */
public class LineControl extends JButton {

    private final Line line;
    private final GrassGenerator grassGenerator;
    
    boolean owned = false;
    boolean highlight = true;

    
    public LineControl(Line line) {
        this.line = line;
        this.grassGenerator = new GrassGenerator(new Random().nextInt(), 10);
        this.setMaximumSize(new Dimension(0, 0));
        this.setPreferredSize(new Dimension(0, 0));

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
        grassGenerator.drawGrass(g, getWidth(), getHeight());
        if (line.getOwner() != null) {
            Color col;
            if(owned == false || highlight) {
                highlight = true;
                col = new Color(255, 255, 0);
                LineControl that = this;
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                    highlight = false;
                    java.awt.EventQueue.invokeLater(() -> this.repaint());
                }).start();
            } else {
                col = line.getOwner().getColor();
            }
            owned = true;
            
            g.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), BoardUI.PLAYER_COLOR_ALPHA));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            
            setBorder(BorderFactory.createLineBorder(BoardUI.WOOD_COLOR.darker(), 3));
        } else {
        }
    }
    
    public Line getLine() {
        return line;
    }

}
