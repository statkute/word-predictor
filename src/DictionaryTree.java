import java.util.*;
import java.util.function.BiFunction;

public class DictionaryTree {

    private Map<Character, DictionaryTree> children = new LinkedHashMap<>();
    public DictionaryTree parent;
    public Boolean endOfWord = false; //Does this Node mark the end of a particular word?
    public boolean visited;
    public int popularity;


    /**
     * Inserts the given word into this dictionary.
     * If the word already exists, nothing will change.
     *
     * @param word the word to insert
     */
    void insert(String word) {
        if (!children.containsKey(word.charAt(0))) {
            children.put(word.charAt(0), new DictionaryTree());
        }

        if (word.length() > 1) {
            insertWord(word.substring(1), children.get(word.charAt(0)));
        }

        //throw new RuntimeException("DictionaryTree.insert not implemented yet");
    }

    //Recursive method that inserts a new word into the trie tree.
    private void insertWord(String word, DictionaryTree child) {
        final DictionaryTree childTree;
        if (child.children.containsKey(word.charAt(0))) {
            childTree = child.children.get(word.charAt(0));
        } else {
            childTree = new DictionaryTree();
            child.children.put(word.charAt(0), childTree);
        }
        if (word.length() == 1) {
            childTree.endOfWord = true;
            return;
        } else {
            insertWord(word.substring(1), childTree);
        }
    }


    /**
     * Inserts the given word into this dictionary with the given popularity.
     * If the word already exists, the popularity will be overriden by the given value.
     *
     * @param word       the word to insert
     * @param popularity the popularity of the inserted word
     */
    void insert(String word, int popularity) {
        if (!children.containsKey(word.charAt(0))) {
            children.put(word.charAt(0), new DictionaryTree());
        }

        if (word.length() > 1) {
            insertWord(word.substring(1), children.get(word.charAt(0)), popularity);
        } else {
            this.popularity = popularity;
        }

    }

    //Recursive method that inserts a new word into the trie tree.
    private void insertWord(String word, DictionaryTree child, int popularity) {
        final DictionaryTree childTree;
        if (child.children.containsKey(word.charAt(0))) {
            childTree = child.children.get(word.charAt(0));
        } else {
            childTree = new DictionaryTree();
            child.children.put(word.charAt(0), childTree);
        }
        if (word.length() == 1) {
            childTree.endOfWord = true;
            childTree.popularity = popularity;
            return;
        } else {
            insertWord(word.substring(1), childTree);
        }
    }

    /**
     * Removes the specified word from this dictionary.
     * Returns true if the caller can delete this node without losing
     * part of the dictionary, i.e. if this node has no children after
     * deleting the specified word.
     *
     * @param word the word to delete from this dictionary
     * @return whether or not the parent can delete this node from its children
     */
    boolean remove(String word) {
        throw new RuntimeException("DictionaryTree.remove not implemented yet");
    }

    /**
     * Determines whether or not the specified word is in this dictionary.
     *
     * @param word the word whose presence will be checked
     * @return true if the specified word is stored in this tree; false otherwise
     */
    boolean contains(String word) {
        boolean characterFound = false;
        DictionaryTree child = null;

        for (Map.Entry<Character, DictionaryTree> e : children.entrySet()){
            if (e.getKey() == word.charAt(0)){
                characterFound = true;
                child = e.getValue();
                break;
            }
        }

        if (characterFound){
            return containsHelper(word.substring(1), child);
        }
        else {
            return false;
        }
        //throw new RuntimeException("DictionaryTree.contains not implemented yet");
    }

