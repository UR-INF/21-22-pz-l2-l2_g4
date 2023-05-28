package com.example.hurtownia.domain.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Reprezentuje create request dla obiektu user.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserCreateRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private Boolean isAdmin;
    private Boolean generatingReports;
    private Boolean grantingDiscounts;
}
