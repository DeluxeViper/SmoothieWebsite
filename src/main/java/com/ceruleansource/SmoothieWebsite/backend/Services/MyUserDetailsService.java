package com.ceruleansource.SmoothieWebsite.backend.Services;

import com.ceruleansource.SmoothieWebsite.backend.Models.Smoothie;
import com.ceruleansource.SmoothieWebsite.backend.Models.user.MyUserDetails;
import com.ceruleansource.SmoothieWebsite.backend.Models.user.User;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.UserRepository;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User.UserBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmailAndRoles(email, "USER");

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));

        return user.map(MyUserDetails::new).get();
//        List<String> splitString = Arrays.asList(emailAndRole.split(","));
//        String role;
//        String email;
//        System.out.println(splitString);
//        if (splitString.size() >= 2){
//            role = splitString.get(1).trim();
//            email = splitString.get(0).trim();
//            if (role.equals("USER")){
//                Optional<User> userOpt = userRepository.findByEmailAndRoles(email, role);
//                if (userOpt.isPresent()){
//                    return userOpt.map(MyUserDetails::new).get();
//                } else {
//                    throw new UsernameNotFoundException("User not found: " + emailAndRole);
//                }
//            } else if (role.equals("GOOGLE_USER")) {
//                Optional<User> googleUserOpt = userRepository.findByEmailAndRoles(email, role);
//                if (googleUserOpt.isPresent()){
//                    return googleUserOpt.map(MyUserDetails::new).get();
//                } else {
//                    throw new UsernameNotFoundException("Google User not found: " + emailAndRole);
//                }
//            } else {
//                throw new UsernameNotFoundException("Google User not found: " + emailAndRole);
//            }
//        } else {
//            System.out.println("MyUserDetailsService: loadbyUsername: Role and/or email not found.");
////            Notification.show("Error! User not found.").addThemeVariants(NotificationVariant.LUMO_ERROR);
//            throw new InternalAuthenticationServiceException("User not found");
//        }
//        Optional<User> normalUserOpt = userRepository.findByEmailAndRoles(emailAndRole, "USER");
//        if (normalUserOpt.isEmpty()) {
//            System.out.println("Normal user empty");
//            Optional<User> googleUser = userRepository.findByEmailAndRoles(emailAndRole, "GOOGLE_USER");
//            if (googleUser.isPresent()) {
//                System.out.println("Google user found");
//                return googleUser.map(MyUserDetails::new).get();
//            } else {
//                Notification.show("User not found!").addThemeVariants(NotificationVariant.LUMO_ERROR);
//                throw new UsernameNotFoundException("User not found: " + emailAndRole);
//            }
//        }
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

    // Currently not in use
    public void updateUser(User user) {
        Optional<User> userOptional = userRepository.findByEmailAndRoles(user.getEmail(), "USER");
        if (userOptional.isPresent()) {
            userRepository.save(userOptional.get());
            Notification.show("Updating User");
        } else {
            Notification.show("User not found!");
//            System.out.println("User not found: " + user);
        }
    }

    /**
     * @param email - checking if user exists with this email
     * @return - If user is found then return true, otherwise return false
     */
    public boolean userExists(String email) {
        Optional<User> user = userRepository.findByEmailAndRoles(email, "USER");

        return user.isPresent();
    }

    public User getUserByEmailAndRole(String email, String role){
        Optional<User> userOptional = userRepository.findByEmailAndRoles(email, role);
        if (userOptional.isPresent()){
            return userOptional.get();
        }
        throw new UsernameNotFoundException("User not found: " + email + ", " + role);
    }
}
