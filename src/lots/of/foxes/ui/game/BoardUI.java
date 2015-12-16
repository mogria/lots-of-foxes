/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lots.of.foxes.ui.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import lots.of.foxes.ITurnHandler;
import lots.of.foxes.model.Board;
import lots.of.foxes.model.Box;
import lots.of.foxes.model.Line;
import lots.of.foxes.model.Player;

/**
 *
 * @author Adrian
 */
public class BoardUI extends JPanel implements Runnable {
    
    static private final double POINT_WEIGHT = 0.2;
    static private final double BOX_WEIGHT = 1;
    
    static public final Color WOOD_COLOR = new Color(205, 133, 63);
    
    private final Board board;

    private final Object turnLock;
    private Line lastClickedLine;
    
    private final JPanel quadraticInnerPanel = new JPanel();
    
    private final LineClickListener listener;
    
    public class LineClickListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getSource() instanceof LineControl) {
                LineControl clickedControl = (LineControl)e.getSource();
                Line clickedLine = clickedControl.getLine();
                if(clickedLine != null) {
                    synchronized(turnLock) {
                        lastClickedLine = clickedLine;
                        turnLock.notifyAll();
                    }
                }
            }
        }
        
    }

    public BoardUI(Board b) {        
        this.turnLock = new Object();
        this.lastClickedLine = null;
        
        this.board = b;
        this.listener = new LineClickListener();
        
        GridBagLayout layout = new GridBagLayout();
        quadraticInnerPanel.setLayout(layout);
        board.getLines().stream().forEach(line -> {
            LineControl lc = new LineControl(line);
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.weightx = line.isHorizontal() ? BOX_WEIGHT : POINT_WEIGHT;
            c.weighty = line.isVertical()? BOX_WEIGHT : POINT_WEIGHT;
            c.gridx = line.getColumn();
            c.gridy = line.getRow();
            lc.addMouseListener(listener);
            quadraticInnerPanel.add(lc, c);
        });
        
        board.getBoxes().stream().forEach(box -> {
            BoxControl bc = new BoxControl(box);
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.weightx  = BOX_WEIGHT;
            c.weighty  = BOX_WEIGHT;
            c.gridx = box.getColumn();
            c.gridy = box.getRow();
            quadraticInnerPanel.add(bc, c);
        });
        
        board.getPointCoordinates().stream().forEach(point -> {
            PointControl pc = new PointControl();
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.weightx = POINT_WEIGHT;
            c.weighty = POINT_WEIGHT;
            c.gridx = point.getColumn();
            c.gridy = point.getRow();
            quadraticInnerPanel.add(pc, c);
        });
        
        
        GridBagLayout outerLayout = new GridBagLayout();
        this.setLayout(outerLayout);
        JPanel that = this;
        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                
                Component sourceComponent = e.getComponent();
                GridBagConstraints gc = makeCenteringGridBagConstraints(sourceComponent.getWidth(), sourceComponent.getHeight());
                outerLayout.setConstraints(quadraticInnerPanel, gc);
                that.revalidate();
            }
            
        });
        
        this.add(quadraticInnerPanel);
    }
    
    private GridBagConstraints makeCenteringGridBagConstraints(int outerWidth, int outerHeight) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.BOTH;
        int sideLength = Math.min(outerWidth, outerHeight);
        int offsetX = (outerWidth - sideLength) / 2;
        int offsetY = (outerHeight - sideLength) / 2;
        gc.insets = new Insets(offsetY, offsetX, offsetY, offsetX);
        gc.weightx = 1;
        gc.weighty = 1;
        return gc;
    } 

    /**
     * Returns the last clicked Row
     *
     * @return last clicked Row
     */
    public Line waitForLineClick() throws InterruptedException {
        synchronized(turnLock) {
            turnLock.wait();
            return this.lastClickedLine;
        }
    }

    @Override
    public void run() {
        java.awt.EventQueue.invokeLater(() -> this.setVisible(true));
    }

}
