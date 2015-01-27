package scrabblebot.bot;

import scrabblebot.core.*;
import scrabblebot.data.RealList;
import scrabblebot.data.Trie;
import scrabblebot.data.TrieNode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RowBot {

    final Board board;
    final List<Tile> tileTiles;
    RealList<Character> tiles;
    Trie trie = ScrabbleTrie.INSTANCE.getTrie();

    public RowBot(Board b, List<Tile> tiles) {
        this.board = b;
        this.tileTiles = tiles;
        this.tiles = tilesToChars(tiles);
    }

    public Move getHighestScoringMove(){
        if (board.isEmpty())
            return highestScoringMoveFromList(getFirstMoveMoves());
        Move row = getHighestScoringRowMove();
        Move oblique = getHighestScoringObliqueMove();
        if (row.getScore() > oblique.getScore()) {
            return row;
        } else {
            return oblique;
        }
    }

    public Move getHighestScoringRowMove(){
        List<Move> allMoves = new ArrayList<>();
        allMoves.addAll(getAllRowMoves());
        allMoves.addAll(getAllColumnMoves());
        return highestScoringMoveFromList(allMoves);
    }

    public List<Move> getAllRowMoves(){
        List<Move> results = new ArrayList<>();
        for (int i = 0; i < Board.BOARD_SIZE; i++){
            final int j = i;
            RealList<Optional<Character>> row = boardRowToRowBotRow(board.getRow(j));
            Set<RowBotMove> moves = getAllMovesForRow(row, tiles);
            results.addAll(moves.stream()
                    .map(m -> new Move(board.clone(), j, m.start, Direction.ACROSS, m.word, tileTiles))
                    .collect(Collectors.toList()));
        }
        return results;
    }

    public List<Move> getAllColumnMoves(){
        List<Move> results = new ArrayList<>();
        for (int i = 0; i < Board.BOARD_SIZE; i++){
            final int j = i;
            RealList<Optional<Character>> row = boardRowToRowBotRow(board.getColumn(j));
            Set<RowBotMove> moves = getAllMovesForRow(row, tiles);
            results.addAll( moves.stream()
                    .map(m -> new Move(board.clone(),m.start,j,Direction.DOWN,m.word,tileTiles))
                    .collect(Collectors.toList()));
        }
        return results;
    }

    public Move getHighestScoringObliqueMove(){
        return highestScoringMoveFromList(getAllObliqueMoves());
    }

    public List<Move> getAllObliqueMoves(){
        List<Move> results = new ArrayList<>();
        for (BoardSpace s : getAllAnchorSquares()){
            results.addAll(getObliqueMovesForASpace(s));
        }
        return results;
    }

    public Set<Move> getObliqueMovesForASpace(BoardSpace sp){
        Set<Move> moves = new HashSet<>();
        if(board.getUpperNeighbor(sp).isPresent() && !board.getUpperNeighbor(sp).get().isOccupied()){
            RealList<Optional<Character>> boardRow = boardRowToRowBotRow(board.getColumn(sp.getCol()));
            for(int i = 0;i < tiles.size(); i++){
                Character c = tiles.get(i);
                RealList<Optional<Character>> sideWordRow = boardRow.replace(sp.getRow()-1, Optional.of(c));
                if (isInWord(sideWordRow, sp.getRow())){
                    RealList<Optional<Character>> obliqueWordRow = boardRowToRowBotRow(board.getRow(sp.getRow()-1)).replace(sp.getCol(), Optional.of(c));
                    Set<RowBotMove> rbmoves = getAllMovesForRow(obliqueWordRow, tiles.remove(i));
                    moves.addAll(rbmoves.stream()
                            .map(m -> new Move(board.clone(), sp.getRow()-1, m.start, Direction.ACROSS, m.word, tileTiles))
                            .collect(Collectors.toList()));
                }
            }
        }
        if(board.getLowerNeighbor(sp).isPresent() && !board.getLowerNeighbor(sp).get().isOccupied()){
            RealList<Optional<Character>> boardRow = boardRowToRowBotRow(board.getColumn(sp.getCol()));
            for(int i = 0;i < tiles.size(); i++){
                Character c = tiles.get(i);
                RealList<Optional<Character>> sideWordRow = boardRow.replace(sp.getRow()+1, Optional.of(c));
                if (isInWord(sideWordRow, sp.getRow())){
                    RealList<Optional<Character>> obliqueWordRow = boardRowToRowBotRow(board.getRow(sp.getRow()+1)).replace(sp.getCol(), Optional.of(c));
                    Set<RowBotMove> rbmoves = getAllMovesForRow(obliqueWordRow, tiles.remove(i));
                    moves.addAll(rbmoves.stream()
                            .map(m -> new Move(board.clone(), sp.getRow()+1, m.start, Direction.ACROSS, m.word, tileTiles))
                            .collect(Collectors.toList()));
                }
            }
        }
        if(board.getLeftNeighbor(sp).isPresent() && !board.getLeftNeighbor(sp).get().isOccupied()){
            RealList<Optional<Character>> boardRow = boardRowToRowBotRow(board.getRow(sp.getRow()));
            for(int i = 0;i < tiles.size(); i++){
                Character c = tiles.get(i);
                RealList<Optional<Character>> sideWordRow = boardRow.replace(sp.getCol()-1, Optional.of(c));
                if (isInWord(sideWordRow, sp.getCol())){
                    RealList<Optional<Character>> obliqueWordRow = boardRowToRowBotRow(board.getColumn(sp.getCol()-1)).replace(sp.getRow(), Optional.of(c));
                    Set<RowBotMove> rbmoves = getAllMovesForRow(obliqueWordRow, tiles.remove(i));
                    moves.addAll(rbmoves.stream()
                            .map(m -> new Move(board.clone(), m.start, sp.getCol()-1, Direction.DOWN, m.word, tileTiles))
                            .collect(Collectors.toList()));
                }
            }
        }
        if(board.getRightNeighbor(sp).isPresent() && !board.getRightNeighbor(sp).get().isOccupied()){
            RealList<Optional<Character>> boardRow = boardRowToRowBotRow(board.getRow(sp.getRow()));
            for(int i = 0;i < tiles.size(); i++){
                Character c = tiles.get(i);
                RealList<Optional<Character>> sideWordRow = boardRow.replace(sp.getCol()+1, Optional.of(c));
                if (isInWord(sideWordRow, sp.getCol())){
                    RealList<Optional<Character>> obliqueWordRow = boardRowToRowBotRow(board.getColumn(sp.getCol()+1)).replace(sp.getRow(), Optional.of(c));
                    Set<RowBotMove> rbmoves = getAllMovesForRow(obliqueWordRow, tiles.remove(i));
                    moves.addAll(rbmoves.stream()
                            .map(m -> new Move(board.clone(), m.start, sp.getCol()+1, Direction.DOWN, m.word, tileTiles))
                            .collect(Collectors.toList()));
                }
            }
        }
        return moves;
    }

    public List<BoardSpace> getAllAnchorSquares(){
        return board.getSpaces().stream()
                .flatMap(row -> row.stream())
                .filter(s-> isAnchorSquare(s))
                .collect(Collectors.toList());
    }

    Move highestScoringMoveFromList(List<Move> moves){
        moves = moves.stream()
                .filter(m -> m.checkMove())
                .collect(Collectors.toList());
        moves.stream().forEach(m -> m.makeMove());
        moves = moves.stream()
                .sorted((m1, m2) -> m2.getScore () - m1.getScore())
                .collect(Collectors.toList());
        return moves.get(0);
    }

    Set<RowBotMove> getAllMovesForRow(RealList<Optional<Character>> row, RealList<Character> tiles){
        List<Integer> inds = startableIndices(row);
        Set<RowBotMove> results = new HashSet<>();
        for (Integer i : inds){
            recursiveCalc(results, row, tiles, i, i, trie.getRoot(), "", false);
        }
        return results;
    }

    List<Move> getFirstMoveMoves(){
        List<Integer> inds = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14));
        RealList<Optional<Character>> row = convertStringToList("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
        Set<RowBotMove> rowBotMoves = new HashSet<>();
        List<Move> results = new ArrayList<>();
        for (Integer i : inds){
            recursiveCalc(rowBotMoves, row, tiles, i, i, trie.getRoot(), "", true);
        }
        results.addAll(rowBotMoves.stream()
                .map(m -> new Move(board.clone(),7, m.start, Direction.ACROSS, m.word, tileTiles))
                .collect(Collectors.toList()));
        return results;
    }

    boolean atEndOfWord(int i,RealList<Optional<Character>> row ){
        return (i == row.size()-1 ||
                (i < row.size()-1 && !row.get(i+1).isPresent())
        );
    }

    void recursiveCalc(Set<RowBotMove> moves, RealList<Optional<Character>> row, RealList<Character> tiles,
                       Integer start, Integer i,  TrieNode node, String partial, boolean tileTouched){
        if ( node.isWord() && tileTouched && atEndOfWord(i, row) )
            moves.add(new RowBotMove(start, partial));
        if (row.get(i).isPresent()){
            tileTouched = true;
            Character c = row.get(i).get();
            partial += c;
            // i++;
            if (node.hasChild(c)){
                node = node.getChild(c);
                if ( node.isWord() && tileTouched && atEndOfWord(i, row) )
                    moves.add(new RowBotMove(start, partial));
            } else {
                return;
            }
        }
        if ((tiles.isEmpty() && atEndOfWord(i, row)) || i >= Board.BOARD_SIZE-1)
            return;
        for(int j = 0; j < tiles.size();j++){
            Character c = tiles.get(j);
            if (node.hasChild(c)){
                //ImmutableList<Character> t = tiles.subList(j,tiles.size());
                String p = partial + c;
                RealList<Character> t = tiles.remove(j);
                recursiveCalc(moves, row, t, start, i+1, node.getChild(c), p, tileTouched);
            }
        }
        return;
    }

    List<Integer> startableIndices(RealList<Optional<Character>> row ){
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < row.size()-1;i++){
            if (row.get(i).isPresent()){
                if (i == 0 || !row.get(i-1).isPresent()){
                    indices.add(i);
                } else {
                    boolean add = IntStream.range(i, row.size() - 1)
                            .mapToObj(j -> row.get(j))
                            .anyMatch(o -> !o.isPresent());
                    if (add)
                        indices.add(i);
                }
            } else {
                if (i == 0 || !row.get(i-1).isPresent() ) {
                    boolean add = IntStream.range(i, i + 8) // 7 + 1 because range is exclusive
                            .filter(j -> j < row.size())
                            .mapToObj(j -> row.get(j))
                            .anyMatch(o -> o.isPresent());
                    if (add)
                        indices.add(i);
                }
            }
        }
        return indices;
    }

    boolean checkSideWord(BoardSpace space, Direction direction) {
        int row = space.getRow();
        int column = space.getCol();
        String sideWord = "";
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
            } else {
                break;
            }
        }
        return (trie.containsWord(sideWord));
    }

    RealList<Character> tilesToChars(List<Tile> tiles){
        List<Character> c = tiles.stream()
                .map(t -> t.getCharacter())
                .collect(Collectors.toList());
        return new RealList<>(c);
    }

    boolean isAnchorSquare(BoardSpace sp){
        return
                ( sp.isOccupied() &&
                        (board.getNeighbors(sp).stream()
                                .anyMatch(s -> !s.isOccupied())
                        )
                );
    }

    RealList<Optional<Character>> boardRowToRowBotRow(List<BoardSpace> spaces){
        List<Optional<Character>> results = new ArrayList<>();
        for (BoardSpace s : spaces){
            if (s.isOccupied()){
                results.add(Optional.of(s.getValue()));
            } else {
                results.add(Optional.empty());
            }
        }
        return new RealList<>(results);
    }

    boolean isInWord(RealList<Optional<Character>> row, int i){
        int j = i+1;
        String word = "";
        Optional<Character> current = row.get(i);
        if (!current.isPresent())
            return false;
        //prepend in the negative direction
        while (i >= 0 && current.isPresent()){
            word = current.get() + word;
            i--;
            if (i >= 0)
                current = row.get(i);
        }
        current = row.get(j);
        //append in the positive direction
        while (j <= row.size()-1 && current.isPresent()){
            word += current.get();
            j++;
            if(j <= row.size()-1)
                current = row.get(j);
        }
        return trie.containsWord(word);
    }

    RealList<Optional<Character>> convertStringToList(String s){
        List<Optional<Character>> chars = new ArrayList<>();
        for (char c : s.toCharArray()){
            if (c == ' '){
                continue;
            }
            else if (c == '_'){
                chars.add(Optional.empty());
            } else {
                chars.add(Optional.of(c));
            }
        }
        return new RealList<>(chars);
    }

}
