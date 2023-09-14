package com.densoft.whatsappclone.controller;

import com.densoft.whatsappclone.dto.SendMessageRequest;
import com.densoft.whatsappclone.model.Message;
import com.densoft.whatsappclone.model.User;
import com.densoft.whatsappclone.response.ApiResponse;
import com.densoft.whatsappclone.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/create")
    public ResponseEntity<Message> sendMessageHandler(@RequestBody SendMessageRequest sendMessageRequest, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.sendMessage(sendMessageRequest, user));
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatMessagesHandler(@PathVariable("chatId") Integer chatId, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.getChatMessages(chatId, user));
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessageHandler(@PathVariable("messageId") Integer messageId, @AuthenticationPrincipal User user) {
        messageService.deleteMessage(messageId, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("message deleted successfully", true));
    }
}
