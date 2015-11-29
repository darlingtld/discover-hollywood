package hollywood.controller;

import com.alibaba.fastjson.JSONObject;
import hollywood.pojo.Movie;
import hollywood.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;


@Scope("prototype")
@Controller
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @RequestMapping(value = "genres/{limit}", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    List<Movie> recommendByGenres(@RequestBody JSONObject jsonObject, @PathVariable("limit") int limit, HttpServletResponse response) {
        return recommendService.recommendByGenres(Arrays.asList(jsonObject.getString("favouriteGenresList").split(",")), limit);
    }


}
