package scrabblebot.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/*
singleton implementations
 - pros and cons vs going all static?
*/
public enum Dictionary {

    INSTANCE;

    private HashSet<String> dictionary = new HashSet<String>();

    private Dictionary() {
        this.createDictionary();
    }

    //methods
    private void createDictionary() {
        File file = new File("resources/sowpods.txt");
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.length() > 0) {
                    this.dictionary.add(line.toUpperCase());
                }
            }
        }
        catch (IOException e){
            System.out.println("problem reading dictionary file:" + e);
        }
    }

    public boolean validWord(String word){
        return dictionary.contains(word.toUpperCase());
    }

}




