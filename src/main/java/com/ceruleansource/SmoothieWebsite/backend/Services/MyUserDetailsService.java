package com.ceruleansource.SmoothieWebsite.backend.Services;

import com.ceruleansource.SmoothieWebsite.backend.Models.MyUserDetails;
import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Models.User;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));

        System.out.println(user);
        return user.map(MyUserDetails::new).get();
    }

    public void store(MyUserDetails userDetails){
        User user = new User(userDetails.getFirstName(), userDetails.getLastName(), userDetails.getEmail(), userDetails.getPassword(),
                new ArrayList<>(), "0", true, "USER");

        userRepository.save(user);
    }
}
