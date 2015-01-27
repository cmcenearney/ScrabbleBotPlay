package scrabblebot.data;

import java.util.HashMap;
import java.util.Map;


public class TrieNode{
    boolean isWord = false;
    private Map<Character, TrieNode> children;

    public TrieNode() {
        children = new HashMap<>();
    }

    public boolean hasChild(Character c){
        return children.containsKey(c);
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public boolean isWord() {
        return isWord;
    }

    public void setIsWord(boolean isWord) {
        this.isWord = isWord;
    }

    public TrieNode addChild(Character c){
        children.put(c, new TrieNode());
        return getChild(c);
    }

    public TrieNode getChild(Character c){
        return children.get(c);
    }
}