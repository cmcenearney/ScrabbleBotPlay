package scrabblebot.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardConfig {

    public static String plain = " _ ";
    public static String double_letter = " = ";
    public static String double_word = "[=]";
    public static String triple_letter = " ≡ ";
    public static String triple_word = "[≡]";

    public List<ArrayList<BoardSpace.Type>> getScrabbleStyle() {
        return scrabbleStyle;
    }

    public List<ArrayList<BoardSpace.Type>> scrabbleStyle = new ArrayList<>(15);

    public BoardConfig() {

        BoardSpace.Type[] row1 = { BoardSpace.Type.TRIPLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_WORD};
        BoardSpace.Type[] row2 = { BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN};
        BoardSpace.Type[] row3 = { BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN};
        BoardSpace.Type[] row4 = { BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER};
        BoardSpace.Type[] row5 = { BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN};
        BoardSpace.Type[] row6 = { BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_LETTER,  BoardSpace.Type.PLAIN};
        BoardSpace.Type[] row7 = { BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN};
        BoardSpace.Type[] row8 = { BoardSpace.Type.TRIPLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_WORD};
        BoardSpace.Type[] row9 = { BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN};
        BoardSpace.Type[] row10 = { BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_LETTER,  BoardSpace.Type.PLAIN};
        BoardSpace.Type[] row11 = { BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN};
        BoardSpace.Type[] row12 = { BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER};
        BoardSpace.Type[] row13 = { BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN};
        BoardSpace.Type[] row14 = { BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_WORD,  BoardSpace.Type.PLAIN };
        BoardSpace.Type[] row15 = { BoardSpace.Type.TRIPLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_WORD,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.DOUBLE_LETTER,  BoardSpace.Type.PLAIN,  BoardSpace.Type.PLAIN,  BoardSpace.Type.TRIPLE_WORD};

        scrabbleStyle.add(new ArrayList<BoardSpace.Type>(Arrays.asList(row1)));
        scrabbleStyle.add(new ArrayList<BoardSpace.Type>(Arrays.asList(row2)));
        scrabbleStyle.add(new ArrayList<BoardSpace.Type>(Arrays.asList(row3)));
        scrabbleStyle.add(new ArrayList<BoardSpace.Type>(Arrays.asList(row4)));
        scrabbleStyle.add(new ArrayList<BoardSpace.Type>(Arrays.asList(row5)));
        scrabbleStyle.add(new ArrayList<BoardSpace.Type>(Arrays.asList(row6)));
        scrabbleStyle.add(new ArrayList<BoardSpace.Type>(Arrays.asList(row7)));
        scrabbleStyle.add(new ArrayList<BoardSpace.Type>(Arrays.asList(row8)));
        scrabbleStyle.add(new ArrayList<BoardSpace.Type>(Arrays.asList(row9)));
        scrabbleStyle.add(new ArrayList<BoardSpace.Type>(Arrays.asList(row10)));
        scrabbleStyle.add(new ArrayList<BoardSpace.Type>(Arrays.asList(row11)));
        scrabbleStyle.add(new ArrayList<BoardSpace.Type>(Arrays.asList(row12)));
        scrabbleStyle.add(new ArrayList<BoardSpace.Type>(Arrays.asList(row13)));
        scrabbleStyle.add(new ArrayList<BoardSpace.Type>(Arrays.asList(row14)));
        scrabbleStyle.add(new ArrayList<BoardSpace.Type>(Arrays.asList(row15)));


    }

}

