package com.iamwxc.bbs.controller;

import com.iamwxc.bbs.dao.MyUserDAO;
import com.iamwxc.bbs.entity.MyUser;
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
    private MyUserDAO myUserDAO;

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
        boolean isUpdatePassword = false;
        boolean isUpdateUsername = false;
        boolean isUpdateUsernameSuccess = false;
        MyUser currentUser = myUserUtil.getLoginUser();

        // update username
        if (newUsername == null || newUsername.equals("")) {
            isUpdateUsername = true;
            modelAndView.addObject("usernameNull", "用户名不能为空喵~");
        }
        else if (!newUsername.equals(currentUser.getUsername())) {
            isUpdateUsername = true;
            MyUser myUser = myUserDAO.findByUsername(newUsername);
            if (myUser != null)
                modelAndView.addObject("usernameExist", "用户名已存在喵~");
            else {
                currentUser.setUsername(newUsername);
                isUpdateUsernameSuccess = true;
            }

        }
        // update password
        if (newPassword == null || newPassword.equals(""))
            modelAndView.addObject("passwordNull", "密码不能为空喵~");
        else if (!newPassword.equals(currentUser.getPassword())) {
            currentUser.setPassword(newPassword);
            if (!isUpdateUsername || isUpdateUsernameSuccess)
                isUpdatePassword = true;
        }
        // update email addr
        if (newEmailAddress != null && !newEmailAddress.equals("")) {
            currentUser.setEmailAddress(newEmailAddress);
            String MD5 = DigestUtils.md5DigestAsHex(newEmailAddress.getBytes());
            String newProfileURL = "http://www.gravatar.com/avatar/" + MD5 + "?s=64";
            currentUser.setProfileURL(newProfileURL);
        }
        myUserDAO.save(currentUser);

        modelAndView.addObject("username", currentUser.getUsername());
        modelAndView.addObject("password", currentUser.getPassword());
        modelAndView.addObject("email", currentUser.getEmailAddress());
        modelAndView.addObject("profileURL", currentUser.getProfileURL());
        modelAndView.addObject("success", "相关信息已更新喵~");
        if (isUpdateUsernameSuccess || isUpdatePassword)
            modelAndView = new ModelAndView("redirect:/login");
        return modelAndView;
    }

}
