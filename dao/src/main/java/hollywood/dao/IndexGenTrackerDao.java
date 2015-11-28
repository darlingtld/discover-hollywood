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

    public int save(IndexGenTracker indexGenTracker) {
        return (int) sessionFactory.getCurrentSession().save(indexGenTracker);
    }

    public IndexGenTracker getLastTrackerByJob(String job) {
        return (IndexGenTracker) sessionFactory.getCurrentSession().createQuery(String.format("from IndexGenTracker where job = '%s' order by timestamp desc", job)).setMaxResults(1).uniqueResult();
    }

    public void update(IndexGenTracker indexGenTracker) {
        sessionFactory.getCurrentSession().update(indexGenTracker);
    }
}
