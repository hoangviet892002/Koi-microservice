package com.example.koiuserservice.modal.response.auth;


import jakarta.ws.rs.DefaultValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    @DefaultValue("admin")
    private String token;
}
