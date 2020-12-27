package com.ceruleansource.SmoothieWebsite.backend.Configuration;

import com.helger.commons.annotation.UsedViaReflection;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Configuration
@RestController
@RequestMapping("/user")
public class ErrorPageConfig {
//    @Bean
//    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer(){
//        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
//            @Override
//            public void customize(ConfigurableWebServerFactory factory) {
//                ErrorPage page404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
//                ErrorPage page500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");
//                factory.addErrorPages(page404, page500);
//            }
//        };
//    }

}
