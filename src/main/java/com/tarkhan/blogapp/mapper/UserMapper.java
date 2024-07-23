package com.tarkhan.blogapp.mapper;

import com.tarkhan.blogapp.entity.Role;
import com.tarkhan.blogapp.entity.User;
import com.tarkhan.blogapp.model.auth.user.GetAccountDto;
import com.tarkhan.blogapp.model.auth.user.RegisterDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(RegisterDto registerDto){
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setRole(Role.USER);
        return user;
    }

    public GetAccountDto toGetAccountDto(User user){
        GetAccountDto getAccountDto = new GetAccountDto();
        getAccountDto.setUsername(user.getUsername());
        getAccountDto.setEmail(user.getEmail());
        getAccountDto.setFirstName(user.getFirstName());
        getAccountDto.setLastName(user.getLastName());
        getAccountDto.setPosts(user.getPosts());
        getAccountDto.setComments(user.getComments());
        getAccountDto.setLikes(user.getLikes());
        getAccountDto.setCreatedAt(user.getCreatedAt());
        getAccountDto.setUpdatedAt(user.getUpdatedAt());
        return getAccountDto;
    }
}
