package com.oleirosoftware.authentication.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/open")
@Slf4j
public class OpenController {
    

    @GetMapping
    public String openRoute(){
        log.info("Request na rota Open");
        return "Rota Open";
    }
}
