package com.iamwxc.bbs.util;

import com.iamwxc.bbs.dao.MyUserDAO;
import com.iamwxc.bbs.entity.MyUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Class description goes here.
 * <p>
 * Used to get current login user's info.
 * </p>
 *
 * @author CC
 * @version 1.0
 */
@Component
public class MyUserUtil {

    @Autowired
    private MyUserDAO myUserDAO;

    public MyUser getLoginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            if (auth instanceof AnonymousAuthenticationToken)
                return null;
            if (auth instanceof UsernamePasswordAuthenticationToken) {
                MyUser myUser = new MyUser();
                BeanUtils.copyProperties(auth.getPrincipal(), myUser);
                if (myUser.getUsername() != null)
                    myUser = myUserDAO.findByUsername(myUser.getUsername());
                return myUser;
            }
        }
        return null;
    }

}
