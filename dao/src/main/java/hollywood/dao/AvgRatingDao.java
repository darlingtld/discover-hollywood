package hollywood.dao;

import hollywood.pojo.AvgRating;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lingda on 2015/11/28.
 */
@Repository
public class AvgRatingDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(AvgRating rating) {
        sessionFactory.getCurrentSession().save(rating);
    }

    public AvgRating getRatingByMovieId(int movieId) {
        return (AvgRating) sessionFactory.getCurrentSession().createQuery(String.format("from AvgRating where movieId = %d", movieId)).uniqueResult();
    }

    public void update(AvgRating avgRatingInDB) {
        sessionFactory.getCurrentSession().update(avgRatingInDB);
    }

    public List<AvgRating> getHighestRatedMovies(int limit) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from AvgRating order by avgRating desc")).setMaxResults(limit).list();
    }
}
