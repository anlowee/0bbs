package com.iamwxc.bbs.controller;

import com.iamwxc.bbs.dao.MomentDAO;
import com.iamwxc.bbs.dao.MyUserDAO;
import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.entity.moment.Moment;
import com.iamwxc.bbs.util.MyUserUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.sql.PseudoColumnUsage;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

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
