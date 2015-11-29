package hollywood.pojo;

import javax.persistence.*;

/**
 * Created by lingda on 2015/11/29.
 */
@Entity
@Table(name = "avg_ratings")
public class AvgRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "movieId")
    private int movieId;
    @Column(name = "avg_rating")
    private double avgRating;
    @Column(name = "sum_rating")
    private double sumRating;
    @Column(name = "sample_count")
    private int sampleCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public double getSumRating() {
        return sumRating;
    }

    public void setSumRating(double sumRating) {
        this.sumRating = sumRating;
    }

    public int getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }
}
