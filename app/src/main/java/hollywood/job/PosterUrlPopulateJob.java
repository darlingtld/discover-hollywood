package hollywood.job;

import hollywood.PropertyHolder;
import hollywood.crawler.PictureUrlCrawler;
import hollywood.pojo.Movie;
import hollywood.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lingda on 2015/11/28.
 */
@Component
public class PosterUrlPopulateJob {
    @Autowired
    private PictureUrlCrawler pictureUrlCrawler;

    @Autowired
    private MovieService movieService;

    @Transactional
    public void populatePosterUrl() {
        List<Movie> movieList = movieService.getMissingPostUrlMovies(PropertyHolder.POSTER_URL_POPULATE_JOB_STEP);
        for (Movie movie : movieList) {
            String posterUrl = pictureUrlCrawler.getMoviePosterUrl(movie.getTmbdUrl());
            movie.setPosterUrl(posterUrl);
            movieService.update(movie);
        }
    }
}
