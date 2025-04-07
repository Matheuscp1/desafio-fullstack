package com.simplesdental.product.service;


import com.simplesdental.product.dto.CreateUserDTO;
import com.simplesdental.product.dto.CredentialDTO;
import com.simplesdental.product.dto.PasswordUserDTO;
import com.simplesdental.product.dto.TokenDTO;
import com.simplesdental.product.dto.UpdateUserDTO;
import com.simplesdental.product.dto.UserDTO;
import com.simplesdental.product.exception.NotFoundException;
import com.simplesdental.product.model.User;
import com.simplesdental.product.repository.UserRepository;
import com.simplesdental.product.utils.PageableQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public TokenDTO login(CredentialDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() != "anonymousUser") {
            User user = (User) authentication.getPrincipal();
        }
        return new TokenDTO(token);
    }

    public UserDTO register(CreateUserDTO data) {
        try {
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
            data.setPassword(encryptedPassword);
            User newUser = data.toEntitie();
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
            UserDTO userCache = (UserDTO) redisTemplate.opsForValue().get("user:" + user.getId());
            if (userCache != null) {
                return userCache;
            }
            Optional<User> userDetails = repository.findById(user.getId());
            UserDTO userDto =  new UserDTO(userDetails.get());
            redisTemplate.opsForValue().set("user:" + userDetails.get().getId(), userDto, 10, TimeUnit.MINUTES);
            return userDto;
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
            redisTemplate.delete("user:" + userDetails.get().getId());

            return new UserDTO(userDetails.get());
        }
        return null;
    }

    public Page<UserDTO> findAll(PageableQuery filter) {
        logger.info("findAll");
        Map<String, String> allowedSortFields = Map.ofEntries(
                Map.entry("id", "id")
        );
        return repository.findAll(PageableQuery.toPageable(filter,allowedSortFields))
                .map(UserDTO::new);
    }


    public Optional<UserDTO> findById(Long id) {
        logger.info("findById");

        return repository.findById(id).map(UserDTO::new);
    }

    public void deleteById(Long id) {
        logger.info("deleteById");
        repository.deleteById(id);
    }

    public UpdateUserDTO updateUser(Long id, UpdateUserDTO user) {
       return repository.findById(id)
                .map(userFound -> {
                    User entitie = user.toEntitie();
                    entitie.setPassword(userFound.getPassword());
                    entitie.setId(id);
                    repository.save(entitie);
                    return user;
                })
                .orElseThrow(()
                        -> new NotFoundException("User not found"));
    }
}
