package hollywood.service;

import hollywood.dao.RatingDao;
import hollywood.pojo.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lingda on 2015/11/28.
 */
@Service
public class RatingService {
    private Logger logger = LoggerFactory.getLogger(RatingService.class);

    @Autowired
    private RatingDao ratingDao;

    @Transactional
    public void save(Rating rating) {
        ratingDao.save(rating);
    }
}
