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
public class PictureUrlCrawler {

    private static final Logger logger = LoggerFactory.getLogger(PictureUrlCrawler.class);

    public String getMoviePosterUrl(String tmbdUrl) {
        if (StringUtils.isEmpty(tmbdUrl)) {
            logger.error("empty tmbdUrl");
            return null;
        } else {
            try {
                logger.info("try to get img from {}", tmbdUrl);
                Document doc = Jsoup.connect(tmbdUrl).userAgent("Mozilla").timeout(30000).get();
                Elements eles = doc.select("#upload_poster");
                String imgUrl = eles.get(0).attr("src");
                logger.info("img src from {} is {}", tmbdUrl, imgUrl);
                return imgUrl;
            } catch (Exception e) {
                logger.error(e.getMessage());
                return null;
            }
        }
    }


}
