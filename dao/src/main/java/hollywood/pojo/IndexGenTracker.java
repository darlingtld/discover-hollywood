package hollywood.pojo;

import javax.persistence.*;

/**
 * Created by lingda on 2015/11/28.
 */
@Entity
@Table(name = "index_gen_tracker")
public class IndexGenTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "job")
    private String job;
    @Column(name = "last_id")
    private int lastId;
    @Column(name = "status")
    private String status;
    @Column(name = "timestamp")
    private String timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

