package ru.skypro.lessons.springboot.weblibrary_1.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUserDTO userDTO = AuthUserDTO.fromAuthUser(userRepository.findByUsername(username));
        if (userDTO == null) {
            throw new UsernameNotFoundException(username);
        }

        return new SecurityUserPrincipal(userDTO);
    }
}
