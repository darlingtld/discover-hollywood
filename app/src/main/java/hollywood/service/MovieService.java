package hollywood.service;

import hollywood.LuceneSearcher;
import hollywood.dao.MovieDao;
import hollywood.pojo.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingda on 2015/11/27.
 */
@Service
public class MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private LuceneSearcher luceneSearcher;

    @Transactional
    public Movie getById(int movieId) {
        logger.info("get movie by id {}", movieId);
        return movieDao.getById(movieId);
    }

    @Transactional
    public void save(Movie movie) {
        logger.info("save movie {}", movie);
        movieDao.save(movie);
    }

    public List<Movie> search(String keyword) {
        logger.info("search by keyword {}", keyword);
        try {
            return luceneSearcher.getMovieResult(keyword);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}
