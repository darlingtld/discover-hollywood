package hollywood.job;

import hollywood.dao.RecRatingDao;
import hollywood.engine.ItemRecommenderFactory;
import hollywood.pojo.Movie;
import hollywood.pojo.User;
import hollywood.service.MovieService;
import hollywood.service.UserService;
import org.grouplens.lenskit.ItemRecommender;
import org.grouplens.lenskit.scored.ScoredId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lingda on 2015/11/29.
 */
@Component
public class RecommendPopulateJob {

    private static final Logger logger = LoggerFactory.getLogger(RecommendPopulateJob.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RecRatingDao recRatingDao;

    @Autowired
    private MovieService movieService;

    @Transactional
    public void calculateRecommendInfo() {
        ItemRecommender irec = ItemRecommenderFactory.rebuildItemRecommnder(recRatingDao);
        List<User> userList = userService.getAll();
        for (User user : userList) {
            logger.info("calculate recommend movie for user {}", user.getUserId());
            List<ScoredId> scoredIdList = irec.recommend(user.getUserId(), 20);
            List<Movie> movieList = new ArrayList<>();
            for (ScoredId scoredId : scoredIdList) {
                Movie movie = movieService.getById((int) scoredId.getId());
                movieList.add(movie);
            }
            userService.addRecommendMovieList(user.getUserId(), movieList);
        }
    }
}
