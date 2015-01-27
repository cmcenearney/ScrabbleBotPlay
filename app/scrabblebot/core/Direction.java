package scrabblebot.core;

public enum Direction {

    ACROSS,DOWN;

    public static Direction getFromString(String s){
        if(s.equals("ACROSS")){
            return ACROSS;
        } else if (s.equals("DOWN")){
            return DOWN;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
