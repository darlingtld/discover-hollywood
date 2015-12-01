package hollywood.dao;

import hollywood.PropertyHolder;
import hollywood.pojo.Movie;
import hollywood.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
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
    private static AtomicInteger userIdGenerator = new AtomicInteger(PropertyHolder.MOCK_INITIAL_USERID);

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    private void init() {
        Query query = new BasicQuery("{}").with(new Sort(new Sort.Order(Sort.Direction.DESC, "userId"))).limit(1);
        User user = mongoTemplate.findOne(query, User.class);
        if (user != null) {
            userIdGenerator = new AtomicInteger(user.getUserId() + 1);
        }
    }

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

    public List<User> getAll() {
        return mongoTemplate.findAll(User.class);
    }

    public void addRecommendMovieList(int userId, List<Movie> movieList) {
        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update();
        update.set("recommendMovieList", movieList);
        mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), User.class);
    }

    public void addRatedMovieList(int userId, Movie movie) {
        User user = getById(userId);
        List<Movie> ratedMovieList = user.getRatedMovieList();
        if (ratedMovieList != null) {
            ratedMovieList.add(movie);
        } else {
            ratedMovieList = new ArrayList<>();
            ratedMovieList.add(movie);
        }
        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update();
        update.set("ratedMovieList", ratedMovieList);
        mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), User.class);
    }

    public void addTaggedMovieList(int userId, Movie movie) {
        User user = getById(userId);
        List<Movie> taggedMovieList = user.getTaggedMovieList();
        if (taggedMovieList != null) {
            taggedMovieList.add(movie);
        } else {
            taggedMovieList = new ArrayList<>();
            taggedMovieList.add(movie);
        }
        Query query = new Query(Criteria.where("userId").is(userId));
        Update update = new Update();
        update.set("taggedMovieList", taggedMovieList);
        mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), User.class);
    }
}
