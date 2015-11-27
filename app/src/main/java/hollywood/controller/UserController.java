package hollywood.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by tangl9 on 2015-10-14.
 */
@Scope("prototype")
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public
    @ResponseBody
    String test() {
        return "hello";
    }

//    /**
//     * 管理员的登录
//     *
//     * @param loginStub
//     * @return
//     */
//    @RequestMapping(value = "login", method = RequestMethod.POST, headers = "content-type=application/json")
//    public
//    @ResponseBody
//    PGroup login(@RequestBody JSONObject loginStub) {
//        String username = loginStub.getString("username");
//        String password = loginStub.getString("password");
//        return userService.adminLogin(username, password);
//    }

}
