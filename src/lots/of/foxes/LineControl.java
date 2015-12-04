/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes;

import lots.of.foxes.model.LineDirection;
import java.awt.Color;
import java.awt.Dimension;
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

        int cntBoxes = line.getColumn() / 2;
        int cntLines = line.getColumn() - cntBoxes;

        return (cntBoxes * boxWidth) + (cntLines * lineheight);
    }

    /**
     * Calculates the Y coordinate of the current Row/Column
     *
     * @return Y value
     */
    private int calcY() {
        int cntBoxes = line.getRow() / 2;
        int cntLines = line.getRow() - cntBoxes;

        return (cntBoxes * boxWidth) + (cntLines * lineheight);
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
    public int getX() {
        return calcX();
    }

    @Override
    public int getY() {
        return calcY();
    }

    @Override
    public Dimension getSize() {

        return new Dimension(line.getDirection() == LineDirection.Horizontal ? boxWidth : lineheight, line.getDirection() == LineDirection.Horizontal ? lineheight : boxWidth);
    }

    @Override
    public int getHeight() {
        return line.getDirection() == LineDirection.Horizontal ? lineheight : boxWidth;
    }

    @Override
    public int getWidth() {
        return line.getDirection() == LineDirection.Horizontal ? boxWidth : lineheight;
    }

    @Override
    public void paint(Graphics g) {
        if (line == null) {
            return;
        }

        super.paint(g);

        if (isBrick()) {
            g.setColor(Color.black);
            g.drawRect(calcX(), calcY(), lineheight, lineheight);
            return;
        }
        if (line.getOwner() != null) {
            g.setColor(line.getOwner().getColor());
        }
        g.setColor(Color.red);
        g.fillRect(0, 0, line.getDirection() == LineDirection.Horizontal ? boxWidth : lineheight, line.getDirection() == LineDirection.Horizontal ? lineheight : boxWidth);

    }

}
