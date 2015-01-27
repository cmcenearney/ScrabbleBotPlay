package models;

import scrabblebot.core.Tile;

import java.util.ArrayList;
import java.util.List;

public class Player {

    String name;
    List<Tile> tiles = new ArrayList<>();
    int score;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, List<Tile> tiles) {
        this.name = name;
        this.tiles = tiles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
