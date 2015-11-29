package hollywood.engine;

import hollywood.dao.RecRatingDao;
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
import org.grouplens.lenskit.transform.normalize.BaselineSubtractingUserVectorNormalizer;
import org.grouplens.lenskit.transform.normalize.UserVectorNormalizer;

/**
 * Created by lingda on 2015/11/29.
 */
public class ItemRecommenderFactory {

    private volatile static ItemRecommender itemRecommender;

    private ItemRecommenderFactory() {
    }

    public static ItemRecommender buildItemRecommnder(RecRatingDao recRatingDao) {
        if (itemRecommender == null) {
            synchronized (ItemRecommenderFactory.class) {
                if (itemRecommender == null) {
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
                        return rec.getItemRecommender();
                    } catch (RecommenderBuildException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }
        }
        return itemRecommender;
    }

    public static ItemRecommender rebuildItemRecommnder(RecRatingDao recRatingDao) {
        try {
            LenskitConfiguration config = new LenskitConfiguration();
            config.bind(ItemScorer.class).to(ItemItemScorer.class);
            config.bind(BaselineScorer.class, ItemScorer.class).to(UserMeanItemScorer.class);
            config.bind(UserMeanBaseline.class, ItemScorer.class).to(ItemMeanRatingItemScorer.class);
            config.bind(UserVectorNormalizer.class).to(BaselineSubtractingUserVectorNormalizer.class);
            config.bind(EventDAO.class).to(recRatingDao.getJDBCRatingDao());
            LenskitRecommender rec = LenskitRecommender.build(config);
            itemRecommender = rec.getItemRecommender();
            return itemRecommender;
        } catch (RecommenderBuildException e) {
            e.printStackTrace();
        }
        return null;
    }
}
