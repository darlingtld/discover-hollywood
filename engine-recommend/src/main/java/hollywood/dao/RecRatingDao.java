package hollywood.dao;

import hollywood.sqlfactory.HollywoodSqlFactory;
import org.grouplens.lenskit.data.sql.JDBCRatingDAO;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

/**
 * Created by lingda on 2015/11/29.
 */
@Repository
public class RecRatingDao {
    @Autowired
    private SessionFactory sessionFactory;

    public JDBCRatingDAO getJDBCRatingDao() {
        SessionImpl sessionImpl = (SessionImpl) sessionFactory.getCurrentSession();
        Connection conn = sessionImpl.connection();
        JDBCRatingDAO dao = new JDBCRatingDAO(conn, new HollywoodSqlFactory());
        return dao;
    }
}
