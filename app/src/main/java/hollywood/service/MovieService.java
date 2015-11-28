package hollywood.service;

import hollywood.LuceneSearcher;
import hollywood.Utils;
import hollywood.crawler.DescriptionCrawler;
import hollywood.dao.LinksDao;
import hollywood.dao.MovieDao;
import hollywood.pojo.Genres;
import hollywood.pojo.Links;
import hollywood.pojo.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Autowired
    private DescriptionCrawler descriptionCrawler;

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
    public List<Movie> search(String keyword, int limit) {
        logger.info("search by keyword {}", keyword);
        try {
            List<Movie> movieList = luceneSearcher.getMovieResult(keyword, limit);
            fillUrls4MovieList(movieList);

            return movieList;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    @Transactional
    private void fillUrls4MovieList(List<Movie> movieList) {
        for (Movie movie : movieList) {
            try {
                Links links = linksDao.getByMovieId(movie.getMovieId());
                movie.setMovieUrl(Utils.generateMovieUrl(links.getMovieId()));
                movie.setImbdUrl(Utils.generateImbdUrl(links.getImbdId()));
                movie.setTmbdUrl(Utils.generateTmbdUrl(links.getTmbdId()));
                movie.setPosterUrl(movieDao.getById(movie.getMovieId()).getPosterUrl());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                continue;
            }
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
        List<Movie> movieList = movieDao.getMissingPostUrlMovies(count);
        fillUrls4MovieList(movieList);
        return movieList;
    }

    @Transactional
    public List<Movie> getMoviesByGenres(String genres, int limit) {
        logger.info("get movies by genres {}", genres);
        try {
            List<Movie> movieList = luceneSearcher.searchMoviesByGenres(genres, limit);
            fillUrls4MovieList(movieList);
            return movieList;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return new ArrayList<>();
    }

    @Transactional
    public HashMap<String, List<Movie>> getMovieListGroupByGenres() {
        HashMap<String, List<Movie>> movieMaps = new HashMap<>();
        for (Genres genres : Genres.values()) {
            movieMaps.put(genres.getGenres(), getMoviesByGenres(genres.getGenres(), 6));
        }
        return movieMaps;
    }

    @Transactional
    public String getMovieDescription(int movieId) {
        Links links = linksDao.getByMovieId(movieId);
        return descriptionCrawler.getMovieDescription(Utils.generateTmbdUrl(links.getTmbdId()));
    }
}
