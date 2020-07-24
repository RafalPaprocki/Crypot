package com.crypot.controller;

import com.crypot.exchange.BitbayApiAdapter;
import com.crypot.model.Test;
import com.crypot.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TestController {
    TestRepository testRepository;
    BitbayApiAdapter bitbayApiAdapter;

    @Autowired
    public TestController(TestRepository testRepository){
        this.testRepository = testRepository;
        this.bitbayApiAdapter = new BitbayApiAdapter();
    }

    @GetMapping("/test")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Test>> getAllTests(){
        List<Test> tests = new ArrayList<>();
        testRepository.findAll().forEach(tests::add);
        return new ResponseEntity<>(tests, HttpStatus.OK);
    }

    @GetMapping("/test2")
    public ResponseEntity<BitbayApiAdapter.WalletResponse> test() throws Exception{
        return this.bitbayApiAdapter.getAllWallets();
    }

    @PostMapping("/test3")
    public String postt(@RequestBody MarketUUID m ){
        return m.first + ", " + m.second;
    }

    public static class MarketUUID{
        public String first;
        public String second;
    }

}
