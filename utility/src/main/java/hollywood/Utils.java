package hollywood;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lingda on 2015/11/27.
 */
public class Utils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String yyyyMMddHHmmss2Format(Date date) {
        return sdf.format(date);
    }

    public static String generateMovieUrl(int movieId) {
        return String.format("https://movielens.org/movies/%d", movieId);
    }

    public static String generateImbdUrl(int imbdId) {
        return String.format("http://www.imdb.com/title/tt0%d", imbdId);
    }

    public static String generateTmbdUrl(int tmbdId) {
        return String.format("https://www.themoviedb.org/movie/%d", tmbdId);
    }
}
