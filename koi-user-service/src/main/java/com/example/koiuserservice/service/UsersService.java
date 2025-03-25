package com.example.koiuserservice.service;

import com.example.koishareservice.base.BaseService;
import com.example.koiuserservice.entity.Users;
import com.example.koiuserservice.modal.request.auth.LoginRequest;
import com.example.koiuserservice.modal.request.auth.RegisterRequest;
import com.example.koiuserservice.modal.response.auth.LoginResponse;
import com.example.koiuserservice.repository.UsersRepository;
import com.example.koiuserservice.utils.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.Arrays;
import java.util.List;


@Service

public class UsersService extends BaseService<Users> {


    private final JwtUtils jwtUtils;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    public UsersService(UsersRepository repository, JwtUtils jwtUtils, KafkaTemplate<String, String> kafkaTemplate) {
        super(repository);
        this.jwtUtils = jwtUtils;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public List<String> globalSearchKeys() {
        return Arrays.asList("name", "email");
    }
    @Override
    public Sort defaultSort() {
        return Sort.by(Sort.Direction.DESC, "id");
    }

    //login

    public LoginResponse login(LoginRequest loginRequest) {
        Users user = ((UsersRepository) repository).findByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }
        String token = jwtUtils.generateToken(user);
        return new LoginResponse(token);
    }

    //register
    public Users register(RegisterRequest registerRequest) {
        Users user = new Users();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        Users savedUser = repository.save(user);
        String event = savedUser.getId().toString();
        kafkaTemplate.send("user-registered", event);
        return savedUser;
    }

}
