# Explanation of 'Word predictor' functionality

You can find my code here: https://git.cs.bham.ac.uk/gxs746/sww-assessed-2.git

The ```DictionaryTree``` class includes following variables:

- ```Map<Character, DictionaryTree> children``` - stores the keys of the current node's children and their subtrees
- ```Optional <String> fullWord``` - if this is the last node of the word, this variable stores the full word the node is a part of
- ```Optional <Integer> popularity``` - if this is the last node of the word, this variable stores the popularity of that word

#### Insertion

The words are inserted using the function ```insert (String word)``` or ```insert (String word, int popularity)```. Then they both, if the word has more than one letter, calls the helper funciton ```insertWord (String word, DictionaryTree child, Optional <Integer> popularity, String fullWord)```.

Both functions first check if the ```word```'s, given as a parameter, first letter is already in the tree. If it is not, a new children to the root node is created -> a new entry to the hash table ```children``` is added with a key of the first letter of the word and an empty ```DictionaryTree``` with according ```fullWord``` and ```popularity```  values. The lenght of the ```word``` is also checked and if it is more than one, the ```insertWord``` helper function is called for the rest of the word that does the same thing recusrsively until it has done that for all of the letters in the word.

#### LongestWord

The ```longestWord()``` method uses a recursive helper method ```longestWord(DictionaryTree longestBranch, String word)``` to find the longest word in the tree. To do that, ```longestWord()``` finds the height of the whole tree and then finds the subtree of that tree that has a height of one less, meaning that that subtree has height that is more or equal to all others - has the longest word . The the helper method goes down the path of that longest branch and builds up the string by adding chars of nodes going down that longest branch.

#### Contains

The ```contains(String word)``` method uses a recursive helper method ```containsHelper (String word)``` to find out if the tree contains the given word. If the none of the root nodes have the first letter of the word as a key, the method ```returns false```. If it is found, however, the method looks if ```fullWord``` value of that node is present and if the lenght of the searched word is one, that would mean that the word exists and would ```return true```. In another case, where the lenght of the word is 1 but the ```fullWord``` value of that node is not present, the method would ```return false```. And finally, if the first letter is present and the word has a lenght of more than one, the ```contains(String word)``` calls the helper method that recursively does the same operations and ```returns true``` if the word is found and ```false``` if it is not.

#### All words

```Java
List<String> allWords() {
    ArrayList<Word> wordInfoList= new ArrayList<Word>();
    List<Word> allWordInfo = allWords(wordInfoList);

    List <String> wordsOnly = new ArrayList<String>();

    for (Word w : allWordInfo){
        wordsOnly.add(w.getWord());
    }
    return  wordsOnly;
}
```

The ```List<String> allWords()``` method uses a recursive helper method ``` List<Word> allWords(List<Word> allWordList)``` to find all of the words stored in the tree.

To do that, objects of class ```Word``` are used. They only store the ```String word``` and ```int popularity```. 

The helper method goes down the tree and creates a new word for each node whose ```fullWord``` value is present and returns a ```List<Word>```.

The method ```allWords()``` stores that list of Word objects and gets the values of the word of each object and stores it in a ```List<String>``` and returns it.

#### Remove

```Java
if (!contains(word)){
        return false;
    }
    else{
        for (Map.Entry<Character, DictionaryTree> e : children.entrySet()){
            if (e.getKey().equals(word.charAt(0))){
                List<Word> allPredictions = e.getValue().allWords(new ArrayList<Word>());
                int temp = 0;
                for (int i = 0; i < allPredictions.size(); i ++){
                    if (allPredictions.get(i).getWord().equals(word)){
                        temp = i;
                    }
                }
                
                allPredictions.remove(temp);
                children.remove(e.getKey());
                
                for (Word w : allPredictions){
                    insert(w.getWord(), w.getPopularity());
                }
                break;
            }
        }
    }
    return true;
}
```

