package hollywood.job;

import hollywood.crawler.PicCrawler;
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
    private PicCrawler picCrawler;

    @Autowired
    private MovieService movieService;

    @Transactional
    public void populatePosterUrl() {
        List<Movie> movieList = movieService.getMissingPostUrlMovies(100);
        for (Movie movie : movieList) {
            String posterUrl = picCrawler.getMoviePosterUrl(movie.getTmbdUrl());
            movie.setPosterUrl(posterUrl);
            movieService.update(movie);
        }
    }
}
