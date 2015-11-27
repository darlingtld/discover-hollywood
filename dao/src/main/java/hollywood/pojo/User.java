package hollywood.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

/**
 * Created by lingda on 2015/11/27.
 */
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private int userId;
    private String username;
    private String password;
    private Date lastLoginTime;
}
