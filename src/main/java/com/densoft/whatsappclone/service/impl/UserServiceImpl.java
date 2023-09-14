package com.densoft.whatsappclone.service.impl;

import com.densoft.whatsappclone.config.TokenProvider;
import com.densoft.whatsappclone.dto.UpdateUserRequest;
import com.densoft.whatsappclone.exception.UserException;
import com.densoft.whatsappclone.model.User;
import com.densoft.whatsappclone.repository.UserRepository;
import com.densoft.whatsappclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Override
    public User findUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UserException("User not found with id: " + id));
    }

    @Override
    public User findUserProfile(String email) {

        return userRepository.findByEmail(email).orElseThrow(() -> new UserException("User not found with email: " + email));
    }

    @Override
    public User updateUser(Integer userId, UpdateUserRequest updateUserRequest) {
        User user = findUserById(userId);
        user.setFullName(updateUserRequest.getFullName());
        user.setProfilePicture(updateUserRequest.getProfilePicture());
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }
}
