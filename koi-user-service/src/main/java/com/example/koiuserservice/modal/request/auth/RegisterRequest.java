package com.example.koiuserservice.modal.request.auth;

import com.example.koiuserservice.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
public class RegisterRequest {

    private Long id;

    private String username;

    private String password;

    private String email;


}
