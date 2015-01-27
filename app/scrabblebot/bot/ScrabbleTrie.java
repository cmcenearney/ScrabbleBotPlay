package scrabblebot.bot;

import scrabblebot.data.Trie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public enum ScrabbleTrie {

    INSTANCE;
    private Trie trie;

    public Trie getTrie() {
        return trie;
    }

    ScrabbleTrie(){
        trie = new Trie();
        initialize();
    }

    private void initialize(){
        File file = new File("resources/sowpods.txt");
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.length() > 0) {
                    trie.addWord(line.toUpperCase());
                }
            }
        }
        catch (IOException e){
            System.out.println("problem reading dictionary file:" + e);
        }
    }
}