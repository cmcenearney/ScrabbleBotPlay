package scrabblebot.core;

import org.junit.Test;

public class TileBagTest {


    @Test
    public void testRemoveByChar(){
        TileBag bag = new TileBag();
        int startSize =  bag.getTiles().size();
        long es = bag.getTiles().stream().filter(t -> t.getCharacter().equals('E')).count();
        bag.removeByChar('E');
        long esAfter = bag.getTiles().stream().filter(t -> t.getCharacter().equals('E')).count();
        int afterSize =  bag.getTiles().size();
        assert(es == esAfter + 1);
        assert(startSize == afterSize + 1);
    }

}