package scrabblebot.core;

public class SideWord {

    
    private String word;
    
    private int points;
    public SideWord(){}
    public SideWord(String s, int y) {
        this.word = s;
        this.points = y;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }
}