package scrabblebot.core;

public class Tile {

    private final Character character;
    private final int points;

    public Tile(Character ch, int pts){
        this.character = ch;
        this.points = pts;
    }

    public Character getCharacter() {
        return character;
    }

    public int getPoints() {
        return points;
    }

}