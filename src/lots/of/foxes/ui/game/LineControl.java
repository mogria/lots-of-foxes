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
        

        if (line.getOwner() != null) {
            g.setColor(line.getOwner().getColor());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            
             setBorder(BorderFactory.createLineBorder(BoardUI.WOOD_COLOR.darker(), 3));
        } else {
            grassGenerator.drawGrass(g, getWidth(), getHeight());
        }
    }
    
    public Line getLine() {
        return line;
    }

}
