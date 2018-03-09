public class Word {
    private String word;
    private int popularity;

    public Word (String word, int popularity){
        this.word = word;
        this.popularity = popularity;
    }

    public String getWord() {
        return word;
    }

    public int getPopularity() {
        return popularity;
    }
}
