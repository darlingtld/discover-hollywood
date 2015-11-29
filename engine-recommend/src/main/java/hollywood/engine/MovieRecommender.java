package hollywood.engine;

import hollywood.dao.RecRatingDao;
import hollywood.pojo.User;
import org.grouplens.lenskit.ItemRecommender;
import org.grouplens.lenskit.scored.ScoredId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by lingda on 2015/11/29.
 */
@Service
public class MovieRecommender {

    @Autowired
    private RecRatingDao recRatingDao;

    @Transactional
    public List<ScoredId> recommend(User user, int recommendCount) {
        ItemRecommender irec = ItemRecommenderFactory.buildItemRecommnder(recRatingDao);
        List<ScoredId> recommendations = irec.recommend(user.getUserId(), recommendCount);
        return recommendations;
    }

}
