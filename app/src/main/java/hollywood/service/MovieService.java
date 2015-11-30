package hollywood.service;

import hollywood.LuceneSearcher;
import hollywood.Utils;
import hollywood.crawler.DescriptionCrawler;
import hollywood.dao.AvgRatingDao;
import hollywood.dao.LinksDao;
import hollywood.dao.MovieDao;
import hollywood.pojo.AvgRating;
import hollywood.pojo.Genres;
import hollywood.pojo.Links;
import hollywood.pojo.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private AvgRatingDao avgRatingDao;

    /**
     * get movie by movie id
     *
     * @param movieId
     * @return
     */
    @Transactional
    public Movie getById(int movieId) {
        logger.info("get movie by id {}", movieId);
        Movie movie = movieDao.getById(movieId);
        fillUrls4Movie(movie, true);
        return movie;
    }

    /**
     * save movie
     *
     * @param movie
     */
    @Transactional
    public void save(Movie movie) {
        logger.info("save movie {}", movie);
        movieDao.save(movie);
    }

    /**
     * searchMoviesByTitle by movie titles
     *
     * @param keyword
     * @param limit
     * @return a list of movies
     */
    @Transactional
    public List<Movie> searchMoviesByTitle(String keyword, int limit) {
        logger.info("searchMoviesByTitle by keyword {}", keyword);
        try {
            List<Movie> movieList = luceneSearcher.searchMoviesByTitle(keyword, limit);
            fillUrls4MovieList(movieList, true);
            return movieList;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * this method populates some necessary information of movies.
     * like movie url, imbd url, tmbd url and its rating information.
     * most of the movie list are retrieved via lucene. so these information are not indexed.
     * need to think more about it.
     *
     * @param movie
     */
    @Transactional
    public void fillUrls4Movie(Movie movie, boolean isRatingNeeded) {
        Links links = linksDao.getByMovieId(movie.getMovieId());
        if (links == null) {
            return;
        }
        movie.setMovieUrl(Utils.generateMovieUrl(links.getMovieId()));
        movie.setImbdUrl(Utils.generateImbdUrl(links.getImbdId()));
        movie.setTmbdUrl(Utils.generateTmbdUrl(links.getTmbdId()));
        if (isRatingNeeded) {
            AvgRating avgRating = avgRatingDao.getRatingByMovieId(movie.getMovieId());
            if (avgRating != null) {
                movie.setAvgRating(avgRating.getAvgRating());
            }
        }
//                TODO too many db interactions here
        Movie movieInDB = movieDao.getById(movie.getMovieId());
        movie.setPosterUrl(movieInDB.getPosterUrl());
        movie.setDescription(movieInDB.getDescription());
    }

    @Transactional
    public void fillUrls4MovieList(List<Movie> movieList, boolean isRatingNeeded) {
        for (Movie movie : movieList) {
            try {
                fillUrls4Movie(movie, isRatingNeeded);
            } catch (Exception e) {
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
        fillUrls4MovieList(movieList, true);
        return movieList;
    }

    @Transactional
    public List<Movie> getMoviesByGenres(String genres, int limit) {
        logger.info("get movies by genres {}", genres);
        try {
            List<Movie> movieList = luceneSearcher.searchMoviesByGenres(genres, limit);
            fillUrls4MovieList(movieList, true);
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

    @Transactional
    public List<Movie> getMissingDescriptionMovies(int count) {
        List<Movie> movieList = movieDao.getMissingDescriptionMovies(count);
        fillUrls4MovieList(movieList, true);
        return movieList;
    }

    @Transactional
    public List<Movie> getHighestRatedMovies(int limit) {
        List<AvgRating> avgRatingList = avgRatingDao.getHighestRatedMovies(limit);
        List<Movie> movieList = new ArrayList<>();
        for (AvgRating avgRating : avgRatingList) {
            Movie movie = getById(avgRating.getMovieId());
            movie.setAvgRating(avgRating.getAvgRating());
            movieList.add(movie);
        }
        fillUrls4MovieList(movieList, true);
        return movieList;
    }

    @Transactional
    public List<Movie> getMostRatedMovies(int limit) {
        List<AvgRating> avgRatingList = avgRatingDao.getMostRatedMovies(limit);
        List<Movie> movieList = new ArrayList<>();
        for (AvgRating avgRating : avgRatingList) {
            Movie movie = getById(avgRating.getMovieId());
            movie.setAvgRating(avgRating.getAvgRating());
            movieList.add(movie);
        }
        fillUrls4MovieList(movieList, true);
        return movieList;
    }

    @Transactional
    public List<Movie> getRecentlyReleasedMovies(int limit) {
        List<Movie> movieList = movieDao.getRecentlyReleasedMovies(limit);
        fillUrls4MovieList(movieList, true);
        return movieList;
    }
}
