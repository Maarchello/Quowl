package com.quowl.quowl.web.controllers.base;

import com.quowl.quowl.domain.logic.user.User;
import com.quowl.quowl.service.user.UserService;
import com.quowl.quowl.web.beans.system.JsonResultBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.List;

@PropertySource("classpath:properties/s3.properties")
@Controller
public class SearchController extends BaseController {
    @Inject private UserService userService;

    @Resource
    Environment env;

    @RequestMapping(value = "search/{nickname}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResultBean search(@PathVariable("nickname") String nickname) {
        List<User> users = userService.search(nickname);
        return JsonResultBean.success(users);
    }

}
