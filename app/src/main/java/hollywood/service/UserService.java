package hollywood.service;

import com.alibaba.druid.util.StringUtils;
import hollywood.PasswordEncryptUtil;
import hollywood.dao.UserDao;
import hollywood.pojo.Genres;
import hollywood.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        logger.info("user {} login", username);
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

    @Transactional
    public User signup(String username, String password) {
//        validate username and password
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new RuntimeException("username or password is empty");
        } else {
            synchronized (UserService.class) {
//            check whether this username has been taken
                if (userDao.getByUsername(username) != null) {
                    throw new RuntimeException(String.format("username %s has been taken", username));
                }
//                encrypt password
                password = PasswordEncryptUtil.encrypt(password);
                return userDao.saveUser(username, password);
            }
        }
    }

    @Transactional
    public User addFavouriteGenresList(int userId, List<String> favGenresList) {
        logger.info("add favourite genres list {} to user {}", favGenresList, userId);
        return userDao.addFavouriteGenresList(userId, favGenresList);
    }
}
