package hollywood.crawler;

import com.alibaba.druid.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by lingda on 2015/11/28.
 */
@Service
public class DescriptionCrawler {
    private static final Logger logger = LoggerFactory.getLogger(PictureUrlCrawler.class);

    public String getMovieDescription(String tmbdUrl) {
        if (StringUtils.isEmpty(tmbdUrl)) {
            logger.error("empty tmbdUrl");
            return "No description was found";
        } else {
            try {
                logger.info("try to get description from {}", tmbdUrl);
                Document doc = Jsoup.connect(tmbdUrl).userAgent("Mozilla").timeout(30000).get();
                Elements eles = doc.select("#overview");

                String text = eles.get(0).text();

                logger.info("description from {} is {}", tmbdUrl, text);
                return text;
            } catch (Exception e) {
                logger.error(e.getMessage());
                return "No description was found";
            }
        }
    }

}
