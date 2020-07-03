package com.crypot.controller;

import com.crypot.model.Test;
import com.crypot.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TestController {
    TestRepository testRepository;

    @Autowired
    public TestController(TestRepository testRepository){
        this.testRepository = testRepository;
    }

    @GetMapping("/test")
    public ResponseEntity<List<Test>> getAllTests(){
        List<Test> tests = new ArrayList<>();
        testRepository.findAll().forEach(tests::add);
        return new ResponseEntity<>(tests, HttpStatus.OK);
    }
    
}
