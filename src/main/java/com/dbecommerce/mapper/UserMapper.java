package com.dbecommerce.mapper;

import com.dbecommerce.domain.User;
import com.dbecommerce.domain.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setAddress(user.getAddress());
        userDto.setUsername(user.getUsername());
        userDto.getRole().addAll(user.getRole());
        return userDto;
    }

    public User mapToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setAddress(userDto.getAddress());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.getRole().addAll(userDto.getRole());
        return user;
    }

    public List<UserDto> mapToListUserDto(List<User> users) {
        return users.stream()
                .map(u -> mapToUserDto(u))
                .collect(Collectors.toList());
    }

}
