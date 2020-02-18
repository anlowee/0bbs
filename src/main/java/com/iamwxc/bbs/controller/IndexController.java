package com.iamwxc.bbs.controller;

import com.iamwxc.bbs.dao.MomentDAO;
import com.iamwxc.bbs.dao.MyUserDAO;
import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.util.MyUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
public class IndexController {

    @Autowired
    private MomentDAO momentDAO;

    @Autowired
    private MyUserUtil myUserUtil;

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        MyUser currentUser = myUserUtil.getLoginUser();
        modelAndView.addObject("user", currentUser);
        modelAndView.addObject("moments", momentDAO.findByMomentID(1L));
        return modelAndView;
    }

}
