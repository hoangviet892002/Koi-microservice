package com.example.koiuserservice.modal.request.auth;


import jakarta.ws.rs.DefaultValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @DefaultValue("admin")
    private String username;
    @DefaultValue("admin")
    private String password;
}
