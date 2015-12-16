package lots.of.foxes.ui;

import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Moritz
 */
public class Resources {
    
    private static Image readImage(String resourcePath) {
        Image image = null;
        try {
            image = ImageIO.read(Resources.class.getResourceAsStream(resourcePath));
        } catch (IOException ex) {
            Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }
    
    public static Image getStandingFox() {
        final String resourcePath = "resources/fox_standing_256x128.png";
        
        return readImage(resourcePath);
    }
}
