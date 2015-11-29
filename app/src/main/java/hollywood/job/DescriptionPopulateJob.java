package hollywood.job;

import hollywood.PropertyHolder;
import hollywood.crawler.DescriptionCrawler;
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
public class DescriptionPopulateJob {
    @Autowired
    private DescriptionCrawler descriptionCrawler;

    @Autowired
    private MovieService movieService;

    @Transactional
    public void populateDescription() {
        List<Movie> movieList = movieService.getMissingDescriptionMovies(PropertyHolder.DESCRIPTION_POPULATE_JOB_STEP);
        for (Movie movie : movieList) {
            String description = descriptionCrawler.getMovieDescription(movie.getTmbdUrl());
            movie.setDescription(description);
            movieService.update(movie);
        }
    }
}
