package app.scrabblebot.bot;

import app.scrabblebot.TestHelper;
import org.junit.Test;
import scrabblebot.bot.ScrabbleTrie;
import scrabblebot.data.Trie;

public class ScrabbleTrieTest extends TestHelper {

    @Test
    public void testScrabbleTrie(){
        Trie t = ScrabbleTrie.INSTANCE.getTrie();
        assert(t.containsPrefix("CRAT"));
    }


}