package hollywood.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by lingda on 2015/11/27.
 */
@Entity
@Table(name = "links")
public class Links {
    @javax.persistence.Id
    @Column(name = "movieId")
    private int movieId;
    @Column(name = "imbdId")
    private int imbdId;
    @Column(name = "tmbdId")
    private int tmbdId;

    @Override
    public String toString() {
        return "Links{" +
                "movieId=" + movieId +
                ", imbdId=" + imbdId +
                ", tmbdId=" + tmbdId +
                '}';
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getImbdId() {
        return imbdId;
    }

    public void setImbdId(int imbdId) {
        this.imbdId = imbdId;
    }

    public int getTmbdId() {
        return tmbdId;
    }

    public void setTmbdId(int tmbdId) {
        this.tmbdId = tmbdId;
    }
}
