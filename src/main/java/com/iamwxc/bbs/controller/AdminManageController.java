package com.iamwxc.bbs.controller;

import com.iamwxc.bbs.dao.MyUserDAO;
import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.service.AdminManageService;
import com.iamwxc.bbs.util.CustomErrorCode;
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
    private AdminManageService adminManageService;

    @GetMapping("/admin")
    public ModelAndView administration() {
        ModelAndView modelAndView = new ModelAndView();
        if (adminManageService.checkAuthority()) {
            modelAndView.setViewName("admin");
            List<MyUser> myUsers = adminManageService.getAllUser();
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

        CustomErrorCode status = adminManageService.updateUserRepo(presetUsername, presetPassword, presetRole, delUsername);
        switch (status) {
            case REGISTER_USER_SUCCESS:
                modelAndView.addObject("success", "注册成功喵~");
                break;
            case DELETE_USER_SUCCESS:
                modelAndView.addObject("success", "注销成功喵~");
                break;
            case PARAM_ALL_NULL:
                modelAndView.addObject("error", "您啥都没填喵~");
                break;
            case USER_EXIST:
                modelAndView.addObject("error", "该用户名已存在喵~");
                break;
            case USER_NOT_FOUND:
                modelAndView.addObject("error", "未找到该用户喵~");
                break;
            case USERNAME_NULL:
                modelAndView.addObject("error", "请填写新用户用户名喵~");
                break;
            case PASSWORD_NULL:
                modelAndView.addObject("error", "请填写新用户密码喵~");
                break;
            case ROLE_NULL:
                modelAndView.addObject("error", "请填写新用户权限喵~");
                break;
            case ROLE_WRONG:
                modelAndView.addObject("error", "用户权限错误喵~");
                break;
            default:
                modelAndView.addObject("error", "出错叻喵~");
        }
        // update user list
        List<MyUser> myUsers = adminManageService.getAllUser();
        modelAndView.addObject("userList", myUsers);
        return modelAndView;
    }

}
