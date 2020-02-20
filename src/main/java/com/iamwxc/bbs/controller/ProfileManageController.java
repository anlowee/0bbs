package com.iamwxc.bbs.controller;

import com.iamwxc.bbs.dao.MyUserDAO;
import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.service.ProfileManageService;
import com.iamwxc.bbs.util.CustomErrorCode;
import com.iamwxc.bbs.util.MyUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class description goes here.
 * <p>
 * If you see this sentence, nothing ambiguous.
 * </p>
 *
 * @author CC
 * @version 1.0
 */
@RestController
public class ProfileManageController {

    @Autowired
    private MyUserUtil myUserUtil;

    @Autowired
    private ProfileManageService profileManageService;

    @GetMapping("/profile")
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView("profile");
        MyUser currentUser = myUserUtil.getLoginUser();
        modelAndView.addObject("username", currentUser.getUsername());
        modelAndView.addObject("password", currentUser.getPassword());
        modelAndView.addObject("email", currentUser.getEmailAddress());
        modelAndView.addObject("profileURL", currentUser.getProfileURL());
        return modelAndView;
    }

    @PostMapping("/profile")
    public ModelAndView updateProfile(@RequestParam(value = "username", required = false) String newUsername,
                                      @RequestParam(value = "password", required = false) String newPassword,
                                      @RequestParam(value = "email", required = false) String newEmailAddress) {
        ModelAndView modelAndView = new ModelAndView("profile");
        MyUser currentUser = myUserUtil.getLoginUser();

        CustomErrorCode status = profileManageService.updateUserProfile(currentUser, newUsername, newPassword, newEmailAddress);
        switch (status) {
            case UPDATE_FAILED:
                modelAndView.addObject("username", currentUser.getUsername());
                modelAndView.addObject("password", currentUser.getPassword());
                modelAndView.addObject("email", currentUser.getEmailAddress());
                modelAndView.addObject("profileURL", currentUser.getProfileURL());
            case USERNAME_NULL:
                modelAndView.addObject("error", "用户名不能为空喵~");
                break;
            case USER_EXIST:
                modelAndView.addObject("error", "用户名已存在喵~");
                break;
            case PASSWORD_NULL:
                modelAndView.addObject("error", "密码不能为空喵~");
                break;
            case UPDATE_SUCCESS:
                modelAndView = new ModelAndView("redirect:/login");
                break;
        }

        return modelAndView;
    }

}
