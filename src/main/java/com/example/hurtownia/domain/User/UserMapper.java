package com.example.hurtownia.domain.user;

import org.mapstruct.Mapper;

@Mapper
public abstract class UserMapper {

    public abstract UserDTO mapToDto(User user);

    public abstract User mapToEntity(UserCreateRequest userCreateRequest);

    public abstract User mapToEntity(UserUpdateRequest userUpdateRequest);
}
