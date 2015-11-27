package hollywood.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by lingda on 2015/11/27.
 */
@Entity
@Table(name = "ratings")
public class Rating {
    @Column(name = "userId")
    private int userId;
    @Column(name = "movieId")
    private int movieId;
    @Column(name = "userId")
    private double rating;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "timestamp")
    private Date timestamp;

}
