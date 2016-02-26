package lof.ui.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author Adrian
 */
public class GrassGenerator {
    static public final Color GRASS_COLOR = new Color(44, 176, 55);
    
    private int seed;
    private int n;
    
    public GrassGenerator(int seed, int n) {
        this.seed = seed;
        this.n = n;
    }
    
    public void drawGrass(Graphics g, int width, int height) {
        g.setColor(GRASS_COLOR);
        g.fillRect(0, 0, width, height);
        
        Random rand = new Random(seed);
        
        g.setColor(GRASS_COLOR.darker());
        for(int i = 0; i < n; i++) {
            
            int x = Math.round(rand.nextFloat() * width);
            int y = Math.round(rand.nextFloat() * height);
            
            g.fillPolygon(new int[]{
                x - 2,
                x,
                x + 2,
            }, new int[]{
                y + 2,
                y - 8,
                y + 2,
            }, 3);
        }
    }
    
}
