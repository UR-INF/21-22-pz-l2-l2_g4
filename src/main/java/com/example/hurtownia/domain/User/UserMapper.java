package com.example.hurtownia.domain.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface UserMapper {

    UserDTO mapToDto(User user);

    List<UserDTO> mapToDto(List<User> users);
}
