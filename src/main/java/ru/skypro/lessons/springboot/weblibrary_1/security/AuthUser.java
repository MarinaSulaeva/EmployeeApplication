package ru.skypro.lessons.springboot.weblibrary_1.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "auth_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private int enabled;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}
