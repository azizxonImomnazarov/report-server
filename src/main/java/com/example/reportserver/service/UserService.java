package com.example.reportserver.service;

import com.example.reportserver.bean.UserBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBean userBean = new UserBean("some-username", "some-password"); // TODO get user
        List<GrantedAuthority> roles = new LinkedList<>(); // TODO get authorities

        if (userBean == null) {
            throw new UsernameNotFoundException(username);
        }

        // TODO remove after loading user above
        if (username.equals("user")) {
            userBean.setUsername("user");
            userBean.setPassword("$2a$12$B.OpH44445Bt7EQuSkKi6eIIzE5YNjTyFbOrSnbFSaYovtTenWoVi");
        }

        UserDetails user = User.withUsername(userBean.getUsername()).password(userBean.getPassword()).authorities(roles).build();
        return user;
    }

    public UserBean getUserByUsername(String username) throws UsernameNotFoundException {
        UserBean userBean = new UserBean("some-username", "some-password"); // TODO get user
        return userBean;
    }
}