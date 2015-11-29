package hollywood.engine;

import hollywood.dao.RecRatingDao;
import hollywood.pojo.User;
import org.grouplens.lenskit.ItemRecommender;
import org.grouplens.lenskit.ItemScorer;
import org.grouplens.lenskit.RecommenderBuildException;
import org.grouplens.lenskit.baseline.BaselineScorer;
import org.grouplens.lenskit.baseline.ItemMeanRatingItemScorer;
import org.grouplens.lenskit.baseline.UserMeanBaseline;
import org.grouplens.lenskit.baseline.UserMeanItemScorer;
import org.grouplens.lenskit.core.LenskitConfiguration;
import org.grouplens.lenskit.core.LenskitRecommender;
import org.grouplens.lenskit.data.dao.EventDAO;
import org.grouplens.lenskit.knn.item.ItemItemScorer;
import org.grouplens.lenskit.scored.ScoredId;
import org.grouplens.lenskit.transform.normalize.BaselineSubtractingUserVectorNormalizer;
import org.grouplens.lenskit.transform.normalize.UserVectorNormalizer;
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
    public List<ScoredId> recommend(User user, int recommedCount) {
        try {
            LenskitConfiguration config = new LenskitConfiguration();
// Use item-item CF to score items
            config.bind(ItemScorer.class).to(ItemItemScorer.class);
// let's use personalized mean rating as the baseline/fallback predictor.
// 2-step process:
// First, use the user mean rating as the baseline scorer
            config.bind(BaselineScorer.class, ItemScorer.class).to(UserMeanItemScorer.class);
// Second, use the item mean rating as the base for user means
            config.bind(UserMeanBaseline.class, ItemScorer.class).to(ItemMeanRatingItemScorer.class);
// and normalize ratings by baseline prior to computing similarities
            config.bind(UserVectorNormalizer.class).to(BaselineSubtractingUserVectorNormalizer.class);
//
            config.bind(EventDAO.class).to(recRatingDao.getJDBCRatingDao());

            LenskitRecommender rec = LenskitRecommender.build(config);
            ItemRecommender irec = rec.getItemRecommender();
            List<ScoredId> recommendations = irec.recommend(user.getUserId(), recommedCount);
            return recommendations;
        } catch (RecommenderBuildException e) {
            e.printStackTrace();
        }
        return null;
    }
}
