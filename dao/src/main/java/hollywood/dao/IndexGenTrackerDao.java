package hollywood.dao;

import hollywood.pojo.IndexGenTracker;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by lingda on 2015/11/28.
 */
@Repository
public class IndexGenTrackerDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(IndexGenTracker indexGenTracker) {
        sessionFactory.getCurrentSession().save(indexGenTracker);
    }

    public void getLastTrackerByJob(String job) {
        sessionFactory.getCurrentSession().createQuery(String.format("from IndexGenTracker where job = '%s' order by timestamp desc", job)).setMaxResults(1).uniqueResult();
    }
}
