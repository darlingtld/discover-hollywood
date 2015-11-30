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
    public static final Integer MAX_BUFFERED_DOCS = Integer.valueOf(prop.getProperty("lucene.maxBufferedDocs"));
    public static final Integer POSTER_URL_POPULATE_JOB_STEP = Integer.valueOf(prop.getProperty("job.poster_url_populate_job.step"));
    public static final Integer DESCRIPTION_POPULATE_JOB_STEP = Integer.valueOf(prop.getProperty("job.description_populate_job.step"));
    public static final Integer MOCK_INITIAL_USERID = Integer.valueOf(prop.getProperty("mock_initial_userid"));
}
