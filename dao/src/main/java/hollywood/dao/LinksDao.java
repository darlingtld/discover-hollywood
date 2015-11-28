package hollywood.dao;

import hollywood.pojo.Links;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by lingda on 2015/11/28.
 */
@Repository
public class LinksDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Links links) {
        sessionFactory.getCurrentSession().save(links);
    }

    public Links getByMovieId(int movieId) {
        return (Links) sessionFactory.getCurrentSession().get(Links.class, movieId);
    }
}
