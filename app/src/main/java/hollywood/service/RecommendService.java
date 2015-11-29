package hollywood.service;

import hollywood.pojo.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lingda on 2015/11/29.
 */
@Service
public class RecommendService {
    private static final Logger logger = LoggerFactory.getLogger(RecommendService.class);

    @Autowired
    private MovieService movieService;

    @Transactional
    public List<Movie> recommendByGenres(List<String> genresList, int limit) {
        List<Movie> movieList = new ArrayList<>();
        for (String genres : genresList) {
            movieList.addAll(movieService.getMoviesByGenres(genres, limit));
        }
        Collections.sort(movieList, (o1, o2) -> (int) (o1.getAvgRating() - o2.getAvgRating()));
        return movieList.subList(0, limit);
    }
}
