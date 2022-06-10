import java.util.ArrayList;

// Class of Movies for storing them into a object.
public class Movie {
    private String title;
    private Long year;
    private ArrayList<String> genre;
    private Long runTime;
    private Double rating;
    private Long votes;
    private String director;
    private String cast;
    private Double gross;

    public Movie(String title, Long year, ArrayList<String> genre, Long runTime, Double rating, Long votes, String director, String cast, Double gross) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.runTime = runTime;
        this.rating = rating;
        this.votes = votes;
        this.director = director;
        this.cast = cast;
        this.gross = gross;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public Long getRunTime() {
        return runTime;
    }

    public void setRunTime(Long runTime) {
        this.runTime = runTime;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public Double getGross() {
        return gross;
    }

    public void setGross(Double gross) {
        this.gross = gross;
    }
}
