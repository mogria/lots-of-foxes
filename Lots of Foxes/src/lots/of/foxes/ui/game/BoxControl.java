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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import lots.of.foxes.model.Box;
import lots.of.foxes.ui.Resources;

/**
 *
 * @author Adrian
 */
public class BoxControl extends JLabel {

    private Box box;
    private GrassGenerator grassGenerator;
    private static final int NUM_IMAGES = 3;
    private Image[] images = new Image[NUM_IMAGES];
    private Image[] rescImages = new Image[NUM_IMAGES];
    private int currentImageIndex = 0;

    public BoxControl(Box box) {
        images[0] = Resources.getRunningFox1();
        images[1] = Resources.getRunningFox2();
        images[2] = Resources.getStandingFox();
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
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                rescImages = new Image[NUM_IMAGES];
            }
        });
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        grassGenerator.drawGrass(g, getWidth(), getHeight());
        if (box.getOwner() != null) {
            Color col = box.getOwner().getColor();
            g.setColor(new Color(col.getRed(), col.getGreen(), col.getBlue(), BoardUI.PLAYER_COLOR_ALPHA));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        
        drawFoxInBox(g, images[currentImageIndex]);
    }
    
    private void drawFoxInBox(Graphics g, Image img) {
        if(img != null && img.getWidth(null) != -1) {
            int imageWidth = img.getWidth(null);
            int imageHeight = img.getHeight(null);

            if(rescImages[currentImageIndex] == null || rescImages[currentImageIndex].getWidth(null) == -1) {

                double scaler = Math.min(getWidth() / (double)imageWidth, getHeight() / (double)imageHeight);
                imageWidth = (int)(imageWidth * scaler);
                imageHeight = (int)(imageHeight * scaler);
                rescImages[currentImageIndex] = img.getScaledInstance(imageWidth, imageHeight, 0);
            } else {
                imageWidth = rescImages[currentImageIndex].getWidth(null);
                imageHeight = rescImages[currentImageIndex].getHeight(null);
            }
            
            int offsetX = (getWidth() - imageWidth) / 2;
            int offsetY = (getHeight() - imageHeight) / 2;
            g.drawImage(rescImages[currentImageIndex], offsetX, offsetY, null);
        }
    }
    
    public void updateFoxImage() {
        if(box.getOwner() == null) {
            currentImageIndex = (currentImageIndex + 1) % (NUM_IMAGES - 1);
        } else {
            currentImageIndex = 2;
        }
        java.awt.EventQueue.invokeLater(() -> repaint());
    }

    public Box getBox() {
        return box;
    }
}
