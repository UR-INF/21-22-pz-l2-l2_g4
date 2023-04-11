package com.example.hurtownia.domain.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserCreateDTO {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private Boolean isAdmin;
    private Boolean generatingReports;
    private Boolean grantingDiscounts;
}
