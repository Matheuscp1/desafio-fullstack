package com.simplesdental.product.service;


import com.simplesdental.product.dto.CreateUserDTO;
import com.simplesdental.product.dto.CredentialDTO;
import com.simplesdental.product.dto.PasswordUserDTO;
import com.simplesdental.product.dto.UserDTO;
import com.simplesdental.product.enums.UserRole;
import com.simplesdental.product.model.User;
import com.simplesdental.product.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public String login(CredentialDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() != "anonymousUser") {
            User user = (User) authentication.getPrincipal();
        }
        return tokenService.generateToken((User) auth.getPrincipal());
    }

    public UserDTO register(CreateUserDTO data) {
        try {
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
            data.setPassword(encryptedPassword);
            User newUser = data.toEntitie();
            newUser.setRole(UserRole.user);
            newUser = this.repository.save(newUser);
            return new UserDTO(newUser);
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage());
            throw new DataIntegrityViolationException("Email já existe");
        }
    }

    public UserDTO context() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() != "anonymousUser") {
            User user = (User) authentication.getPrincipal();
            user.getId();
            Optional<User> userDetails = repository.findById(user.getId());
            return new UserDTO(userDetails.get());
        }
        return null;
    }

    public UserDTO updatePassword(PasswordUserDTO data) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() != "anonymousUser") {
            User user = (User) authentication.getPrincipal();
            user.getId();
            Optional<User> userDetails = repository.findById(user.getId());
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
            userDetails.get().setPassword(encryptedPassword);
            repository.save(userDetails.get());
            return new UserDTO(userDetails.get());
        }
        return null;
    }
}
