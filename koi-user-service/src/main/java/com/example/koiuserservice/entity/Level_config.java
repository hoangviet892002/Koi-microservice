package com.example.koiuserservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "level_config")
public class Level_config {

        @Column(nullable = false)
        private int level;
        @Column(name = "experience_required", nullable = false)
        private int experienceRequired;
}
