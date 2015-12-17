package lots.of.foxes.ui;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Moritz
 */
public class Resources {
    
    private static Map<String, Image> imageCache = new HashMap<>();
    
    private static Image getOrCacheImage(String resourcePath) {
        Image image;
        if((image = imageCache.get(resourcePath)) == null) {
            image = readImage(resourcePath);
            imageCache.put(resourcePath, image);
        }
        return image;
    }
    
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
        return getOrCacheImage(resourcePath);
    }
    
    public static Image getRunningFox1() {
        final String resourcePath = "resources/fox_run_1_256x128.png";
        return getOrCacheImage(resourcePath);
    }
    
    public static Image getRunningFox2() {
        final String resourcePath = "resources/fox_run_2_256x128.png";
        return getOrCacheImage(resourcePath);
    }
}
