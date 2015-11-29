package hollywood.sqlfactory;

import org.grouplens.lenskit.data.sql.BasicSQLStatementFactory;

/**
 * Created by lingda on 2015/11/29.
 */
public class HollywoodSqlFactory extends BasicSQLStatementFactory {

    public HollywoodSqlFactory() {
        super.setTimestampColumn("ratings");
        super.setItemColumn("movieId");
        super.setRatingColumn("rating");
        super.setTimestampColumn("timestamp");
        super.setUserColumn("userId");
    }
}