The ```boolean remove (String word)``` method returns false if the ```contains(word)``` returns false (meaning that the word that is asked to be deleted is not in the tree). If the word is in the tree, the method finds a subtree that the word is in (based on the first letter in the word) and gets all the words in it using ```List <Word> allWords (List<Word> allWordList)```. Then the word is removed from that returned list and the whone subtree of the first letter of that word is deleted from the tree. Then, the list of Words is looped and each word inserted back in with the ```insert()``` function.

#### Predict

##### Predict (String prefix)

The ```Optional<String> predict(String prefix)``` method uses a recursive helper method ```List<Word> predictHelper(String prefix, List<Word> allWords)``` to find all of the words stored in the subtree.
subtree of that prefix. Then it stores that ```List<Words>``` and sorts it based on the word popularity and returns an optional type of the most popular word if atleast one prediction exists. If not, it returns ```optional.empty()```.

* If a word has not been assigned popularity, it is considered to be the least popular word of all.

##### Predict (String prefix, int n)

The ```List<String> predict (String prefix, int n)``` method uses a recursive helper method ```List<Word> predictHelper(String prefix, List<Word> allWords)``` to find all of the words stored in the subtree.
subtree of that prefix. Then it stores that ```List<Words>``` and sorts it based on the word popularity and returns n most popular words (if there are less than n word predictions, returns ```List<String>``` of the number of words found).

* If a word has not been assigned popularity, it is considered to be the least popular word of all.

#### Fold

```Java
<A> A fold(BiFunction<DictionaryTree, Collection<A>, A> f) {
    Collection<A> list = new ArrayList<A>();

    for (Map.Entry<Character, DictionaryTree> e : children.entrySet()){
        DictionaryTree tree = e.getValue();
        list.add(tree.fold(f));
    }

    return f.apply(this, list);
}
```

The fold method applies a given function to each node of the tree, and returns a collection of the results.


##### Size using fold

```Java
    if (children.isEmpty()){
        return 0;
    }
    return fold((t,c) -> {
        int size = 1;
        for (int a : c){
            size += a;
        }
        return size;
    });
```

The method makes each node of the tree worth 1 using the fold method and returns the total sum of all the values of the nodes (1 for each node resulting in the size). However, if the root node has no children (meninng that the tree is empty) it returns 0.


##### Height using fold

```Java
int height() {
    return fold((t,c) -> {
        int height = -1;
        for (int a : c){
            height = Math.max(height, a);
        }
        return height+1;
    });
}
```

The method makes each node of the tree worth -1 using the fold method and returns the maximum value of each subtree +1 (resulting in the height of the tree).

##### Maximum branching using fold

```Java
int maximumBranching() {
    return fold((t,c) -> {
        int branching = 0;
        for (int a : c){
            branching = Math.max(branching, c.size());
        }
        return branching;
    });
}
```

The method sets branching to 0 and then calculates the maximum number of elements of a found in each collection (children of a subtree) and updates the height value if that number of elements is higher. Therefore, the method returns the maximum number of children nodes one node in the tree has - maximum branching.

##### Number of leaves using fold

```Java
int numLeavesFold() {
    return fold((t,c) -> {
        int leaves;
    
        if (c.isEmpty()){
            leaves = 1;
        } else {
            leaves = 0;
        }
            
        for (int a : c){
            leaves += a;
        }
        return leaves;
    });
}
```

The method sets the value of the node to be 1 if it has no children (meaning that it is a leaf node) and to 0 if it has at least one child node. Then it sums up the values of all the nodes resulting in the number of leaves as they are the only ones with values not equal to 0.


##### A comment on the advantages/disadvantages of using a tree for predicting multiple words with ranked popularities

Advantages: Searching a tree is quicker than many other data structures such as lists, and having it ranked by popularities makes it even quicker .
Disadvantages: If the words the user is searching for have the lowest popularity it can take a longer time to find.
