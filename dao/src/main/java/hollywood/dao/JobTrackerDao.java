package hollywood.dao;

import hollywood.pojo.JobTracker;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by lingda on 2015/11/28.
 */
@Repository
public class JobTrackerDao {

    @Autowired
    private SessionFactory sessionFactory;

    public int save(JobTracker jobTracker) {
        return (int) sessionFactory.getCurrentSession().save(jobTracker);
    }

    public JobTracker getLastTrackerByJob(String job) {
        return (JobTracker) sessionFactory.getCurrentSession().createQuery(String.format("from JobTracker where job = '%s' order by timestamp desc", job)).setMaxResults(1).uniqueResult();
    }

    public void update(JobTracker jobTracker) {
        sessionFactory.getCurrentSession().update(jobTracker);
    }
}
