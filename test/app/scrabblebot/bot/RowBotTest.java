package app.scrabblebot.bot;

import app.scrabblebot.TestHelper;
import org.junit.Test;
import scrabblebot.bot.RowBot;
import scrabblebot.core.Direction;
import scrabblebot.core.Move;
import scrabblebot.core.Tile;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RowBotTest extends TestHelper {

    @Test
    public void testEmptyBoard(){
        List<Tile> tiles = tilesFromString("TPIDEAW");
        RowBot bot = new RowBot(emptyBoard, tiles);
        Move m = bot.getHighestScoringMove();
        assert(30 == m.getScore());
    }

    @Test
    public void testHighestScoringMove1(){
        List<Tile> tiles = tilesFromString("TPIDEAW");
        RowBot bot = new RowBot(twoWords, tiles);
        Move expected = new Move(twoWords, 8, 10, Direction.DOWN, "DAWTIE", tiles).process();
        Move actual = bot.getHighestScoringMove();
        assertEquals(expected, actual);
    }

    @Test
    public void testHighestScoringMove2(){
        List<Tile> tiles = tilesFromString("HJKLMOP");
        RowBot bot = new RowBot(twoWords, tiles);
        Move expected = new Move(twoWords, 5, 8, Direction.DOWN, "OKEH", tiles).process();
        Move actual = bot.getHighestScoringMove();
        assertEquals(expected, actual);
    }


    @Test
    public void testHighestScoringMove3(){
        List<Tile> tiles = tilesFromString("AELQRTU");
        RowBot bot = new RowBot(twoWords, tiles);
        Move expected = new Move(twoWords, 10, 4, Direction.ACROSS, "EQUATOR", tiles).process();
        Move actual = bot.getHighestScoringMove();
        assertEquals(expected, actual);
    }

    @Test
    public void testHighestScoringMove4(){
        List<Tile> tiles = tilesFromString("AADJKRU");
        RowBot bot = new RowBot(threeWords, tiles);
        Move expected = new Move(threeWords, 3, 8, Direction.DOWN, "JAUKED", tiles).process();
        Move actual = bot.getHighestScoringMove();
        assertEquals(expected, actual);
    }

    @Test
    public void testHighestScoringMove5(){
        List<Tile> tiles = tilesFromString("FGMOORS");
        RowBot bot = new RowBot(threeWords, tiles);
        Move expected = new Move(threeWords, 7, 14, Direction.DOWN, "GROOFS", tiles).process();
        Move actual = bot.getHighestScoringMove();
//        p(expected.getBoard());
//        p(actual.getBoard());
//        Set<RowBotMove> rbmoves = bot.getAllMovesForRow(
//                bot.convertStringToList("_ _ _ _ _ _ _ _ _ _ _ _ S _ _"),
//                bot.tilesToChars(tilesFromString("GROOFM"))
//        );
        assertEquals(expected, actual);
    }

    //threeWords + CEQRTWY
    @Test
    public void test6(){
        List<Tile> tiles = tilesFromString("CEQRTWY");
        RowBot bot = new RowBot(threeWords, tiles);
        Move expected = new Move(threeWords, 9, 13, Direction.DOWN, "QWERTY", tiles).process();
        Move actual = bot.getHighestScoringMove();
//        p(expected.getBoard());
//        p(actual.getBoard());
//        Set<RowBotMove> rbmoves = bot.getAllMovesForRow(
//                bot.convertStringToList("_ _ _ _ _ _ _ _ _ _ _ _ R _ _"),
//                bot.tilesToChars(tilesFromString("QWERTYC"))
//        );
        assertEquals(expected, actual);
    }

    //CDELNOR + threeWords
    @Test
    public void test7(){
        List<Tile> tiles = tilesFromString("CDELNOR");
        RowBot bot = new RowBot(threeWords, tiles);
        Move expected = new Move(threeWords, 10, 5, Direction.ACROSS, "CONDOLER", tiles).process();
        Move actual = bot.getHighestScoringMove();
        assertEquals(expected, actual);
    }

    //ADKORTU + threeWords
    @Test
    public void test8(){
        List<Tile> tiles = tilesFromString("ADKORTU");
        RowBot bot = new RowBot(threeWords, tiles);
        Move expected = new Move(threeWords, 3, 8, Direction.DOWN, "TROKED", tiles).process();
        Move actual = bot.getHighestScoringMove();
        assertEquals(expected.getScore(), actual.getScore());
    }

    //AILMNRT + threeWords
    @Test
    public void test9(){
        List<Tile> tiles = tilesFromString("TRAMLIN");
        RowBot bot = new RowBot(threeWords, tiles);
        Move expected = new Move(threeWords, 0, 7, Direction.DOWN, "TRAMLINE", tiles).process();
        Move actual = bot.getHighestScoringMove();
//        p(expected.getBoard());
//        p(actual.getBoard());
//        Set<RowBotMove> rbmoves = bot.getAllMovesForRow(
//                bot.convertStringToList("_ _ _ _ _ _ _ E _ _ _ _ _ _ _"),
//                bot.tilesToChars(tiles)
//        );
        assertEquals(expected, actual);
    }

    //fourWords + ABDFIOR
    @Test
    public void testFourWordsBoard_ABDFIOR() {
        List<Tile> tiles = tilesFromString("BBDFIOR");
        RowBot bot = new RowBot(fourWords, tiles);
        Move expected = new Move(fourWords, 9, 14, Direction.DOWN, "FORBID", tiles).process();
        Move actual = bot.getHighestScoringMove();
        assertEquals(expected, actual);
    }

    //fiveWords + AFILNRE
    @Test
    public void testFiveWordsBoard_AFILNRE() {
        List<Tile> tiles = tilesFromString("AFILNRE");
        RowBot bot = new RowBot(fiveWords, tiles);
        Move expected = new Move(fiveWords, 5, 0, Direction.ACROSS, "INFLAMER", tiles).process();
        Move actual = bot.getHighestScoringMove();
        assertEquals(expected, actual);
    }

    //AEGHIRT + fiveWords
    @Test
    public void testFiveWordsBoard_AEGHIRT() {
        List<Tile> tiles = tilesFromString("AEGHIRT");
        RowBot bot = new RowBot(fiveWords, tiles);
        Move expected = new Move(fiveWords, 5, 12, Direction.DOWN, "HERITAGE", tiles).process();
        Move actual = bot.getHighestScoringMove();
        assertEquals(expected, actual);
    }

    //EEIJNRV + sixWords
    @Test
    public void testSixWordsBoard_EEIJNRV() {
        List<Tile> tiles = tilesFromString("EEIJNRE");
        RowBot bot = new RowBot(fiveWords, tiles);
        Move expected = new Move(fiveWords, 9, 13, Direction.DOWN, "JEERER", tiles).process();
        Move actual = bot.getHighestScoringMove();
        assertEquals(expected, actual);
    }

}