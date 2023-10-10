package com.example.newsapi.controller;

import com.example.newsapi.configuration.security.jwt.JWTTokenProvider;
import com.example.newsapi.dto.user.AuthDto;
import com.example.newsapi.dto.user.RegDto;
import com.example.newsapi.dto.user.UserDto;
import com.example.newsapi.entity.user.User;
import com.example.newsapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

import static com.example.newsapi.controller.util.Validator.checkBindingResult;
import static com.example.newsapi.mapper.UserMapper.INSTANCE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<UserDto> registration(@RequestBody @Valid RegDto regDto,
                                                BindingResult bindingResult){
        checkBindingResult(bindingResult);
        return ok(INSTANCE.userToUserDto(userService.save(INSTANCE.regDtoToUser(regDto))));
    }

    @PostMapping("/auth")
    public ResponseEntity<String> authorization(@RequestBody @Valid AuthDto authDto,
                                                BindingResult bindingResult){
        checkBindingResult(bindingResult);
        User user = userService.login(INSTANCE.authDtoToUser(authDto));
        return ok(jwtTokenProvider.generateToken(user.getUsername(), user.getRoles()));
    }
}
