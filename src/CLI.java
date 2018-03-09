import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Kelsey McKenna
 */
public class CLI {

    /**
     * Loads words (lines) from the given file and inserts them into
     * a dictionary.
     *
     * @param f the file from which the words will be loaded
     * @return the dictionary with the words loaded from the given file
     * @throws IOException if there was a problem opening/reading from the file
     */
    static DictionaryTree loadWords(File f) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String word;
            DictionaryTree d = new DictionaryTree(Optional.empty(), Optional.empty());
            int popularity = 0;
//            String [] twoWords = {"hello", "hi", "hamz", "al", "m", "k", "kz"};
////            for (String w : twoWords){
////                popularity = popularity+1;
////                d.insert(w, popularity);
////            }
            while ((word = reader.readLine()) != null) {
                popularity = popularity+1;
                d.insert(word,popularity);
            }

            return d;
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.print("Loading dictionary ... ");
        DictionaryTree d = loadWords(new File(args[0]));
        System.out.println("done");
        System.out.println("size: " + d.size());
        System.out.println("height: " + d.height());
        System.out.println("longest word: " + d.longestWord());
        System.out.println("contains al? : " + d.contains("al"));
        System.out.println("number of leaves: " + d.numLeaves());
        System.out.println("maximum braching: " + d.maximumBranching());
        List<String> all = d.allWords();
        System.out.println();
        System.out.println("Number of words in the arraylist = " + all.size());
        for (String w : all){
            System.out.print(w + ", ");
        }
        System.out.println("");

        d.remove("al");
        System.out.println("Just removed the word al");
        List<String> afterRemoval = d.allWords();
        System.out.println();
        System.out.println("Number of words in the arraylist after removal= " + afterRemoval.size());
        for (String w : afterRemoval){
            System.out.print(w + ", ");
        }
        System.out.println();
        System.out.println();

        System.out.println("Predictions for ph: ");
        List<String> predictions = d.predict("ph", 5);
        for (String w : predictions){
            System.out.print(w + ", ");
        }

        System.out.println("Enter prefixes for prediction below.");

        try (BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("---> " + d.predict(fromUser.readLine()));
            }
        }
    }

}
