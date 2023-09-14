package com.densoft.whatsappclone.service;

import com.densoft.whatsappclone.dto.UpdateUserRequest;
import com.densoft.whatsappclone.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Integer id);

    User findUserProfile(String email);

    User updateUser(Integer userId, UpdateUserRequest updateUserRequest);

    List<User> searchUser(String query);
}
