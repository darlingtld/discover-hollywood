package hollywood.service;

import hollywood.dao.AvgRatingDao;
import hollywood.dao.RatingDao;
import hollywood.pojo.AvgRating;
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

    @Transactional
    public void save(Rating rating) {
        ratingDao.save(rating);
    }

    @Transactional
    public int getMaxId() {
        return ratingDao.getMaxId();
    }

    @Transactional
    public int calculateRatings(int lastId) {
        logger.info("calculate ratings - average score.  starting from ratings id {}", lastId);
        int id = lastId;
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
}
