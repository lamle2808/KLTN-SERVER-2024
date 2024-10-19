package com.example.kltn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kltn.entity.Account;
import com.example.kltn.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account) {
        try {
            if (accountService.getByEmail(account.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("email is ready in database");
            } 
            if (accountService.getByPhoneNumber(account.getPhoneNumber()).isPresent()) {
                return ResponseEntity.badRequest().body("phoneNumber is ready in database");
            }
            else {
                Account registeredAccount = accountService.register(account);
                if (registeredAccount == null) {
                    return ResponseEntity.badRequest().body("Error !!");
                }
                return ResponseEntity.ok().body(registeredAccount);
            }
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body("There is an exception when execute!! --> " + exception);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        try {
            String token = accountService.login(account);
            if (token.equals("")) {
                return ResponseEntity.badRequest().body("incorrect email or incorrect password!!");
            }
            Account accountLogin = accountService.getByEmail2(account.getEmail());
            if (!accountLogin.isEnable()){
                return ResponseEntity.badRequest().body("account not found !!");
            }
            //Customer customer = customerService.getByEmail(account.getEmail());
            // if (customer != null) {
            //     CustomerDataBean customerDataBean = accountService.customerLogin(token, customer);
            //     return ResponseEntity.ok().body(customerDataBean);
            // }
            // Employee employee = employeeService.getByEmail(account.getEmail());
            // EmployeeDataBean employeeDataBean = accountService.employeeLogin(token, employee);
            return ResponseEntity.ok().body(token);
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body("There is an exception when execute!! --> " + exception);
        }
    }
}
