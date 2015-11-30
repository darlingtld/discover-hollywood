package hollywood.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by lingda on 2015/11/30.
 */
@Entity
@Table(name = "stat_tags")
public class StatTags {

    @javax.persistence.Id
    private int movieId;
    @Column(name = "tags")
    private String tags;
    @Column(name = "count")
    private int count;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
