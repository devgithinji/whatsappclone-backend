package com.densoft.whatsappclone.service.impl;

import com.densoft.whatsappclone.config.TokenProvider;
import com.densoft.whatsappclone.dto.LoginRequest;
import com.densoft.whatsappclone.exception.UserException;
import com.densoft.whatsappclone.model.User;
import com.densoft.whatsappclone.repository.UserRepository;
import com.densoft.whatsappclone.response.AuthResponse;
import com.densoft.whatsappclone.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    public AuthResponse createUser(User user) {
        boolean isExisting = userRepository.findByEmail(user.getEmail()).isPresent();

        if (isExisting) throw new UserException("existing user found with similar details");

        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);

        return getAuthResponse(user);
    }

    @Override
    public AuthResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new BadCredentialsException("invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new BadCredentialsException("invalid username or password");

        return getAuthResponse(user);
    }

    private AuthResponse getAuthResponse(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return new AuthResponse(jwt, true);
    }
}
