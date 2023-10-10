package com.example.newsapi.dto.user;

import com.example.newsapi.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor @AllArgsConstructor
public class RegDto {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String parentName;
    private Set<Role> roles;
}
