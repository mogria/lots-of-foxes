/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
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
        return boxWidth - lineheight;
    }

    @Override
    public int getWidth() {
        return boxWidth - lineheight;
    }

    private int calcX() {

        int cntLines = box.getColumn() - (Math.round(box.getColumn() / 2));
        return ((cntLines + 1) * lineheight) + (boxWidth * cntLines);

    }

    private int calcY() {
        int cntLines = box.getRow() - (Math.round(box.getRow() / 2));
        return ((cntLines + 1) * lineheight) + (boxWidth * cntLines);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (box.getOwner() != null) {
            g.setColor(box.getOwner().getColor());
        }
        g.drawRect(0, 0, boxWidth - lineheight, boxWidth - lineheight);
    }
}
