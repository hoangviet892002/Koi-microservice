package com.example.koiuserservice.repository;


import com.example.koishareservice.base.BaseRepository;
import com.example.koiuserservice.entity.Users;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository  extends BaseRepository<Users, Long> {
    Users findByEmail(String email);
    Users findByUsername(String username);
}
