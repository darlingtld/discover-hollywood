package hollywood.service;

import hollywood.dao.MovieDao;
import hollywood.pojo.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lingda on 2015/11/27.
 */
@Service
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieDao movieDao;

    @Transactional
    public Movie getById(int movieId) {
        logger.info("get movie by id {}", movieId);
        return movieDao.getById(movieId);
    }
}
