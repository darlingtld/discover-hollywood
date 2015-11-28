package hollywood.service;

import hollywood.dao.IndexGenTrackerDao;
import hollywood.pojo.IndexGenTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lingda on 2015/11/28.
 */
@Service
public class IndexGenTrackerService {
    private static Logger logger = LoggerFactory.getLogger(IndexGenTrackerService.class);

    @Autowired
    private IndexGenTrackerDao indexGenTrackerDao;

    @Transactional
    public IndexGenTracker getLastTrackerByJob(String job){
        logger.info("get last index gen tracker by job {}", job);
        return indexGenTrackerDao.getLastTrackerByJob(job);
    }

    @Transactional
    public int save(IndexGenTracker indexGenTracker) {
        logger.info("save indexGenTracker {}", indexGenTracker);
        return indexGenTrackerDao.save(indexGenTracker);
    }

    @Transactional
    public void update(IndexGenTracker indexGenTracker) {
        logger.info("update indexGenTracker {}", indexGenTracker);
        indexGenTrackerDao.update(indexGenTracker);
    }
}
