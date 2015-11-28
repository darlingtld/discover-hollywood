package hollywood.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by lingda on 2015/11/27.
 */
@Entity
@Table(name = "movies")
public class Movie {
    @javax.persistence.Id
    @Column(name = "movieId")
    private int movieId;
    @Column(name = "title")
    private String title;
    @Column(name = "genres")
    private String genres;
    @Column(name = "posterUrl")
    private String posterUrl;
    @Transient
    private String movieUrl;
    @Transient
    private String imbdUrl;
    @Transient
    private String tmbdUrl;

    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", genres='" + genres + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", movieUrl='" + movieUrl + '\'' +
                ", imbdUrl='" + imbdUrl + '\'' +
                ", tmbdUrl='" + tmbdUrl + '\'' +
                '}';
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public void setMovieUrl(String movieUrl) {
        this.movieUrl = movieUrl;
    }

    public String getImbdUrl() {
        return imbdUrl;
    }

    public void setImbdUrl(String imbdUrl) {
        this.imbdUrl = imbdUrl;
    }

    public String getTmbdUrl() {
        return tmbdUrl;
    }

    public void setTmbdUrl(String tmbdUrl) {
        this.tmbdUrl = tmbdUrl;
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
