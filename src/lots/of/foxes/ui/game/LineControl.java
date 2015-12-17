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
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import lots.of.foxes.model.Line;

/**
 *
 * @author Adrian
 */
public class LineControl extends JButton {

    private final Line line;
    private final GrassGenerator grassGenerator;

    
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
            Color col = line.getOwner().getColor();
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
