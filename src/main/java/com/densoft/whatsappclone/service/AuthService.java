package com.densoft.whatsappclone.service;

import com.densoft.whatsappclone.dto.LoginRequest;
import com.densoft.whatsappclone.model.User;
import com.densoft.whatsappclone.response.AuthResponse;

public interface AuthService {
    AuthResponse createUser(User user);

    AuthResponse loginUser(LoginRequest request);
}
