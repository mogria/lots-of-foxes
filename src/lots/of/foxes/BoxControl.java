/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;

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

    public BoxControl(Box line, int lineheight, int boxWidth) {
        this.box = box;
        this.lineheight = lineheight;
        this.boxWidth = boxWidth;
    }

    private int calcX() {
        
        return 0;
    }

    private int calcY() {
        return 0;
    }

    @Override
    public void paintComponent(Graphics g) {
        
        if (box.getOwner() != null) {
            g.setColor(box.getOwner().getColor());
        }        
        g.drawRect(calcX(), calcY(), boxWidth - lineheight, boxWidth - lineheight);
    }
}
