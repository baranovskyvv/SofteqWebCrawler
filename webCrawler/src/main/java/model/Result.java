package model;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Result implements Comparable<Result> {
    private int hits;
    private String link;
    private Map<String, Integer> searchWords;


    public Result(String link, Map<String, Integer> searchWords) {
        this.link = link;
        this.searchWords = searchWords;
        calculateHits();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Map<String, Integer> getSearchWords() {
        return searchWords;
    }

    public void setSearchWords(Map<String, Integer> searchWords) {
        this.searchWords = searchWords;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void calculateHits() {
        int hits = 0;
        for (int hit : this.searchWords.values()) {
            hits = hits + hit;
        }
        this.hits = hits;
    }



    @Override
    public int hashCode() {
        return Objects.hash(link);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return hits == result.hits &&
                Objects.equals(link, result.link) &&
                Objects.equals(searchWords, result.searchWords);
    }

    @Override
    public String toString() {
        return "Result{" +
                "hits=" + hits +
                ", link='" + link + '\'' +
                ", searchWords=" + searchWords +
                '}';
    }

    public String toCSV() {
        List<String> list=new ArrayList<>();
        list.add(this.link);
        for(int value:this.searchWords.values()){
            list.add(String.valueOf(value));
        }
        return String.join(",", list);
    }


    @Override
    public int compareTo(Result o) {
        return Integer.compare(this.hits, o.hits);
    }
}