    boolean containsHelper (String word, DictionaryTree child){
        boolean characterFound = false;
        DictionaryTree childTemp = null;

        for (Map.Entry<Character, DictionaryTree> e : child.children.entrySet()){
            if (e.getKey() == word.charAt(0)){
                characterFound = true;
                child = e.getValue();
                break;
            }
        }

        if (word.length() == 1){
            if (characterFound){
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if (characterFound){
                return containsHelper(word.substring(1), child);
            }
            else {
                return false;
            }
        }

    }
    /**
     * @param prefix the prefix of the word returned
     * @return a word that starts with the given prefix, or an empty optional
     * if no such word is found.
     */
    Optional<String> predict(String prefix) {
        throw new RuntimeException("DictionaryTree.predict not implemented yet");
    }

    /**
     * Predicts the (at most) n most popular full English words based on the specified prefix.
     * If no word with the specified prefix is found, an empty Optional is returned.
     *
     * @param prefix the prefix of the words found
     * @return the (at most) n most popular words with the specified prefix
     */
    List<String> predict(String prefix, int n) {
        throw new RuntimeException("DictionaryTree.predict not implemented yet");
    }

    /**
     * @return the number of leaves in this tree, i.e. the number of words which are
     * not prefixes of any other word.
     */
    int numLeaves() {
        DictionaryTree child = null;
        int numberOfLeaves = 0;
        for (Map.Entry<Character, DictionaryTree> e : children.entrySet()){
            child = e.getValue();
            System.out.println("GOING DOWN THE NODE OF: " + e.getKey());
            if (!child.children.isEmpty()) {
                numberOfLeaves += (numLeavesHelper(child, numberOfLeaves));
            }
            else{
                numberOfLeaves++;
            }
        }
        return numberOfLeaves;
        // throw new RuntimeException("DictionaryTree.numLeaves not implemented yet");
    }

    int numLeavesHelper(DictionaryTree child, int numberOfLeaves) {
        DictionaryTree newChild = null;
        for (Map.Entry<Character, DictionaryTree> e : child.children.entrySet()){
            newChild = e.getValue();
            System.out.println("GOING DOWN THE NODE OF: " + e.getKey());
            System.out.println("Number of leaves before adding anything: " + numberOfLeaves);
            if (newChild.children.isEmpty()){
                System.out.println("LEAVE: YES");
                numberOfLeaves++;
            }
            else{
                System.out.println("LEAVE: NO");
                int temp = (numLeavesHelper(newChild, numberOfLeaves)) - numberOfLeaves;
                numberOfLeaves += temp;
            }
            System.out.println("Number of AFTER the addition by "+ e.getKey() +": " + numberOfLeaves);
        }
        return  numberOfLeaves;
        //throw new RuntimeException("DictionaryTree.numLeaves not implemented yet");
    }

    /**
     * @return the maximum number of children held by any node in this tree
     */
    int maximumBranching() {
        throw new RuntimeException("DictionaryTree.maximumBranching not implemented yet");
    }

    /**
     * @return the height of this tree, i.e. the length of the longest branch
     */
    int height() {
        int height = -1;

        for (Map.Entry<Character, DictionaryTree> e : children.entrySet())
            height = Math.max(height, e.getValue().height());

        return 1 + height;

        //throw new RuntimeException("DictionaryTree.height not implemented yet");
    }

    /**
     * @return the number of nodes in this tree
     */
    int size() {
        int size = 1;

        for (Map.Entry<Character, DictionaryTree> child : children.entrySet())
            size += child.getValue().size();

        return size;

        //throw new RuntimeException("DictionaryTree.size not implemented yet");
    }

    /**
     * @return the longest word in this tree
     */
    String longestWord() {
        int maxHeight = height();
        String longestWord = "";
        boolean foundRightBranch = false;
        DictionaryTree longestBranch = null;
        for (Map.Entry<Character, DictionaryTree> e : children.entrySet()) {
            if (e.getValue().height() == maxHeight - 1) {
                foundRightBranch = true;
                longestBranch = e.getValue();
                longestWord += e.getKey();
            }
        }

        if (foundRightBranch) {
            if (maxHeight >= 2) {
                longestWord = longestWord(longestBranch, longestWord);
            } else {
                return longestWord;
            }
        }

        return longestWord;
        //throw new RuntimeException("DictionaryTree.longestWord not implemented yet");
    }

    String longestWord(DictionaryTree longestBranch, String word) {
        if (longestBranch.height() == 1) {
            for (Map.Entry<Character, DictionaryTree> e : longestBranch.children.entrySet()) {
                word += e.getKey();
            }
            return word;
        }

        boolean foundBranch = false;
        DictionaryTree branchToSearch = null;
        char letter = '\0';
        int maxHeight = 0;
        for (Map.Entry<Character, DictionaryTree> e : longestBranch.children.entrySet()) {
            if (e.getValue().height() > maxHeight) {
                maxHeight = e.getValue().height();
                foundBranch = true;
                branchToSearch = e.getValue();
                letter = e.getKey();
            }
        }

        word += letter;

        if (maxHeight > 0) {
            return longestWord(branchToSearch, word);
        } else {
            return word;
        }
    }


    /**
     * @return all words stored in this tree as a list
     */
    List<String> allWords() {
        throw new RuntimeException("DictionaryTree.allWords not implemented yet");
    }

    /**
     * Folds the tree using the given function. Each of this node's
     * children is folded with the same function, and these results
     * are stored in a collection, cResults, say, then the final
     * result is calculated using f.apply(this, cResults).
     *
     * @param f   the summarising function, which is passed the result of invoking the given function
     * @param <A> the type of the folded value
     * @return the result of folding the tree using f
     */
    <A> A fold(BiFunction<DictionaryTree, Collection<A>, A> f) {
        throw new RuntimeException("DictionaryTree.fold not implemented yet");
    }


}
