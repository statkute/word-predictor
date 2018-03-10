import java.util.*;

public class Word implements Comparator<Word>{
    private String word;
    private int popularity;

    public Word (String word, int popularity){
        this.word = word;
        this.popularity = popularity;
    }

    public Word (){
    }

    public String getWord() {
        return word;
    }

    public int getPopularity() {
        return popularity;
    }

    public int compare(Word d, Word d1) {
        return d.popularity - d1.popularity;
    }


}
