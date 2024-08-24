package com.abdelrahaman.authenticationservice.config;

import com.abdelrahaman.authenticationservice.client.UserClientFeign;
import com.abdelrahaman.authenticationservice.dto.UserAuthDto;
import com.abdelrahaman.authenticationservice.entity.User;
import com.abdelrahaman.authenticationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        if(user.isEmpty())
            throw new UsernameNotFoundException(String.format("user name : %s is Not Found!",username));

        return new CustomUserDetails(user.get().getUserName(),user.get().getPassword());
    }
}
