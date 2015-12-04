/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JPanel;
import lots.of.foxes.model.Box;
import lots.of.foxes.model.LineDirection;

/**
 *
 * @author Adrian
 */
public class BoxControl extends JPanel {

    Box box;
    int lineheight;
    int boxWidth;

    public BoxControl(Box box, int lineheight, int boxWidth) {
        this.box = box;
        this.lineheight = lineheight;
        this.boxWidth = boxWidth;
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

    private int calcX() {

        int cntBoxes = box.getColumn() / 2;
        int cntLines = box.getColumn() - cntBoxes;

        return (cntBoxes * boxWidth) + (cntLines * lineheight);

    }

    private int calcY() {
        int cntBoxes = box.getRow() / 2;
        int cntLines = box.getRow() - cntBoxes;

        return (cntBoxes * boxWidth) + (cntLines * lineheight);
    }

    @Override
    public void paint(Graphics g) {
        if (box == null) {
            return;
        }
        super.paint(g);

        if (box.getOwner() != null) {
            g.setColor(box.getOwner().getColor());
        }
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, boxWidth, boxWidth);
    }
}
