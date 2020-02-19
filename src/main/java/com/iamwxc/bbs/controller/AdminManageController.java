package com.iamwxc.bbs.controller;

import com.iamwxc.bbs.dao.MyUserDAO;
import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.util.MyUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
public class AdminManageController {

    @Autowired
    private MyUserDAO myUserDAO;

    @Autowired
    private MyUserUtil myUserUtil;

    private boolean checkAuthority() {
        MyUser currentUser = myUserUtil.getLoginUser();
        if (currentUser == null)
            return false;
        else return currentUser.getRole().equals("admin");
    }

    @GetMapping("/admin")
    public ModelAndView administration() {
        ModelAndView modelAndView = new ModelAndView();
        if (checkAuthority()) {
            modelAndView.setViewName("admin");
            List<MyUser> myUsers = myUserDAO.findAll(Sort.by(Sort.Direction.ASC, "username"));
            modelAndView.addObject("userList", myUsers);
        } else
            modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @PostMapping("/admin")
    public ModelAndView doAdministration(@RequestParam(value = "presetUsername", required = false) String presetUsername,
                                         @RequestParam(value = "presetPassword", required = false) String presetPassword,
                                         @RequestParam(value = "presetRole", required = false) String presetRole,
                                         @RequestParam(value = "delUsername", required = false) String delUsername) {
        // initial mav
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("presetUsername", "");
        modelAndView.addObject("presetPassword", "");
        modelAndView.addObject("presetRole", "");
        modelAndView.addObject("delUsername", "");

        if ((presetUsername == null || presetUsername.equals("")) &&
                (presetPassword == null || presetPassword.equals("")) &&
                (presetRole == null || presetRole.equals("")) &&
                (delUsername == null || delUsername.equals("")))
            // if admin submit nothing
            modelAndView.addObject("error", "请填写注册用户或注销用户的信息喵~");
        else if (delUsername != null && !delUsername.equals("")) {
            // handle delete user
            MyUser targetUser = myUserDAO.findByUsername(delUsername);
            if (targetUser == null) {
                // cannot find user
                modelAndView.addObject("error", "未找到该用户喵~");
            } else {
                // delete
                myUserDAO.delete(targetUser);
                modelAndView.addObject("success", "注销用户成功喵~");
            }
        } else {
            // handle register user
            if (presetUsername == null || presetUsername.equals("")) {
                modelAndView.addObject("error", "请填写新用户预设用户名喵~");
            } else if (presetPassword == null || presetPassword.equals("")) {
                modelAndView.addObject("error", "请填写新用户预设密码喵~");
            } else if (presetRole == null || presetRole.equals("")) {
                modelAndView.addObject("error", "请填写新用户预设角色喵~");
            } else {
                if (!presetRole.equals("admin") && !presetRole.equals("user")) {
                    modelAndView.addObject("error", "角色仅有admin/user喵~");
                } else {
                    // check if username existed
                    MyUser newUser = myUserDAO.findByUsername(presetUsername);
                    if (newUser != null) {
                        modelAndView.addObject("error", "该用户名已存在喵~");
                    } else {
                        newUser = new MyUser();
                        newUser.setUsername(presetUsername);
                        newUser.setPassword(presetPassword);
                        newUser.setRole(presetRole);
                        myUserDAO.save(newUser);
                        modelAndView.addObject("success", "注册新用户成功喵~");
                    }
                }
            }
        }
        // update user list
        List<MyUser> myUsers = myUserDAO.findAll(Sort.by(Sort.Direction.ASC, "username"));
        modelAndView.addObject("userList", myUsers);
        return modelAndView;
    }

}
