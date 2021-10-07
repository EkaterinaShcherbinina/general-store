package com.eshcherbinina.generalstore.config.sucurity;

import com.eshcherbinina.generalstore.dao.entity.Role;
import com.eshcherbinina.generalstore.dao.entity.User;
import com.eshcherbinina.generalstore.dao.repositiry.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByEmail(username);
        if(user == null) throw new UsernameNotFoundException(username);

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user));
    }

    private Collection<GrantedAuthority> getAuthorities(User user){
        Set<Role> userRoles = user.getRoles();
        Collection<GrantedAuthority> authorities = new ArrayList<>(userRoles.size());
        for(Role userRole : userRoles){
            authorities.add(new SimpleGrantedAuthority(userRole.getName().toUpperCase()));
        }

        return authorities;
    }
}
