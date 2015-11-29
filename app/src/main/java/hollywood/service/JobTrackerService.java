package hollywood.service;

import hollywood.dao.JobTrackerDao;
import hollywood.pojo.JobTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lingda on 2015/11/28.
 */
@Service
public class JobTrackerService {
    private static Logger logger = LoggerFactory.getLogger(JobTrackerService.class);

    @Autowired
    private JobTrackerDao jobTrackerDao;

    @Transactional
    public JobTracker getLastTrackerByJob(String job){
        logger.info("get last index gen tracker by job {}", job);
        return jobTrackerDao.getLastTrackerByJob(job);
    }

    @Transactional
    public int save(JobTracker jobTracker) {
        logger.info("save jobTracker {}", jobTracker);
        return jobTrackerDao.save(jobTracker);
    }

    @Transactional
    public void update(JobTracker jobTracker) {
        logger.info("update jobTracker {}", jobTracker);
        jobTrackerDao.update(jobTracker);
    }
}
