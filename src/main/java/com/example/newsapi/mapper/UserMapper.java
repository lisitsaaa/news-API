package com.example.newsapi.mapper;

import com.example.newsapi.dto.user.AuthDto;
import com.example.newsapi.dto.user.RegDto;
import com.example.newsapi.dto.user.UserDto;
import com.example.newsapi.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User regDtoToUser(RegDto dto);

    UserDto userToUserDto(User user);

    User authDtoToUser(AuthDto dto);
}
