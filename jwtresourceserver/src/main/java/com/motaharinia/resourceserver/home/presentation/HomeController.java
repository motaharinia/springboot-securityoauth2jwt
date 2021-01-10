package com.motaharinia.resourceserver.home.presentation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @Value("${spring.application.name}")
    private String springApplicationName;


    @GetMapping("/")
    public String getUrl() {
        return springApplicationName;
    }

}
