package com.example.hurtownia.domain.user;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Mapper pomiędzy User i jego DTO.
 */
@Component
public class UserMapper {

    /**
     * Konwerter z User na UserTableViewDTO.
     */
    Converter<User, UserTableViewDTO> userToUserTableViewDTOConverter = context -> {
        UserTableViewDTO userTableViewDTO = new UserTableViewDTO();
        userTableViewDTO.setId(context.getSource().getId());
        userTableViewDTO.setName(context.getSource().getName());
        userTableViewDTO.setSurname(context.getSource().getSurname());
        userTableViewDTO.setEmail(context.getSource().getEmail());
        userTableViewDTO.setPassword(context.getSource().getPassword());
        userTableViewDTO.setPhoneNumber(context.getSource().getPhoneNumber());
        userTableViewDTO.setIsAdmin(context.getSource().getIsAdmin());
        userTableViewDTO.setGeneratingReports(context.getSource().getGeneratingReports());
        userTableViewDTO.setGrantingDiscounts(context.getSource().getGrantingDiscounts());
        return userTableViewDTO;
    };

    /**
     * Konwerter z UserTableViewDTO na User.
     */
    Converter<UserTableViewDTO, User> userTableViewDTOToUserConverter = context -> {
        User user = new User();
        user.setId(context.getSource().getId());
        user.setName(context.getSource().getName());
        user.setSurname(context.getSource().getSurname());
        user.setEmail(context.getSource().getEmail());
        user.setPassword(context.getSource().getPassword());
        user.setPhoneNumber(context.getSource().getPhoneNumber());
        user.setIsAdmin(context.getSource().getIsAdmin());
        user.setGeneratingReports(context.getSource().getGeneratingReports());
        user.setGrantingDiscounts(context.getSource().getGrantingDiscounts());
        return user;
    };

    /**
     * Konwerter z UserCreateDTO na User.
     */
    Converter<UserCreateDTO, User> userCreateDTOToUserConverter = context -> {
        User user = new User();
        user.setName(context.getSource().getName());
        user.setSurname(context.getSource().getSurname());
        user.setEmail(context.getSource().getEmail());
        user.setPassword(context.getSource().getPassword());
        user.setPhoneNumber(context.getSource().getPhoneNumber());
        user.setIsAdmin(context.getSource().getIsAdmin());
        user.setGeneratingReports(context.getSource().getGeneratingReports());
        user.setGrantingDiscounts(context.getSource().getGrantingDiscounts());
        return user;
    };
    private ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.createTypeMap(User.class, UserTableViewDTO.class).setConverter(userToUserTableViewDTOConverter);
        modelMapper.createTypeMap(UserTableViewDTO.class, User.class).setConverter(userTableViewDTOToUserConverter);
        modelMapper.createTypeMap(UserCreateDTO.class, User.class).setConverter(userCreateDTOToUserConverter);
    }

    /**
     * Mapuje User na UserTableViewDTO.
     *
     * @param user
     * @return obiekt UserTableViewDTO
     */
    public UserTableViewDTO toDTO(User user) {
        return Objects.isNull(user) ? null : modelMapper.map(user, UserTableViewDTO.class);
    }

    /**
     * Mapuje UserCreateDTO na User.
     *
     * @param userCreateDTO
     * @return obiekt User
     */
    public User toEntity(UserCreateDTO userCreateDTO) {
        return Objects.isNull(userCreateDTO) ? null : modelMapper.map(userCreateDTO, User.class);
    }

    /**
     * Mapuje UserTableViewDTO na User.
     *
     * @param userTableViewDTO
     * @return obiekt User
     */
    public User toEntity(UserTableViewDTO userTableViewDTO) {
        return Objects.isNull(userTableViewDTO) ? null : modelMapper.map(userTableViewDTO, User.class);
    }
}
