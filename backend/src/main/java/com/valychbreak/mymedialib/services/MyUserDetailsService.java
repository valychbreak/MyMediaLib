package com.valychbreak.mymedialib.services;

import com.valychbreak.mymedialib.entity.Role;
import com.valychbreak.mymedialib.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by valych on 4/1/17.
 */
@Service(value = "userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    //get user from the database, via Hibernate
    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*@Transactional(readOnly=true)*/
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        com.valychbreak.mymedialib.entity.User user = userRepository.findFirstByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        List<GrantedAuthority> authorities =
                buildUserAuthority(user.getRole());

        return buildUserForAuthentication(user, authorities);

    }

    // Converts com.valychbreak.mymedialib.entity.User user to
    // org.springframework.security.core.userdetails.User
    private User buildUserForAuthentication(com.valychbreak.mymedialib.entity.User user,
                                            List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(),
                true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Role role) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        //for (Role role : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(role.getRole()));
        //}

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }

}
