package com.iamwxc.bbs.service;

import com.iamwxc.bbs.dao.MyUserDAO;
import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.util.CustomErrorCode;
import com.iamwxc.bbs.util.MyUserUtil;
import com.sun.deploy.xml.CustomParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
@Service
public class AdminManageService {

    @Autowired
    private MyUserUtil myUserUtil;

    @Autowired
    private MyUserDAO myUserDAO;

    /**
     * deny normal access
     * @return if it's admin return <code>true</code> else <code>false</code>
     */
    public Boolean checkAuthority() {
        MyUser currentUser = myUserUtil.getLoginUser();
        if (currentUser == null)
            return false;
        else return currentUser.getRole().equals("admin");
    }

    public List<MyUser> getAllUser() {
        return myUserDAO.findAll(Sort.by(Sort.Direction.ASC, "username"));
    }

    public CustomErrorCode updateUserRepo(String presetUsername,
                                      String presetPassword,
                                      String presetRole,
                                      String delUsername) {
        if ((presetUsername == null || presetUsername.equals("")) &&
                (presetPassword == null || presetPassword.equals("")) &&
                (presetRole == null || presetRole.equals("")) &&
                (delUsername == null || delUsername.equals("")))
            // if admin submit nothing
            return CustomErrorCode.PARAM_ALL_NULL;
        else if (delUsername != null && !delUsername.equals("")) {
            // handle delete user
            MyUser targetUser = myUserDAO.findByUsername(delUsername);
            if (targetUser == null) {
                // cannot find user
                return CustomErrorCode.USER_NOT_FOUND;
            } else {
                // delete
                myUserDAO.delete(targetUser);
                return CustomErrorCode.DELETE_USER_SUCCESS;
            }
        } else {
            // handle register user
            if (presetUsername == null || presetUsername.equals("")) {
                return CustomErrorCode.USERNAME_NULL;
            } else if (presetPassword == null || presetPassword.equals("")) {
                return CustomErrorCode.PASSWORD_NULL;
            } else if (presetRole == null || presetRole.equals("")) {
                return CustomErrorCode.ROLE_NULL;
            } else {
                if (!presetRole.equals("admin") && !presetRole.equals("user")) {
                    return CustomErrorCode.ROLE_WRONG;
                } else {
                    // check if username existed
                    MyUser newUser = myUserDAO.findByUsername(presetUsername);
                    if (newUser != null) {
                        return CustomErrorCode.USER_EXIST;
                    } else {
                        newUser = new MyUser();
                        newUser.setUsername(presetUsername);
                        newUser.setPassword(presetPassword);
                        newUser.setRole(presetRole);
                        myUserDAO.save(newUser);
                        return CustomErrorCode.REGISTER_USER_SUCCESS;
                    }
                }
            }
        }
    }

}
