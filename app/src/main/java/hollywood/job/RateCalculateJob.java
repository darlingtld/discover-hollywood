package hollywood.job;

import hollywood.pojo.JobStatus;
import hollywood.pojo.JobTracker;
import hollywood.service.JobTrackerService;
import hollywood.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lingda on 2015/11/29.
 */
@Component
public class RateCalculateJob {
    private static final String HIGHEST_RATE_CALCULATE_JOB = "highest_rate_calculate_job";

    @Autowired
    private JobTrackerService jobTrackerService;

    @Autowired
    private RatingService ratingService;

    public void calculateRatings4Movies() {
//        check whether the previous job has not finished
        JobTracker jobTracker = jobTrackerService.getLastTrackerByJob(HIGHEST_RATE_CALCULATE_JOB);
        if (jobTracker != null && jobTracker.getStatus().equals(JobStatus.RUNNING)) {
//            wait for the next trigger
            return;
        }
        int lastId = jobTracker == null ? 0 : jobTracker.getLastId() + 1;
        if (jobTracker != null && jobTracker.getLastId() == ratingService.getMaxId()) {
//                skip
            return;
        }
//        mark the start of the job
        jobTracker = new JobTracker();
        jobTracker.setJob(HIGHEST_RATE_CALCULATE_JOB);
        jobTracker.setLastId(lastId);
        jobTracker.setStatus(JobStatus.RUNNING);
        jobTracker.setTimestamp(new Date());
        jobTrackerService.save(jobTracker);

//        do the job
        lastId = ratingService.calculateRatings(lastId);

//        job is done. mark it in database
        jobTracker.setLastId(lastId);
        jobTracker.setStatus(JobStatus.SUCCESS);

        jobTrackerService.update(jobTracker);
    }
}
