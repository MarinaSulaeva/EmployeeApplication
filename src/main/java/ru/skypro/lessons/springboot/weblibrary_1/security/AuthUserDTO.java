package ru.skypro.lessons.springboot.weblibrary_1.security;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthUserDTO {
    private Long id;
    private String username;
    private String password;
    private int enabled;
    private Role role;

    public static AuthUserDTO fromAuthUser(AuthUser authUser) {
        AuthUserDTO authUserDTO = new AuthUserDTO();
        authUserDTO.setId(authUser.getId());
        authUserDTO.setUsername(authUser.getUsername());
        authUserDTO.setPassword(authUser.getPassword());
        authUserDTO.setEnabled(authUser.getEnabled());
        authUserDTO.setRole(authUser.getRole());
        return authUserDTO;
    }

    public AuthUser toAuthUser() {
        AuthUser authUser = new AuthUser();
        authUser.setId(this.getId());
        authUser.setUsername(this.getUsername());
        authUser.setPassword(this.getPassword());
        authUser.setEnabled(this.getEnabled());
        authUser.setRole(this.getRole());
        return authUser;
    }
}
