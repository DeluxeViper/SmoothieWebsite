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
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Transactional
    public boolean addUser(User user) {
        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setIntake(user.getIntake());
        newUser.setActive(user.isActive());
        newUser.setPassword(user.getPassword());
        newUser.setRoles(user.getRoles());
        newUser.setSmoothies(user.getSmoothies());

        User savedUser = userRepository.save(newUser);
        return userRepository.findById(savedUser.getId()).isPresent();
    }

    public void updateUser(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
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
