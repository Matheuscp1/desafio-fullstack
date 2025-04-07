package com.simplesdental.product.controller;


import com.simplesdental.product.dto.CreateUserDTO;
import com.simplesdental.product.dto.CredentialDTO;
import com.simplesdental.product.dto.PasswordUserDTO;
import com.simplesdental.product.dto.TokenDTO;
import com.simplesdental.product.dto.UpdateUserDTO;
import com.simplesdental.product.dto.UserDTO;
import com.simplesdental.product.model.User;
import com.simplesdental.product.openapi.AuthControllerOpenApi;
import com.simplesdental.product.service.AuthenticationService;
import com.simplesdental.product.utils.PageableQuery;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public TokenDTO login(@RequestBody @Valid CredentialDTO data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() != "anonymousUser") {
            User user = (User) authentication.getPrincipal();
        }
        return this.service.login(data);
    }

    @Override
    @GetMapping("auth/context")
    public UserDTO context() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() != "anonymousUser") {
            User user = (User) authentication.getPrincipal();
        }
        return this.service.context();
    }
    @Override
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/user")
    public UserDTO createUser(@RequestBody @Valid CreateUserDTO data) {
        return this.service.register(data);
    }

    @Override
    @PutMapping("user/password")
    public UserDTO updatePassword(@RequestBody @Valid PasswordUserDTO data) {
        return this.service.updatePassword(data);
    }

    @Override
    @GetMapping("/user")
    public Page<UserDTO> getAllUsers(PageableQuery filter) {
        return service.findAll(filter);
    }

    @Override
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> geUserById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @PutMapping("/user/{id}")
    public UpdateUserDTO updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserDTO user) {
        return service.updateUser(id,user);
    }

    @Override
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return service.findById(id)
                .map(product -> {
                    service.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
