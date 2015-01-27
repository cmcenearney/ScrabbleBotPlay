package app.scrabblebot;

import com.google.common.collect.Lists;
import com.google.common.primitives.Chars;
import org.junit.Test;
import scrabblebot.core.Board;
import scrabblebot.core.Tile;
import scrabblebot.core.TileConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestHelper {

    public Board emptyBoard = parseBoardFixture("test/resources/board_states/empty.txt");
    public Board fullOfA = parseBoardFixture("test/resources/board_states/full_of_a.txt");
    public Board oneA = parseBoardFixture("test/resources/board_states/one_a.txt");
    public Board twoWords = parseBoardFixture("test/resources/board_states/two_words.txt");
    public Board threeWords = parseBoardFixture("test/resources/board_states/three_words.txt");
    public Board fourWords = parseBoardFixture("test/resources/board_states/four_words.txt");
    public Board fiveWords = parseBoardFixture("test/resources/board_states/five_words.txt");

    public void p(Object... args){
        String s = "";
        for (Object o : args){
            s += o.toString();
        }
        System.out.println(s);
    }

    public Board parseBoardFixture(String path){
        Board b = new Board();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (int i = 0; i < 15; i++){
                //char[] vals = lines.get(i).toCharArray(); //split(" ");
                List<Character> chars = Lists.newArrayList(Chars.asList(lines.get(i).toCharArray()));
                chars = chars.stream().filter(c -> !c.equals(' ')).collect(Collectors.toList());
                // List<Character> chars = new ArrayList<Character>(Arrays.);
                if (chars.size() < 15){
                    throw new IllegalArgumentException("yer board aint big enough");
                }
                for (int j = 0; j < 15; j++){
                    Character c = chars.get(j);
                    if (c.equals('_'))
                        c = null;
                    b.getSpace(i,j).setValue(c);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return b;
    }

    public List<Tile> tilesFromString(String s){
        List<Tile> tiles = new ArrayList<>();
        //String[] letters = s.split("");
        TileConfig tc = new TileConfig();
        for (char l : s.toCharArray()){
            int points = tc.getTilePoints(l);
            tiles.add(new Tile(l, points));
        }
        return tiles;
    }

    @Test
    public void testParseBoardFixture() {
        String empty = "test/resources/board_states/empty.txt";
        String fullOfA = "test/resources/board_states/full_of_a.txt";
        String oneA = "test/resources/board_states/one_a.txt";
        Board b = parseBoardFixture(empty);
        assertTrue(b.isEmpty());

        b = parseBoardFixture(fullOfA);
        assertFalse(b.isEmpty());
        assertTrue(
                b.getSpaces().stream()
                        .flatMap(r -> r.stream())
                        .allMatch(s -> s.getValue().equals('A'))
        );

        b = parseBoardFixture(oneA);
        assertFalse(b.isEmpty());
        assertTrue(b.getSpace(7,7).getValue().equals('A'));
    }

}

