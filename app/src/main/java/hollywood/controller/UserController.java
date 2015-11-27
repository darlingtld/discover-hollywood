package hollywood.controller;

import com.alibaba.fastjson.JSONObject;
import hollywood.PropertyHolder;
import hollywood.pojo.User;
import hollywood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by tangl9 on 2015-10-14.
 */
@Scope("prototype")
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * user login
     *
     * @param loginStub
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST, headers = "content-type=application/json")
    public
    @ResponseBody
    User login(@RequestBody JSONObject loginStub, HttpServletResponse response) {
        String username = loginStub.getString("username");
        String password = loginStub.getString("password");
        try {
            return userService.login(username, password);
        } catch (Exception e) {
            response.setHeader(PropertyHolder.HEADER_MESSAGE, e.getMessage());
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
            return null;
        }
    }

}
