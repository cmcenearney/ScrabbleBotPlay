package scrabblebot.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Board {

    public static final int BOARD_SIZE = 15;
    private List<ArrayList<BoardSpace>> spaces = new ArrayList<ArrayList<BoardSpace>>(BOARD_SIZE);

    public Board(){
        BoardConfig config = new BoardConfig();
        for (int row = 0; row < BOARD_SIZE; row++){
            spaces.add(row, new ArrayList<BoardSpace>(15));
            for (int col = 0; col < BOARD_SIZE; col++){
                BoardSpace.Type type = config.getScrabbleStyle().get(row).get(col);
                BoardSpace newSpace = new BoardSpace(type, row, col);
                spaces.get(row).add(col, newSpace);
            }
        }
    }

    public List<ArrayList<BoardSpace>> getSpaces(){
        return this.spaces;
    }

    public void setSpaces(ArrayList<ArrayList<BoardSpace>> s){
        spaces = s;
    }

    public Board setSpacesFromChars(List<List<Character>> charList){
        for (int row = 0; row < BOARD_SIZE; row++){
            for (int col = 0; col < BOARD_SIZE; col++){
                Character c = charList.get(row).get(col);
                BoardSpace s = getSpace(row, col);
                s.setValue(c);
            }
        }
        return this;
    }

    public BoardSpace getSpace(int row, int column){
        return this.spaces.get(row).get(column);
    }

    public ArrayList<BoardSpace> getRow(int row){
        return spaces.get(row);
    }

    public ArrayList<BoardSpace> getColumn(int col){
        ArrayList<BoardSpace> column = new ArrayList<BoardSpace>(15);
        for (int row=0; row < BOARD_SIZE; row++) {
            BoardSpace space = this.getSpace(row,col);
            column.add(row, space);
        }
        return column;
    }

    public boolean isEmpty(){
        return spaces.stream()
                .flatMap(row -> row.stream())
                .allMatch(s -> !s.isOccupied());
    }
    
    public List<BoardSpace> getNeighbors(BoardSpace sp){
        List<BoardSpace> n = new ArrayList<>();
        int row = sp.row;
        int col = sp.col;
        if (col > 0 && col < (BOARD_SIZE-1)) {
            n.add(getSpace(row, col-1));
            n.add(getSpace(row, col+1));
        } else if (col == 0){
            n.add(getSpace(row, col+1));
        } else if (col == BOARD_SIZE-1){
            n.add(getSpace(row, col -1));
        }
        if (row > 0 && row < (BOARD_SIZE-1)) {
            n.add(getSpace(row-1, col));
            n.add(getSpace(row+1, col));
        } else if (row == 0){
            n.add(getSpace(row+1, col));
        } else if (row == BOARD_SIZE-1){
            n.add(getSpace(row-1, col));
        }
        return n;
    }

    public Optional<BoardSpace> getLeftNeighbor(BoardSpace sp){
        if (sp.getCol() > 0) {
            return Optional.of(getSpace(sp.getRow(), sp.getCol() - 1));
        } else {
            return Optional.empty();
        }
    }

    public Optional<BoardSpace> getRightNeighbor(BoardSpace sp){
        if (sp.getCol() < BOARD_SIZE-1) {
            return Optional.of(getSpace(sp.getRow(), sp.getCol() + 1));
        } else {
            return Optional.empty();
        }
    }

    public Optional<BoardSpace> getUpperNeighbor(BoardSpace sp){
        if (sp.getRow() > 0) {
            return Optional.of(getSpace(sp.getRow()-1, sp.getCol()));
        } else {
            return Optional.empty();
        }
    }

    public Optional<BoardSpace> getLowerNeighbor(BoardSpace sp){
        if (sp.getRow() < BOARD_SIZE-1) {
            return Optional.of(getSpace(sp.getRow()+1, sp.getCol()));
        } else {
            return Optional.empty();
        }
    }

    public Board clone(){
        Board newB = new Board();
        for (int row = 0; row < BOARD_SIZE; row++){
            for (int col = 0; col < BOARD_SIZE; col++){
                BoardSpace parent = spaces.get(row).get(col);
                BoardSpace newSpace = newB.getSpaces().get(row).get(col);
                newSpace.setValue(parent.getValue());
                newSpace.setOccupied(parent.isOccupied());
            }
        }
        return newB;
    }

    @Override
    public String toString() {
        String out = "";
        for(ArrayList<BoardSpace> row : spaces){
            for (BoardSpace s : row){
                if (s.isOccupied()) {
                    out += " " + s.getValue() + " ";
                } else {
                    out += " _ ";
                }
            }
            out += "\n";
        }
        return "Board {\n" +
                out +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (spaces != null ? !spaces.equals(board.spaces) : board.spaces != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return spaces != null ? spaces.hashCode() : 0;
    }
}
