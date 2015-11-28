package hollywood.pojo;

import javax.persistence.*;

/**
 * Created by lingda on 2015/11/27.
 */
@Entity
@Table(name="movies")
public class Movie {
    @javax.persistence.Id
    @Column(name="movieId")
    private int movieId;
    @Column(name = "title")
    private String title;
    @Column(name="genres")
    private String genres;

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", genres='" + genres + '\'' +
                '}';
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}
