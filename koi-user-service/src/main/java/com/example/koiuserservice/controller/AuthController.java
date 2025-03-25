package com.example.koiuserservice.controller;

import com.example.koishareservice.base.BaseController;
import com.example.koishareservice.base.BaseResponse;
import com.example.koishareservice.base.BaseService;
import com.example.koiuserservice.entity.Users;
import com.example.koiuserservice.modal.request.auth.LoginRequest;
import com.example.koiuserservice.modal.request.auth.RegisterRequest;
import com.example.koiuserservice.service.UsersService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@ControllerAdvice
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController<Users> {

    public AuthController(@Qualifier("usersService") UsersService service) {
        super(service);
    }
    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(BaseResponse.success(((UsersService)service).login(loginRequest)));
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(BaseResponse.success(((UsersService)service).register(registerRequest)));
    }

}
