package lots.of.foxes.model;

/**
 * Possible difficulty settings for the AI
 * @author Moritz
 */
public enum AIDifficulty {
    LOW("Low"),
    MEDIUM("Medium");
    
    private String name;
    private AIDifficulty(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
