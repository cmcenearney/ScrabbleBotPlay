package scrabblebot.core;

import java.util.HashMap;

public class TileConfig {

    public class Tuple {
        public final int number;
        public final int points;
        public Tuple(int x, int y) {
            this.number = x;
            this.points = y;
        }
    }

    public static final HashMap<Character, Tuple> tileConfig = new HashMap<>();

    public TileConfig() {
        // using Tuple format to store the number of tiles and point value
        tileConfig.put('E', new Tuple(12, 1));
        tileConfig.put('A',  new Tuple(9, 1));
        tileConfig.put('I',  new Tuple(9, 1));
        tileConfig.put('O',  new Tuple(8, 1));
        tileConfig.put('N',  new Tuple(6, 1));
        tileConfig.put('R',  new Tuple(6, 1));
        tileConfig.put('T',  new Tuple(6, 1));
        tileConfig.put('L',  new Tuple(4, 1));
        tileConfig.put('S',  new Tuple(4, 1));
        tileConfig.put('U',  new Tuple(4, 1));
        tileConfig.put('D',  new Tuple(4, 2));
        tileConfig.put('G',  new Tuple(3, 2));
        tileConfig.put('B',  new Tuple(2, 3));
        tileConfig.put('C',  new Tuple(2, 3));
        tileConfig.put('M',  new Tuple(2, 3));
        tileConfig.put('P',  new Tuple(2, 3));
        tileConfig.put('F',  new Tuple(2, 4));
        tileConfig.put('H',  new Tuple(2, 4));
        tileConfig.put('V',  new Tuple(2, 4));
        tileConfig.put('W',  new Tuple(2, 4));
        tileConfig.put('Y',  new Tuple(2, 4));
        tileConfig.put('K',  new Tuple(1, 5));
        tileConfig.put('J',  new Tuple(1, 8));
        tileConfig.put('X',  new Tuple(1, 8));
        tileConfig.put('Q',  new Tuple(1, 10));
        tileConfig.put('Z',  new Tuple(1, 10));
    }

    public static int getTilePoints(Character s){
        return tileConfig.get(s).points;
    }

}