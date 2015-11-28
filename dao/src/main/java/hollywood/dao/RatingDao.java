package hollywood.dao;

import hollywood.pojo.Rating;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
