package com.quowl.quowl.web.controllers;

import com.quowl.quowl.domain.user.ProfileInfo;
import com.quowl.quowl.domain.user.User;
import com.quowl.quowl.repository.user.ProfileRepository;
import com.quowl.quowl.repository.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

@Controller
public class TestController {
    @Inject private UserRepository userRepository;
    @Inject private ProfileRepository profileRepository;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hey(Model model) {
        return "hello";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {


        /*ProfileInfo profileInfo = new ProfileInfo();
        profileInfo.setFirstName("Marat");
        profileInfo.setLastName("Kadzhaev");
        profileInfo.setGender(Gender.MALE);
        profileInfo = profileRepository.save(profileInfo);

        User user = new User();
        user.setName("Marat");
        user.setEmail("marchello@gmail.com");
        user.setPassword("123456");
        user.setLangKey("ru");
        user.setProfileInfo(profileInfo);
        userRepository.save(user);*/

        List<User> list = userRepository.findAll();
        ProfileInfo profileInfos = profileRepository.findOneByUserId(list.get(0).getId());

        model.addAttribute("names", list);
        model.addAttribute("profile", profileInfos);
        return "index";
    }

}
