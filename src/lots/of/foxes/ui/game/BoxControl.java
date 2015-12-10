/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes.ui.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import lots.of.foxes.model.Box;

/**
 *
 * @author Adrian
 */
public class BoxControl extends JPanel {

    Box box;
    int lineheight;
    int boxWidth;
    int lineoffset;

    public BoxControl(Box box, int lineheight, int boxWidth, int pointMultiplicator) {
        this.box = box;
        this.lineheight = lineheight;
        this.boxWidth = boxWidth;
        this.lineoffset = (lineheight * pointMultiplicator) / 4;
        
    }

    @Override
    public int getX() {
        return calcX();
    }

    @Override
    public int getY() {
        return calcY();
    }

    @Override
    public Dimension getSize() {
        return new Dimension(boxWidth - lineheight, boxWidth - lineheight);
    }

    @Override
    public int getHeight() {
        return boxWidth;
    }

    @Override
    public int getWidth() {
        return boxWidth;
    }

    /**
     * Calculates the X coordinate of the current Row/Column
     *
     * @return X value
     */
    private int calcX() {

        int cntBoxes = box.getColumn() / 2;
        int cntLines = box.getColumn() - cntBoxes;

        return (cntBoxes * boxWidth) + (cntLines * lineheight) + lineoffset;

    }

    /**
     * Calculates the Y coordinate of the current Row/Column
     *
     * @return Y value
     */
    private int calcY() {
        int cntBoxes = box.getRow() / 2;
        int cntLines = box.getRow() - cntBoxes;

        return (cntBoxes * boxWidth) + (cntLines * lineheight) + lineoffset;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (box.getOwner() != null) {
            g.setColor(box.getOwner().getColor());
             g.fillRect(0, 0, boxWidth, boxWidth);
             this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        } else {
            //g.setColor(Color.gray);
            
        }

       
    }
}
