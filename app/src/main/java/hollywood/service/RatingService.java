package hollywood.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hollywood.dao.AvgRatingDao;
import hollywood.dao.MovieDao;
import hollywood.dao.RatingDao;
import hollywood.dao.UserDao;
import hollywood.pojo.AvgRating;
import hollywood.pojo.Movie;
import hollywood.pojo.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lingda on 2015/11/28.
 */
@Service
public class RatingService {
    private Logger logger = LoggerFactory.getLogger(RatingService.class);

    @Autowired
    private RatingDao ratingDao;

    @Autowired
    private AvgRatingDao avgRatingDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MovieDao movieDao;

    @Transactional
    public void save(Rating rating) {
//        save this rating to the whole ratings table
        ratingDao.save(rating);
//        save this rating to user rated movie list
        Movie movie = movieDao.getById(rating.getMovieId());
        movie.setAvgRating(rating.getRating());
        userDao.addRatedMovieList(rating.getUserId(), movie);
    }

    @Transactional
    public int getMaxId() {
        return ratingDao.getMaxId();
    }

    /**
     * main function to calculate ratings.  generate average ratings, total ratings and rating counts
     * it shall combine with the previous result to make sure its accuracy
     *
     * @param lastId
     * @return
     */
    @Transactional
    public int calculateRatings(int lastId) {
        logger.info("calculate ratings - average score.  starting from ratings id {}", lastId);
        int id = lastId;
//        the step count
        int limit = 500;
        HashMap<Integer, AvgRating> avgRatingHashMap = new HashMap<>();
        List<Rating> ratingList = ratingDao.getRatingsByIdRange(id, id + limit);
        for (Rating rating : ratingList) {
            if (avgRatingHashMap.containsKey(rating.getMovieId())) {
                AvgRating avgRating = avgRatingHashMap.get(rating.getMovieId());
                avgRating.setSampleCount(avgRating.getSampleCount() + 1);
                avgRating.setSumRating(avgRating.getSumRating() + rating.getRating());
            } else {
                AvgRating avgRating = new AvgRating();
                avgRating.setMovieId(rating.getMovieId());
                avgRating.setSumRating(rating.getRating());
                avgRating.setSampleCount(1);
                avgRatingHashMap.put(rating.getMovieId(), avgRating);
            }

        }
        for (Integer key : avgRatingHashMap.keySet()) {
            AvgRating avgRating = avgRatingHashMap.get(key);
//                check whether the avg rating for this movie exits
            AvgRating avgRatingInDB = avgRatingDao.getRatingByMovieId(avgRating.getMovieId());
            if (avgRatingInDB != null) {
                avgRatingInDB.setSampleCount(avgRatingInDB.getSampleCount() + avgRating.getSampleCount());
                avgRatingInDB.setSumRating(avgRatingInDB.getSumRating() + avgRating.getSumRating());
                avgRatingInDB.setAvgRating(avgRatingInDB.getSumRating() / avgRatingInDB.getSampleCount());
                avgRatingDao.update(avgRatingInDB);
            } else {
                avgRating.setAvgRating(avgRating.getSumRating() / avgRating.getSampleCount());
                avgRatingDao.save(avgRating);
            }
        }

        return ratingList.get(ratingList.size() - 1).getId();
    }

    @Transactional
    public Rating getRatingByMovieIdAndUserId(int movieId, int userId) {
        logger.info("get rating by movie id {} and user id {}", movieId, userId);
        return ratingDao.getRatingByMovieIdAndUserId(movieId, userId);
    }

    @Transactional
    public JSONArray getRatingTrendByMovieId(int movieId) {
        logger.info("get rating trend of movie id {}", movieId);
        List<Object[]> resultList = ratingDao.getRatingTrendByMovieId(movieId);
        JSONArray dataset = new JSONArray();
        for (Object[] object : resultList) {
            JSONObject data = new JSONObject();
            data.put("rating", object[0]);
            data.put("year", object[1]);
            dataset.add(data);
        }
        return dataset;
    }
}
