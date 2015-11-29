package hollywood.dao;

import hollywood.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lingda on 2015/11/27.
 */
@Repository
public class UserDao {
    //    just mock some data
    private static final AtomicInteger userIdGenerator = new AtomicInteger(21051128);

    @Autowired
    private MongoTemplate mongoTemplate;

    public User getById(int id) {
        Query query = new Query(Criteria.where("userId").is(id));
        return mongoTemplate.findOne(query, User.class);
    }

    public User getByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query, User.class);
    }

    public User saveUser(String username, String password) {
        User user = new User();
        user.setUserId(userIdGenerator.getAndIncrement());
        user.setUsername(username);
        user.setPassword(password);
        mongoTemplate.save(user);
        return getByUsername(username);
    }

    public User addFavouriteGenresList(int userId, List<String> favGenresList) {
        User user = getById(userId);
        List<String> userGenresList = user.getFavouriteGenresList();
        if (userGenresList != null) {
            Set<String> genresSet = new HashSet<>();
            genresSet.addAll(userGenresList);
            genresSet.addAll(favGenresList);
            favGenresList = new ArrayList<>(genresSet);
        }
        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update();
        update.set("favouriteGenresList", favGenresList);
        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), User.class);
    }
}
