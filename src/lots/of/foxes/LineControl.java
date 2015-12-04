/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;

import lots.of.foxes.model.LineDirection;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import lots.of.foxes.model.Line;

/**
 *
 * @author Adrian
 */
public class LineControl extends JPanel {

    Line line;
    /**
     * Height of the Line
     */
    int lineheight;
    /**
     * Dimension of the box
     */
    int boxWidth;
    /**
     * Direction of the Line
     */
    LineDirection lineDirection;

    public LineControl(Line line, int lineheight, int boxWidth) {
        this.line = line;
        this.lineheight = lineheight;
        this.boxWidth = boxWidth;
    }

    /**
     * The current Line Object
     */
    public Line getLine() {
        return line;
    }

    /**
     * Calculates the X coordinate of the current Row/Column
     *
     * @return X value
     */
    private int calcX() {

        int cntLines = (Math.round(line.getColumn() / 2));
        return ((cntLines + 1) * lineheight) + (boxWidth * cntLines);
    }

    /**
     * Calculates the Y coordinate of the current Row/Column
     *
     * @return Y value
     */
    private int calcY() {
        int cntLines = (Math.round(line.getRow() / 2));
        return ((cntLines + 1) * lineheight) + (boxWidth * cntLines);
    }

    /**
     * Check the if the current line is a Brick
     *
     * @return isBrick
     */
    private boolean isBrick() {
        return line.getColumn() % 2 == 0 && line.getRow() % 2 == 0;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (isBrick()) {
            g.setColor(Color.black);
            g.drawRect(calcX(), calcY(), lineheight, lineheight);
            return;
        }
        if (line.getOwner() != null) {
            g.setColor(line.getOwner().getColor());
        }

        g.drawRect(calcX(), calcY(), line.getDirection() == LineDirection.Horizontal ? boxWidth : lineheight, this.lineDirection == LineDirection.Horizontal ? lineheight : boxWidth);

    }

}
