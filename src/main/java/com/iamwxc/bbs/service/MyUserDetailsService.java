package com.iamwxc.bbs.service;

import com.iamwxc.bbs.entity.MyUser;
import com.iamwxc.bbs.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Class description goes here.
 * <p>
 * A custom user detail service, to validate.
 * </p>
 *
 * @author CC
 * @version 1.0
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        MyUser myUser = myUserRepository.findByUsername(s);
        User user = null;
        if (myUser != null) {
            String password = myUser.getPassword();
            String role = myUser.getRole();
            user = new User(s,
                    passwordEncoder.encode(password),  // a PasswordEncoder instance is a must
                    AuthorityUtils.commaSeparatedStringToAuthorityList(role));
        }
        return user;
    }

}
