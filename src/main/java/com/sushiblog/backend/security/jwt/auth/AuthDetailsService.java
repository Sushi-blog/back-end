package com.sushiblog.backend.security.jwt.auth;

import com.sushiblog.backend.entity.user.UserRepository;
import com.sushiblog.backend.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public AuthDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return userRepository.findById(userEmail)
                .map(AuthDetails::new)
                .orElseThrow(UserNotFoundException::new);
    }

}
