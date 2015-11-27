package hollywood.service;

import hollywood.PasswordEncryptUtil;
import hollywood.dao.UserDao;
import hollywood.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lingda on 2015/11/27.
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Transactional
    public User login(String username, String password) {
        User user = userDao.getByUsername(username);
        if (user == null) {
            throw new RuntimeException(String.format("username %s does not exist", username));
        } else {
            if (!PasswordEncryptUtil.matches(password, user.getPassword())) {
                throw new RuntimeException("password is incorrect");
            } else {
                return user;
            }
        }
    }
}
