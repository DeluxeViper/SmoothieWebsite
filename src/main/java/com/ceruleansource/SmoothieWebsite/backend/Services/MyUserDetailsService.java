package com.ceruleansource.SmoothieWebsite.backend.Services;

import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Models.user.MyUserDetails;
import com.ceruleansource.SmoothieWebsite.backend.Models.user.User;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.UserRepository;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));

        return user.map(MyUserDetails::new).get();
    }

    public void store(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user){
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()){
            userRepository.save(userOptional.get());
            Notification.show("Updating User");
        } else {
            Notification.show("User not found!");
            System.out.println("User not found: " + user);
        }
    }

    /**
     * @param email
     * @return - If user is found then return true, otherwise return false
     */
    public boolean userExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.isPresent();
    }
}
