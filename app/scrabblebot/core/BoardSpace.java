package scrabblebot.core;

public class BoardSpace {

    enum Type {
        DOUBLE_LETTER, DOUBLE_WORD, TRIPLE_LETTER, TRIPLE_WORD, PLAIN
    }

    final Type type;
    final int row;
    final int col;
    private Character value = null;
    private boolean occupied = false;

    public BoardSpace(Type type, int row, int col) {
        this.type = type;
        this.row = row;
        this.col = col;
    }

    public Character getValue() {
        return value;
    }

    public void setValue(Character value) {
        this.value = value;
        if (value != null){
            this.occupied = true;
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Type getType() {
        return type;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardSpace that = (BoardSpace) o;

        if (col != that.col) return false;
        if (occupied != that.occupied) return false;
        if (row != that.row) return false;
        if (type != that.type) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + row;
        result = 31 * result + col;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (occupied ? 1 : 0);
        return result;
    }
}
