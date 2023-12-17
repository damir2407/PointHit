package com.example.weblabb4.service;


import com.example.weblabb4.config.jwt.JwtProvider;
import com.example.weblabb4.dto.request.AuthRequest;
import com.example.weblabb4.dto.request.RegistrationRequest;
import com.example.weblabb4.entity.RoleEntity;
import com.example.weblabb4.entity.UserEntity;
import com.example.weblabb4.exception.UserAlreadyExistException;
import com.example.weblabb4.exception.UserNotFoundException;
import com.example.weblabb4.repository.RoleRepo;
import com.example.weblabb4.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public String authUser(AuthRequest authRequest) {
        UserEntity userEntity = findByUsernameAndPassword(authRequest.getUsername(),
            authRequest.getPassword());
        return jwtProvider.generateToken(userEntity.getUsername());
    }

    public Long registerUser(RegistrationRequest registrationRequest) {
        UserEntity user = new UserEntity();
        user.setPassword(registrationRequest.getPassword());
        user.setUsername(registrationRequest.getUsername());
        return saveUser(user).getId();
    }


    private UserEntity saveUser(UserEntity userEntity) throws UserAlreadyExistException {
        log.info("Сохранение пользователя: " + userEntity.getUsername());
        if (userRepo.findByUsername(userEntity.getUsername()).isPresent()) {
            log.error("Пользователь с именем " + userEntity.getUsername() + " уже существует");
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует");
        }
        if (!roleRepo.findByName("ROLE_USER").isPresent()) {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setName("ROLE_USER");
            roleEntity.setId(1);
            roleRepo.save(roleEntity);
        }
        RoleEntity userRole = roleRepo.findByName("ROLE_USER").get();
        userEntity.setRoleEntity(userRole);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepo.save(userEntity);
    }


    public UserEntity findByUsername(String username) {
        return userRepo.findByUsername(username)
            .orElseThrow(() -> {
                log.error("Произошла ошибка! Пользователя с именем: " + username + " не существует!");
                return new UserNotFoundException("Пользователя не существует!");
            });
    }

    public UserEntity findByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        UserEntity userEntity = findByUsername(username);
        if (passwordEncoder.matches(password, userEntity.getPassword())) {
            return userEntity;
        } else {
            log.error("Неправильный логин или пароль!");
            throw new UserNotFoundException("Неправильный логин или пароль!");
        }
    }
}

