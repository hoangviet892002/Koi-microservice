package com.example.koiuserservice.controller;

import com.example.koishareservice.base.BaseController;
import com.example.koishareservice.base.BaseResponse;
import com.example.koishareservice.base.BaseService;
import com.example.koiuserservice.entity.Users;
import com.example.koiuserservice.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
@RequestMapping("/users")
public class UsersController extends BaseController<Users> {


    @Autowired
    public UsersController(@Qualifier("usersService") UsersService service) {
        super(service);
    }

    @GetMapping
    public ResponseEntity<BaseResponse> findAll(@RequestParam Map<String, String> params) throws Exception {
        return super.findAll(params);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> findOne(@PathVariable long id) {
        return super.findOne(id);
    }

    @PostMapping
    public ResponseEntity<BaseResponse> addEntity(@Valid @RequestBody Users newEntity) throws Exception {
        return super.addEntity(newEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateEntity(@PathVariable long id, @Valid @RequestBody Users updateEntity) throws Exception {
        return super.updateEntity(id, updateEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteEntity(@PathVariable long id) {
        return super.deleteEntity(id);
    }


}
