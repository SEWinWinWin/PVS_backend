package pvs.app.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pvs.app.service.AuthService;
import pvs.app.service.ProjectService;

@RestController
public class AuthController {

    static final Logger logger = LogManager.getLogger(AuthController.class.getName());

    @Autowired
    private AuthService authService;
    /**
     * 登录
     */
    @PostMapping(value = "/auth/login")
    public String login( String username,String password ) {
        // 登录成功会返回Token给用户
        String token = authService.login( username, password );
        logger.debug(token);
        return token;
    }

    @PostMapping(value = "/user/hi")
    public String userHi( String name ) {
        return "hi " + name + " , you have 'user' role";
    }

    @PostMapping(value = "/admin/hi")
    public String adminHi( String name ) {
        return "hi " + name + " , you have 'admin' role";
    }


}
