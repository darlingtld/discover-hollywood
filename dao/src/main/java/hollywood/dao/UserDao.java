package hollywood.dao;

import hollywood.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by lingda on 2015/11/27.
 */
@Repository
public class UserDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    public User getById(String id) {
        Query query = new Query(Criteria.where("userId").is(id));
        return mongoTemplate.findOne(query, User.class);
    }

    public User getByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query, User.class);
    }
}
