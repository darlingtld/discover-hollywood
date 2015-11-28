package hollywood.dao;

import hollywood.pojo.Tag;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by lingda on 2015/11/28.
 */
@Repository
public class TagDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Tag tag) {
        sessionFactory.getCurrentSession().save(tag);
    }
}
