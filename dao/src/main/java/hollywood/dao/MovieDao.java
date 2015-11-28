package hollywood.dao;

import hollywood.pojo.Movie;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by lingda on 2015/11/27.
 */
@Repository
public class MovieDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Movie getById(int movieId) {
        return (Movie) sessionFactory.getCurrentSession().get(Movie.class, movieId);
    }

    public void save(Movie movie) {
        sessionFactory.getCurrentSession().save(movie);
    }
}
