package PS72021.WIA2;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping("/user")
    public User user(@RequestParam Map<String,String> requestParams) {
        return new User(1, requestParams.get("firstname"), requestParams.get("lastname"));
    }

}
