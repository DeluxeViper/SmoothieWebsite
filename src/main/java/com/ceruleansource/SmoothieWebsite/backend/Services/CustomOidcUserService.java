package com.ceruleansource.SmoothieWebsite.backend.Services;

import com.ceruleansource.SmoothieWebsite.backend.Models.user.GoogleOAuth2UserInfo;
import com.ceruleansource.SmoothieWebsite.backend.Models.user.User;
import com.ceruleansource.SmoothieWebsite.backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        Map attributes = oidcUser.getAttributes();
        GoogleOAuth2UserInfo userInfo = new GoogleOAuth2UserInfo();
        userInfo.setEmail((String) attributes.get("email"));
        userInfo.setImageUrl((String) attributes.get("sub"));
        userInfo.setImageUrl((String) attributes.get("picture"));
        userInfo.setFirstName((String) attributes.get("given_name"));
        userInfo.setLastName((String) attributes.get("family_name"));
        updateUser(userInfo);
        return oidcUser;
    }

    @Transactional
    void updateUser(GoogleOAuth2UserInfo userInfo) {
        Optional<User> userOpt = userRepository.findByEmailAndRoles(userInfo.getEmail(), "GOOGLE_USER");
        if (userOpt.isEmpty()){
            User user = new User();
            user.setEmail(userInfo.getEmail());
            user.setActive(true);
            user.setFirstName(userInfo.getFirstName());
            user.setLastName(userInfo.getLastName());
            user.setIntake("0");
            user.setPassword("confidential");
            user.setRoles("GOOGLE_USER");
            userRepository.save(user);
        }
    }
}
