package scrabblebot.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import scrabblebot.bot.ScrabbleTrie;
import scrabblebot.data.Trie;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Move takes
 * - a reference to a board state
 * - starting coordinates
 * - direction (DOWN or ACROSS)
 * - word
 * - rack of tiles
 *
 * checks if it is a valid move,
 * and scores it
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Move {

    @JsonIgnore
    private ArrayList<SideWord> sideWords = new ArrayList<SideWord>();

    private int score;
    @JsonProperty
    private final int row;
    @JsonProperty
    private final int column;
    @JsonProperty
    private final Direction direction;
    private final String word;
    private String errorMessage = null;
    @JsonIgnore
    protected final Board board;
    @JsonIgnore
    boolean intersectsExistingWord = false;
    @JsonIgnore
    private Trie d = ScrabbleTrie.INSTANCE.getTrie();
    @JsonIgnore
    private List<Tile> tiles;

    public static final String DEFAULT_ERR_MSG = "error";
    public static final String BEGINNING_OR_END_OF_WORD_PROBLEM = "there is a problem with the beginning or end of this word";
    public static final String WORD_TOO_LARGE = "word is too big for that spot.";
    public static final String CONFLICT_WITH_PLACED_TILE = "there is a conflict with an already placed tile";
    public static final String INVALID_WORD = "word is not valid in our lexicon";
    public static final String FIRST_MOVE_CENTER_TILE = "Error - the first move must touch the center tile (H,8).";
    public static final String FAILED_SIDEWORD_CHECK = "failed side word check at";

    public Move(Board board, int row, int column, Direction direction, String word, List<Tile> tiles) {
        this.row = row;
        this.column = column;
        this.word = word;
        this.direction = direction;
        this.board = board.clone();
        this.tiles = tiles;
    }


    public Move process(){
        checkMove();
        return makeMove();
    }


    private boolean validateBeginning(){
        if(direction == Direction.ACROSS){
            return (column == 0 || !board.getSpace(row, column-1).isOccupied());
        } else {
            return (row == 0 || !board.getSpace(row-1, column).isOccupied());
        }
    }

    public boolean checkMove() {
        //first check that it's a word
        if (!validateWord(word))
            return false;
        //if it's the first move make sure it touches the center tile
        if (!checkIfFirstMoveThatCenterTileIsTouched())
            return false;
        //make sure it doesn't adjoin an existing tile at the front (malformed / user error)
        if (!validateBeginning()){
            errorMessage = BEGINNING_OR_END_OF_WORD_PROBLEM;
            return false;
        }
        //then check that it will work
        boolean tilePlaced = false;
        List<Character> tileValues = getTileValues(tiles);
        //iterate over the proposed word / board spaces and check at each space/letter that it is possible
        for (int i = 0; i < word.length(); i++) {
            int x = row;
            int y = column;
            Character currentLetter = word.charAt(i);
            if (direction == Direction.ACROSS) {
                y = column + i;
            } else {
                x = row + i;
            }
            if (y >= Board.BOARD_SIZE || x >= Board.BOARD_SIZE) {
                errorMessage = WORD_TOO_LARGE;
                return false;
            }
            BoardSpace currentSpace = board.getSpace(x, y);
            boolean spaceOccupied = currentSpace.isOccupied();
            Character currentSpaceValue = currentSpace.getValue();
            // there is a letter on the space and it's *not* the right letter of the word we're checking
            if (spaceOccupied && !currentLetter.equals(currentSpaceValue)) {
                errorMessage = CONFLICT_WITH_PLACED_TILE;
                return false;
            }
            // there is a letter on the space and it *is* the right letter of the word we're checking
            else if (spaceOccupied && currentLetter.equals(currentSpaceValue)) {
                intersectsExistingWord = true;
                continue;
            }
            // the space is empty and player has a tile for the letter
            else if (!spaceOccupied && tileValues.contains(currentLetter)) {
                if (sideWord(currentLetter, x, y)) {
                    tileValues.remove(currentLetter);
                    tilePlaced = true;
                } else {
                    errorMessage = FAILED_SIDEWORD_CHECK;
                    return false;
                }
            }
            // the space is empty and player doesn't have a tile for the letter
            else if (!spaceOccupied && !tileValues.contains(currentLetter)) {
                return false;
            }
        }
        return (tilePlaced && ((board.isEmpty()) || intersectsExistingWord));
    }

    public Move makeMove() {
        int score = 0;
        int tilesPlaced = 0;
        int multiplicativeFactor = 1;
        for (int i = 0; i < word.length(); i++) {
            BoardSpace currentSpace = board.getSpace(row, column);
            try {
            if (direction == Direction.ACROSS) {
                currentSpace = board.getSpace(row, (column + i));
            } else {
                currentSpace = board.getSpace((row + i), column);
            }
            } catch (IndexOutOfBoundsException e) {
                System.out.print(this.toString());
            }
            Character currentSpaceValue = currentSpace.getValue();
            Character currentLetter = word.charAt(i);
            boolean spaceOccupied = currentSpace.isOccupied();
            if (!spaceOccupied) {
                BoardSpace.Type type = currentSpace.getType();
                currentSpace.setValue(currentLetter);
                tilesPlaced++;
                int p = TileConfig.getTilePoints(currentLetter);
                if (type == BoardSpace.Type.TRIPLE_LETTER) {
                    score += (p * 3);
                } else if (type == BoardSpace.Type.DOUBLE_LETTER) {
                    score += (p * 2);
                } else if (type == BoardSpace.Type.TRIPLE_WORD) {
                    multiplicativeFactor *= 3;
                    score += p;
                } else if (type == BoardSpace.Type.DOUBLE_WORD) {
                    multiplicativeFactor *= 2;
                    score += p;
                } else if (type == BoardSpace.Type.PLAIN) {
                    score += p;
                }
            } else if (spaceOccupied) {
                int p = TileConfig.getTilePoints(currentSpaceValue);
                score += p;
            }
        }
        score *= multiplicativeFactor;
        //is it a 'bingo'?  (must come after multiplying)
        if (tilesPlaced == 7) {
            score += 50;
        }
        for (SideWord s : sideWords) {
            score += s.getPoints();
        }
        this.score = score;
        return this;
    }

    public Integer scoreMove() {
        Board b = board.clone();
        int score = 0;
        int tilesPlaced = 0;
        int multiplicativeFactor = 1;
        for (int i = 0; i < word.length(); i++) {
            BoardSpace currentSpace = b.getSpace(row, column);
            try {
                if (direction == Direction.ACROSS) {
                    currentSpace = b.getSpace(row, (column + i));
                } else {
                    currentSpace = b.getSpace((row + i), column);
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.print(this.toString());
            }
            Character currentSpaceValue = currentSpace.getValue();
            Character currentLetter = word.charAt(i);
            boolean spaceOccupied = currentSpace.isOccupied();
            if (!spaceOccupied) {
                BoardSpace.Type type = currentSpace.getType();
                currentSpace.setValue(currentLetter);
                tilesPlaced++;
                int p = TileConfig.getTilePoints(currentLetter);
                if (type == BoardSpace.Type.TRIPLE_LETTER) {
                    score += (p * 3);
                } else if (type == BoardSpace.Type.DOUBLE_LETTER) {
                    score += (p * 2);
                } else if (type == BoardSpace.Type.TRIPLE_WORD) {
                    multiplicativeFactor *= 3;
                    score += p;
                } else if (type == BoardSpace.Type.DOUBLE_WORD) {
                    multiplicativeFactor *= 2;
                    score += p;
                } else if (type == BoardSpace.Type.PLAIN) {
                    score += p;
                }
                removeByChar(currentLetter);
            } else if (spaceOccupied) {
                int p = TileConfig.getTilePoints(currentSpaceValue);
                score += p;
            }
        }
        score *= multiplicativeFactor;
        //is it a 'bingo'?  (must come after multiplying)
        if (tilesPlaced == 7) {
            score += 50;
        }
        for (SideWord s : sideWords) {
            score += s.getPoints();
        }
        this.score = score;
        return score;
    }

    public Board getBoard() {
        return board;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getScore() {
        return score;
    }

    public String getWord() {
        return word;
    }

    private boolean sideWord(Character character, int row, int column) {
        String sideWord = Character.toString(character);
        int points = 0;
        // look in the positive direction starting one tile over, if occupied, append to side-word, continue
        int posInd;
        if (direction == Direction.ACROSS) {
            posInd = row + 1;
        } else {
            posInd = column + 1;
        }
        BoardSpace nextSpace;
        while (posInd < Board.BOARD_SIZE) {
            if (direction == Direction.ACROSS) {
                nextSpace = board.getSpace(posInd, column);
            } else {
                nextSpace = board.getSpace(row, posInd);
            }
            if (nextSpace.isOccupied()) {
                sideWord += nextSpace.getValue();
                points += TileConfig.getTilePoints(nextSpace.getValue());
                posInd++;
            } else {
                break;
            }
        }
        // look in the negative direction starting one tile over, if occupied, prepend to side-word, continue
        int negInd;
        if (direction == Direction.ACROSS) {
            negInd = row - 1;
        } else {
            negInd = column - 1;
        }
        while (negInd >= 0) {
            if (direction == Direction.ACROSS) {
                nextSpace = board.getSpace(negInd, column);
            } else {
                nextSpace = board.getSpace(row, negInd);
            }
            if (nextSpace.isOccupied()) {
                sideWord = nextSpace.getValue() + sideWord;
                negInd--;
                points += TileConfig.getTilePoints(nextSpace.getValue());
            } else {
                break;
            }
        }
        if (sideWord.length() == 1) {
            return true;
        } else if (sideWord.length() > 1 && !validateWord(sideWord) ) {
            return false;
        } else if (sideWord.length() > 1 && validateWord(sideWord) ) {
            //score the word
            BoardSpace.Type type = board.getSpace(row, column).getType();
            int multiplicativeFactor = 1;
            if (type == BoardSpace.Type.TRIPLE_WORD) {
                multiplicativeFactor = 3;
            } else if (type == BoardSpace.Type.DOUBLE_WORD) {
                multiplicativeFactor = 2;
            }
            int placedTileScore = TileConfig.getTilePoints(character);
            if (type == BoardSpace.Type.TRIPLE_LETTER) {
                placedTileScore *= 3;
            } else if (type == BoardSpace.Type.DOUBLE_LETTER) {
                placedTileScore *= 2;
            }
            points += placedTileScore;
            points *= multiplicativeFactor;
            sideWords.add(new SideWord(sideWord, points));
            intersectsExistingWord = true;
            return true;
        }
        return false;
    }

    private List<Character> getTileValues(List<Tile> tiles){
        return tiles.stream()
                .map(t -> t.getCharacter())
                .collect(Collectors.toList());
    }

    private boolean validateWord(String w){
        if (!d.containsWord(w)) {
            errorMessage = INVALID_WORD;
            return false;
        }
        return true;
    }

    private boolean checkIfFirstMoveThatCenterTileIsTouched(){
        if (board.isEmpty()) {
            if (direction == Direction.ACROSS) {
                if (!(row == 7 && column <= 7 && column + word.length() >= 7)) {
                    errorMessage = FIRST_MOVE_CENTER_TILE;
                    return false;
                }
            } else {
                if (!(column == 7 && row <= 7 && row + word.length() >= 7)) {
                    errorMessage = FIRST_MOVE_CENTER_TILE;
                    return false;
                }
            }
        }
        return true;
    }

    private void removeByChar(Character c){
        for(Tile t : tiles){
            if (t.getCharacter().equals(c)) {
                tiles.remove(t);
                break;
            }
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (column != move.column) return false;
        if (row != move.row) return false;
        if (!board.equals(move.board)) return false;
        if (direction != move.direction) return false;
        if (!tiles.equals(move.tiles)) return false;
        if (!word.equals(move.word)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        result = 31 * result + direction.hashCode();
        result = 31 * result + word.hashCode();
        result = 31 * result + board.hashCode();
        result = 31 * result + tiles.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Move{" +
                " score=" + score +
                ", row=" + row +
                ", column=" + column +
                ", direction=" + direction +
                ", word='" + word + '\'' +
                //", errorMessage='" + errorMessage + '\'' +
                //", intersectsExistingWord=" + intersectsExistingWord +
                //", tiles=" + tiles +
                '}';
    }

}