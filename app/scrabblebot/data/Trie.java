package scrabblebot.data;

import java.util.ArrayList;
import java.util.List;

public class Trie {

    TrieNode root = new TrieNode();

    public void addWord(String word){
        TrieNode currentNode = root;
        List<Character> chars =  convertStringToChars(word);
        for (int i = 0; i < chars.size(); i++){
            Character c = chars.get(i);
            if (!currentNode.hasChild(c)){
                currentNode = currentNode.addChild(c);
            } else {
                currentNode = currentNode.getChild(c);
            }
        }
        currentNode.setIsWord(true);
    }

    public TrieNode getNode(String word){
        TrieNode currentNode = root;
        List<Character> chars =  convertStringToChars(word);
        for (int i = 0; i < chars.size(); i++){
            Character c = chars.get(i);
            if (currentNode.hasChild(c)){
                currentNode = currentNode.getChild(c);
            }
            else {
                return null;
            }
        }
        return currentNode;
    }

    public TrieNode getRoot() {
        return root;
    }

    public boolean containsPrefix(String word) {
        return (getNode(word) != null && !getNode(word).isWord());
    }

    public boolean containsWord(String word) {
        return (getNode(word) != null && getNode(word).isWord());
    }

    private List<Character> convertStringToChars(String s){
        List<Character> chars = new ArrayList<>();
        for(char c : s.toCharArray())
            chars.add(c);
        return chars;
    }


}
