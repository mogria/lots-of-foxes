package savegame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import lots.of.foxes.GameConfig;
import lots.of.foxes.model.Board;

/**
 * represents a save file
 * 
 * the save file
 * @author Moritz
 */
public class SaveFile {
    
    private File file; 
    
    public SaveFile(String fileName) {
        this.file = new File(fileName);
    }
    
    public boolean exists() {
        return file.exists();
    }
    
    public void save(Board board) throws IOException {
        if(!exists()) file.createNewFile();
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            throw new IOException("could not create file", ex);
        }

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(board);
    }
    
    public Board load() throws FileNotFoundException, IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            Object boardObject = objectInputStream.readObject();
            return (Board)boardObject;
        } catch (ClassNotFoundException | ClassCastException ex) {
            throw new IOException("could not read board", ex);
        } 
        
    }
}
