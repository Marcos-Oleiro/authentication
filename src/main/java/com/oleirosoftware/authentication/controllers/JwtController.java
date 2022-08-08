package com.oleirosoftware.authentication.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/jwt")
@Slf4j
public class JwtController {
    

    @GetMapping
    public String test(){

        log.info("Request na rota com JWT");
        return "ok";
    }
}
