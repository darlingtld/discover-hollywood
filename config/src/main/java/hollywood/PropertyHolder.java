package hollywood;

import java.io.IOException;
import java.util.Properties;

public class PropertyHolder {

    public static final String HEADER_MESSAGE = "message";

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(PropertyHolder.class.getClassLoader().getResourceAsStream("movie.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String LUCENE_INDEX_DIR = prop.getProperty("lucene.indexDir");
    public static final Integer LUCENE_MAX_HITS = Integer.valueOf(prop.getProperty("lucene.maxHits"));

}
