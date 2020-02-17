package com.iamwxc.bbs.controller;

import com.iamwxc.bbs.dao.MomentDAO;
import com.iamwxc.bbs.dao.MyUserDAO;
import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.util.MyUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class HelloController {

    @Autowired
    private MomentDAO momentDAO;

    @Autowired
    private MyUserDAO myUserDAO;

    @Autowired
    private MyUserUtil myUserUtil;

    @GetMapping("/hello")
    public MyUser greeting() {
        MyUser currentUser = myUserUtil.getLoginUser();
        return currentUser;
    }

}
