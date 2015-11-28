package hollywood.service;

import hollywood.LuceneSearcher;
import hollywood.Utils;
import hollywood.dao.LinksDao;
import hollywood.dao.MovieDao;
import hollywood.pojo.Links;
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
    private LinksDao linksDao;

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

    @Transactional
    public List<Movie> search(String keyword) {
        logger.info("search by keyword {}", keyword);
        try {
            List<Movie> movieList = luceneSearcher.getMovieResult(keyword);
            for (Movie movie : movieList) {
                Links links = linksDao.getByMovieId(movie.getMovieId());
                movie.setMovieUrl(Utils.generateMovieUrl(links.getMovieId()));
                movie.setImbdUrl(Utils.generateImbdUrl(links.getImbdId()));
                movie.setTmbdUrl(Utils.generateTmbdUrl(links.getTmbdId()));
            }
            return movieList;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Transactional
    public int getMaxMovieId() {
        return movieDao.getMaxMovieId();
    }

    @Transactional
    public void update(Movie movie) {
        movieDao.update(movie);
    }

    @Transactional
    public List<Movie> getMissingPostUrlMovies(int count) {
        return movieDao.getMissingPostUrlMovies(count);
    }
}
