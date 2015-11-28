package hollywood.job;

import hollywood.IndexGenerator;
import hollywood.pojo.IndexGenTracker;
import hollywood.pojo.JobStatus;
import hollywood.service.IndexGenTrackerService;
import hollywood.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lingda on 2015/11/28.
 */
@Component
public class IndexGenJob {

    private static final String MOVIE_INDEX_JOB = "movie_index_job";
    @Autowired
    private IndexGenerator indexGenerator;

    @Autowired
    private IndexGenTrackerService indexGenTrackerService;

    @Autowired
    private MovieService movieService;

    public void createMovieIndex() {
        IndexGenTracker indexGenTracker = indexGenTrackerService.getLastTrackerByJob(MOVIE_INDEX_JOB);
        if (indexGenTracker != null && indexGenTracker.getStatus().equals(JobStatus.RUNNING)) {
//            wait for the next trigger
            return;
        }
        int lastId = indexGenTracker == null ? 0 : indexGenTracker.getLastId() + 1;
        if (indexGenTracker != null && indexGenTracker.getLastId() == movieService.getMaxMovieId()) {
//                skip
            return;
        }
        indexGenTracker = new IndexGenTracker();
        indexGenTracker.setJob(MOVIE_INDEX_JOB);
        indexGenTracker.setLastId(lastId);
        indexGenTracker.setStatus(JobStatus.RUNNING);
        indexGenTracker.setTimestamp(new Date());
        indexGenTrackerService.save(indexGenTracker);

        lastId = indexGenerator.createMovieIndex(lastId);

        indexGenTracker.setLastId(lastId);
        indexGenTracker.setStatus(JobStatus.SUCCESS);

        indexGenTrackerService.update(indexGenTracker);
    }
}
