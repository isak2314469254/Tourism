package com.trytry.lasttry.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


@RestController
public class trytry {
    @RequestMapping("/hello1")
    public String hello(String name){
        if(Objects.equals(name, "yzy")) return null;
        System.out.println("nice try!");
        System.out.println("Hello, " + name + "!");
        return "Hello, " + name + ", nice try !";
    }
}
