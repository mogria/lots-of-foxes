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
import java.awt.Point;
import javax.swing.BorderFactory;
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

    int lineoffset;

    public LineControl(Line line, int lineheight, int boxWidth, int pointMultiplicator) {
        this.line = line;
        this.lineheight = lineheight;
        this.boxWidth = boxWidth;
        this.lineoffset = (lineheight * pointMultiplicator) / 4;

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

        return (cntBoxes * boxWidth) + (cntLines * lineheight) + lineoffset;
    }

    /**
     * Calculates the Y coordinate of the current Row/Column
     *
     * @return Y value
     */
    private int calcY() {
        int cntBoxes = line.getRow() / 2;
        int cntLines = line.getRow() - cntBoxes;

        return (cntBoxes * boxWidth) + (cntLines * lineheight) + lineoffset;
    }

    /**
     * Check the if the current line is a Brick
     *
     * @return isBrick
     */
    private boolean isBrick() {
        return line.getColumn() % 2 == 0 && line.getRow() % 2 == 0;
    }

    /**
     * Calculates if the passed Point is in the object
     *
     * @param p Point to check
     * @return is in the current panel
     */
    public boolean containsPoint(Point p) {
        return this.getX() < p.x && p.x < (this.getX() + this.getWidth()) && this.getY() < p.y && p.y < (this.getY() + this.getHeight());
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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (line.getOwner() != null) {
            g.setColor(line.getOwner().getColor());
        } else {
            g.setColor(Color.lightGray);
        }

        g.fillRect(0, 0, line.getDirection() == LineDirection.Horizontal ? boxWidth : lineheight, line.getDirection() == LineDirection.Horizontal ? lineheight : boxWidth);

    }

}
