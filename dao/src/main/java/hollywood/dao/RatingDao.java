package hollywood.dao;

import hollywood.pojo.Rating;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lingda on 2015/11/28.
 */
@Repository
public class RatingDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Rating rating) {
        sessionFactory.getCurrentSession().save(rating);
    }

    public int getMaxId() {
        return ((Number) sessionFactory.getCurrentSession().createSQLQuery("select max(id) from ratings").uniqueResult()).intValue();
    }

    public List<Rating> getRatingsByIdRange(int fromId, int toId) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Rating where id >= %d and id <=%d", fromId, toId)).list();
    }

    public Rating getRatingByMovieIdAndUserId(int movieId, int userId) {
        return (Rating) sessionFactory.getCurrentSession().createQuery(String.format("from Rating where movieId=%d and userId=%d", movieId, userId)).uniqueResult();
    }

    public List<Object[]> getRatingTrendByMovieId(int movieId) {
        List<Object[]> resultList = sessionFactory.getCurrentSession().createSQLQuery(String.format("SELECT avg(rating), year(timestamp) y FROM ratings where movieId=%d GROUP BY year(timestamp) order by y", movieId)).list();
        return resultList;
    }
}
