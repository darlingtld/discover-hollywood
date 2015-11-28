package hollywood.dao;

import hollywood.pojo.Movie;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Movie> getByPagination(int start, int count) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Movie")).setFirstResult(start).setMaxResults(count).list();
    }

    public int getMaxMovieId() {
        return ((Number) sessionFactory.getCurrentSession().createSQLQuery(String.format("select max(movieId) from movies")).uniqueResult()).intValue();

    }
}
