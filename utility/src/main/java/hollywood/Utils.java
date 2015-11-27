package hollywood;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lingda on 2015/11/27.
 */
public class Utils {
    public static String yyyyMMddHHmmss2Format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
