package hollywood.dao;

import hollywood.pojo.StatTags;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lingda on 2015/11/28.
 */
@Repository
public class StatTagsDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(StatTags statTags) {
        sessionFactory.getCurrentSession().save(statTags);
    }

    public StatTags getStatTagsByMovieId(int movieId) {
        return (StatTags) sessionFactory.getCurrentSession().createQuery(String.format("from StatTags where movieId = %d", movieId)).uniqueResult();
    }

    public void update(StatTags statTagsInDB) {
        sessionFactory.getCurrentSession().update(statTagsInDB);
    }

    public List<StatTags> getMostTaggedMovies(int limit) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from StatTags order by count desc")).setMaxResults(limit).list();
    }
}