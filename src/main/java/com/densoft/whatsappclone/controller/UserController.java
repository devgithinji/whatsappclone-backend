package com.densoft.whatsappclone.controller;

import com.densoft.whatsappclone.dto.UpdateUserRequest;
import com.densoft.whatsappclone.model.User;
import com.densoft.whatsappclone.response.ApiResponse;
import com.densoft.whatsappclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(Authentication authentication) {
        User user = userService.findUserProfile(authentication.getName());
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> searchUsers(@RequestParam(required = true) String query) {
        List<User> users = userService.searchUser(query);
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest updateUserRequest, Authentication authentication) {
        User user = userService.findUserProfile(authentication.getName());
        userService.updateUser(user.getId(), updateUserRequest);
        return ResponseEntity.ok(new ApiResponse("user updated successfully", true));
    }
}
