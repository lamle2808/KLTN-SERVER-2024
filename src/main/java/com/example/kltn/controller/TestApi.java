package com.example.kltn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestApi {

    @GetMapping("/getdata")
    public ResponseEntity<?> getdata(){
        return ResponseEntity.ok().body("Hello World !!!");
    }
}
