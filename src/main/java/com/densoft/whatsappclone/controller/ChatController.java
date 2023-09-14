package com.densoft.whatsappclone.controller;

import com.densoft.whatsappclone.dto.GroupChatRequest;
import com.densoft.whatsappclone.dto.SingleChatRequest;
import com.densoft.whatsappclone.model.Chat;
import com.densoft.whatsappclone.model.User;
import com.densoft.whatsappclone.response.ApiResponse;
import com.densoft.whatsappclone.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/single")
    public ResponseEntity<Chat> createChat(@RequestBody SingleChatRequest singleChatRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.createChat(user, singleChatRequest.getUserId()));
    }


    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupChat(@RequestBody GroupChatRequest groupChatRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chatService.createGroup(groupChatRequest, user));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatById(@PathVariable(value = "chatId", required = true) Integer chatId, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(chatService.findChatById(chatId));
    }


    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findAllChatByUserId(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(chatService.findAllChatByUserId(user));
    }


    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroup(@PathVariable(value = "chatId", required = true) Integer chatId,
                                               @PathVariable(value = "userId", required = true) Integer userId,
                                               @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(chatService.addUserToGroup(userId, chatId, user));
    }


    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat> removeUserFromGroup(@PathVariable(value = "chatId", required = true) Integer chatId,
                                                    @PathVariable(value = "userId", required = true) Integer userId,
                                                    @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(chatService.removeFromGroup(userId, chatId, user));
    }

    @PutMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChat(@PathVariable(value = "chatId", required = true) Integer chatId,
                                                  @AuthenticationPrincipal User user) {
        chatService.deleteChat(chatId, user);
        return ResponseEntity.ok(new ApiResponse("chat deleted", true));
    }


}
