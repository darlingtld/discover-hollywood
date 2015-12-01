package hollywood.dao;

import hollywood.pojo.Tag;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public int getMaxId() {
        return ((Number) sessionFactory.getCurrentSession().createSQLQuery("select max(id) from tags").uniqueResult()).intValue();
    }

    public List<Tag> getTagsByIdRange(int fromId, int toId) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Tag where id >= %d and id <=%d", fromId, toId)).list();
    }

    public Tag getTagsByMovieIdAndUserId(int movieId, int userId) {
        return (Tag) sessionFactory.getCurrentSession().createQuery(String.format("from Tag where movieId=%d and userId=%d", movieId, userId)).uniqueResult();

    }

    public List<Tag> getByPagination(int start, int count) {
        return sessionFactory.getCurrentSession().createQuery(String.format("from Tag")).setFirstResult(start).setMaxResults(count).list();

    }
}
