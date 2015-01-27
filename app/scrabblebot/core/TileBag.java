package scrabblebot.core;

import java.util.ArrayList;
import java.util.List;

public class TileBag {

    private List<Tile> tiles = new ArrayList<Tile>();

    public TileBag() {
        TileConfig config = new TileConfig();
        for (Character key : config.tileConfig.keySet()) {
            TileConfig.Tuple tuple = config.tileConfig.get(key);
            int num = tuple.number;
            int points = tuple.points;
            for (int i = 1; i <= num; i++) {
                tiles.add(new Tile(key, points));
            }
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public Tile randomDraw() {
        int random_index = (int) (Math.random() * tiles.size());
        return tiles.remove(random_index);
    }

    public void removeByChar(Character c){
        for(Tile t : tiles){
            if (t.getCharacter().equals(c)) {
                tiles.remove(t);
                break;
            }
        }
    }

}
