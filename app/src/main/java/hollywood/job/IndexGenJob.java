package hollywood.job;

import hollywood.IndexGenerator;
import hollywood.pojo.JobStatus;
import hollywood.pojo.JobTracker;
import hollywood.service.JobTrackerService;
import hollywood.service.MovieService;
import hollywood.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lingda on 2015/11/28.
 */
@Component
public class IndexGenJob {

    private static final String MOVIE_INDEX_JOB = "movie_index_job";

    private static final String TAG_INDEX_JOB = "tag_index_job";

    @Autowired
    private IndexGenerator indexGenerator;

    @Autowired
    private JobTrackerService jobTrackerService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private TagService tagService;

    public void createMovieIndex() {
        JobTracker jobTracker = jobTrackerService.getLastTrackerByJob(MOVIE_INDEX_JOB);
        if (jobTracker != null && jobTracker.getStatus().equals(JobStatus.RUNNING)) {
//            wait for the next trigger
            return;
        }
        int lastId = jobTracker == null ? 0 : jobTracker.getLastId() + 1;
        if (jobTracker != null && jobTracker.getLastId() == movieService.getMaxMovieId()) {
//                skip
            return;
        }
        jobTracker = new JobTracker();
        jobTracker.setJob(MOVIE_INDEX_JOB);
        jobTracker.setLastId(lastId);
        jobTracker.setStatus(JobStatus.RUNNING);
        jobTracker.setTimestamp(new Date());
        jobTrackerService.save(jobTracker);

        lastId = indexGenerator.createMovieIndex(lastId);

        jobTracker.setLastId(lastId);
        jobTracker.setStatus(JobStatus.SUCCESS);

        jobTrackerService.update(jobTracker);
    }

    public void createTagIndex() {
        JobTracker jobTracker = jobTrackerService.getLastTrackerByJob(TAG_INDEX_JOB);
        if (jobTracker != null && jobTracker.getStatus().equals(JobStatus.RUNNING)) {
//            wait for the next trigger
            return;
        }
        int lastId = jobTracker == null ? 0 : jobTracker.getLastId() + 1;
        if (jobTracker != null && jobTracker.getLastId() == tagService.getMaxTagId()) {
//                skip
            return;
        }
        jobTracker = new JobTracker();
        jobTracker.setJob(TAG_INDEX_JOB);
        jobTracker.setLastId(lastId);
        jobTracker.setStatus(JobStatus.RUNNING);
        jobTracker.setTimestamp(new Date());
        jobTrackerService.save(jobTracker);

        lastId = indexGenerator.createTagIndex(lastId);

        jobTracker.setLastId(lastId);
        jobTracker.setStatus(JobStatus.SUCCESS);

        jobTrackerService.update(jobTracker);
    }
}
