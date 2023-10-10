package com.example.newsapi.dto.user;

import com.example.newsapi.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor @AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String parentName;
    private LocalDate creationDate;
    private LocalDate lastEditDate;
    private Set<Role> roles;
}
