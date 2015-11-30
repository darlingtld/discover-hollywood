package hollywood.job;

import hollywood.pojo.JobStatus;
import hollywood.pojo.JobTracker;
import hollywood.service.JobTrackerService;
import hollywood.service.StatTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by lingda on 2015/11/29.
 */
@Component
public class TagProcessJob {
    private static final String TAG_PROCESS_JOB = "tag_process_job";

    @Autowired
    private JobTrackerService jobTrackerService;

    @Autowired
    private StatTagsService statTagsService;

    public void processTags4Movies() {
//        check whether the previous job has not finished
        JobTracker jobTracker = jobTrackerService.getLastTrackerByJob(TAG_PROCESS_JOB);
        if (jobTracker != null && jobTracker.getStatus().equals(JobStatus.RUNNING)) {
//            wait for the next trigger
            return;
        }
        int lastId = jobTracker == null ? 0 : jobTracker.getLastId() + 1;
        if (jobTracker != null && jobTracker.getLastId() == statTagsService.getMaxId()) {
//                skip
            return;
        }
//        mark the start of the job
        jobTracker = new JobTracker();
        jobTracker.setJob(TAG_PROCESS_JOB);
        jobTracker.setLastId(lastId);
        jobTracker.setStatus(JobStatus.RUNNING);
        jobTracker.setTimestamp(new Date());
        jobTrackerService.save(jobTracker);

//        do the job
        lastId = statTagsService.processTags(lastId);

//        job is done. mark it in database
        jobTracker.setLastId(lastId);
        jobTracker.setStatus(JobStatus.SUCCESS);

        jobTrackerService.update(jobTracker);
    }
}
