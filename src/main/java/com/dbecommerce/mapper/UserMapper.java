package com.dbecommerce.mapper;

import com.dbecommerce.domain.User;
import com.dbecommerce.domain.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto mapToUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getAddress());
    }

    public User mapToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setAddress(userDto.getAddress());
        return user;
    }

    public List<UserDto> mapToListUserDto(List<User> users) {
        return users.stream()
                .map(u -> mapToUserDto(u))
                .collect(Collectors.toList());
    }

}
