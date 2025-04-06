package com.simplesdental.product.controller;


import com.simplesdental.product.dto.CreateUserDTO;
import com.simplesdental.product.dto.CredentialDTO;
import com.simplesdental.product.dto.PasswordUserDTO;
import com.simplesdental.product.dto.UserDTO;
import com.simplesdental.product.model.User;
import com.simplesdental.product.openapi.AuthControllerOpenApi;
import com.simplesdental.product.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController implements AuthControllerOpenApi {

    @Autowired
    AuthenticationService service;

    @Override
    @PostMapping("auth/login")
    public String login(@RequestBody @Valid CredentialDTO data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() != "anonymousUser") {
            User user = (User) authentication.getPrincipal();
        }
        return this.service.login(data);
    }

    @Override
    @PostMapping("auth/context")
    public UserDTO context() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() != "anonymousUser") {
            User user = (User) authentication.getPrincipal();
        }
        return this.service.context();
    }
    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("user/register")
    public UserDTO createUser(@RequestBody @Valid CreateUserDTO data) {
        return this.service.register(data);
    }

    @Override
    @PutMapping("user/password")
    public UserDTO updatePassword(@RequestBody @Valid PasswordUserDTO data) {
        return this.service.updatePassword(data);
    }
}
