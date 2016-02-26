package lots.of.foxes.ui.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author Adrian
 */
public class PointControl extends JLabel {

    public PointControl() {
        
        
        this.setMaximumSize(new Dimension(0, 0));
        this.setBackground(BoardUI.WOOD_COLOR);
    }
    
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(getBackground().darker());
        g.fillOval((int)(getWidth() * 0.2), (int)(getHeight() * 0.2), (int)(getWidth() * 0.6), (int)(getHeight() * 0.6));
        g.setColor(Color.BLACK);
        g.drawOval((int)(getWidth() * 0.2), (int)(getHeight() * 0.2), (int)(getWidth() * 0.6), (int)(getHeight() * 0.6));
        this.setBorder(BorderFactory.createLineBorder(BoardUI.WOOD_COLOR.darker(), 1));
    }
    
}
