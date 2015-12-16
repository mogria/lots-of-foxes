/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes.ui.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lots.of.foxes.model.Box;
import lots.of.foxes.ui.Resources;

/**
 *
 * @author Adrian
 */
public class BoxControl extends JLabel {

    private Box box;
    private GrassGenerator grassGenerator;

    public BoxControl(Box box) {
        grassGenerator = new GrassGenerator(new Random().nextInt(), 60);
        this.setMaximumSize(new Dimension(0, 0));
        this.setPreferredSize(new Dimension(0, 0));
        
        this.box = box;
        BoxControl that = this;
        this.box.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            if(evt.getPropertyName().equals(Box.PROP_OWNER)) {
                that.repaint();
            }
        });
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (box.getOwner() != null) {
            g.setColor(box.getOwner().getColor());
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            grassGenerator.drawGrass(g, getWidth(), getHeight());
            
        }
        
        Image standingFox = Resources.getStandingFox();
        drawFoxInBox(g, standingFox);
    }
    
    private void drawFoxInBox(Graphics g, Image img) {
        if(img != null && img.getWidth(null) != -1) {
            int imageWidth = img.getWidth(null);
            int imageHeight = img.getHeight(null);
            
            
            double scaler = Math.min(getWidth() / (double)imageWidth, getHeight() / (double)imageHeight);
            imageWidth = (int)(imageWidth * scaler);
            imageHeight = (int)(imageHeight * scaler);
            Image rescaled = img.getScaledInstance(imageWidth, imageHeight, 0);
            
            
            int offsetX = (getWidth() - imageWidth) / 2;
            int offsetY = (getHeight() - imageHeight) / 2;
            
            
            g.drawImage(rescaled, offsetX, offsetY, null);
        }
    }

    public Box getBox() {
        return box;
    }
}
