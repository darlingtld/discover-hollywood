package hollywood.pojo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lingda on 2015/11/27.
 */
@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    private int id;
    @Column(name = "userId")
    private int userId;
    @Column(name = "movieId")
    private int movieId;
    @Column(name = "rating")
    private double rating;
    @Column(name = "timestamp")
    private Date timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

}
