package dao;

import hollywood.pojo.Links;
import hollywood.pojo.Movie;
import hollywood.pojo.Rating;
import hollywood.pojo.Tag;
import hollywood.service.LinksService;
import hollywood.service.MovieService;
import hollywood.service.RatingService;
import hollywood.service.TagService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

/**
 * Created by lingda on 2015/11/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class ImportData {

    @Autowired
    private LinksService linksService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private TagService tagService;

    @Test
    public void importLinks() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\splunk\\ml-latest-small\\links.csv"));
        String line = bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            String[] eles = line.split(",");
            try {
                Links links = new Links();
                links.setMovieId(Integer.parseInt(eles[0]));
                links.setImbdId(Integer.parseInt(eles[1]));
                links.setTmbdId(Integer.parseInt(eles[2]));
                linksService.save(links);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

    }

    @Test
    public void importMovies() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\splunk\\ml-latest-small\\movies.csv"));
        String line = bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            String[] eles = line.split(",");
            try {
                Movie movie = new Movie();
                movie.setMovieId(Integer.parseInt(eles[0]));
                movie.setTitle(eles[1]);
                movie.setGenres(eles[2]);
                movieService.save(movie);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

    }

    @Test
    public void importRatings() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\splunk\\ml-latest-small\\ratings.csv"));
        String line = bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            String[] eles = line.split(",");
            try {
                Rating rating = new Rating();
                rating.setUserId(Integer.parseInt(eles[0]));
                rating.setMovieId(Integer.parseInt(eles[1]));
                rating.setRating(Double.parseDouble(eles[2]));
                rating.setTimestamp(new Date(Long.parseLong(eles[3]) * 1000));
                ratingService.save(rating);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

    }

    @Test
    public void importTags() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\splunk\\ml-latest-small\\tags.csv"));
        String line = bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            String[] eles = line.split(",");
            try {
                Tag tag = new Tag();
                tag.setUserId(Integer.parseInt(eles[0]));
                tag.setMovieId(Integer.parseInt(eles[1]));
                tag.setTag(eles[2]);
                tag.setTimestamp(new Date(Long.parseLong(eles[3]) * 1000));
                tagService.save(tag);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

    }
}
