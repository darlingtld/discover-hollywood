package hollywood.pojo;

import javax.persistence.*;

/**
 * Created by lingda on 2015/11/27.
 */
@Entity
@Table(name = "links")
public class Links {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movieId")
    private int movieId;
    @Column(name = "imbdId")
    private int imbdId;
    @Column(name = "tmbdId")
    private int tmbdId;

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
