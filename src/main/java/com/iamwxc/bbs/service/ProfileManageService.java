package com.iamwxc.bbs.service;

import com.iamwxc.bbs.dao.MyUserDAO;
import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.util.CustomErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
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
@Service
public class ProfileManageService {

    @Autowired
    private MyUserDAO myUserDAO;

    public CustomErrorCode updateUserProfile(MyUser targetUser,
                                             String newUsername,
                                             String newPassword,
                                             String newEmailAddress) {
        CustomErrorCode status = null;
        boolean isUpdatePassword = false;
        boolean isUpdateUsername = false;
        boolean isUpdateEmailAddress = false;
        boolean isUpdateUsernameSuccess = false;

        // update username
        if (newUsername == null || newUsername.equals("")) {
            isUpdateUsername = true;
            status = CustomErrorCode.USERNAME_NULL;
        } else if (!newUsername.equals(targetUser.getUsername())) {
            isUpdateUsername = true;
            MyUser myUser = myUserDAO.findByUsername(newUsername);
            if (myUser != null)
                status = CustomErrorCode.USER_EXIST;
            else {
                targetUser.setUsername(newUsername);
                isUpdateUsernameSuccess = true;
            }

        }
        // update password
        if (newPassword == null || newPassword.equals(""))
            status = CustomErrorCode.PASSWORD_NULL;
        else if (!newPassword.equals(targetUser.getPassword())) {
            targetUser.setPassword(newPassword);
            if (!isUpdateUsername || isUpdateUsernameSuccess)
                isUpdatePassword = true;
        }
        // update email addr
        if (newEmailAddress != null && !newEmailAddress.equals("")) {
            targetUser.setEmailAddress(newEmailAddress);
            isUpdateEmailAddress = true;
            String MD5 = DigestUtils.md5DigestAsHex(newEmailAddress.getBytes());
            String newProfileURL = "http://www.gravatar.com/avatar/" + MD5 + "?s=64";
            targetUser.setProfileURL(newProfileURL);
        }
        myUserDAO.save(targetUser);
        if (status == null && (isUpdateUsernameSuccess || isUpdatePassword || isUpdateEmailAddress))
            status = CustomErrorCode.UPDATE_SUCCESS;
        else
            status = CustomErrorCode.UPDATE_FAILED;
        return status;
    }

}
