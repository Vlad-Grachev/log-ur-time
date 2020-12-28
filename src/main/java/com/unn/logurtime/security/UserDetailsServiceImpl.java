package com.unn.logurtime.security;

import com.unn.logurtime.model.User;
import com.unn.logurtime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        GrantedAuthority authority;
        if (!user.getRole().getName().equals("ADMIN"))
            authority = new SimpleGrantedAuthority("ROLE_USER");
        else
            authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        UserDetails userDetails =
                (UserDetails) new org.springframework.security.core.userdetails.User(user.getUsername(),
                        user.getPassword(), Arrays.asList(authority));
        return userDetails;
    }
}
