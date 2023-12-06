package algonquin.cst2335.finalproject.dict;

public class WordHistory {
    private String word;
    private String definition;

    public WordHistory(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }
}
