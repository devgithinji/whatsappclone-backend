package com.densoft.whatsappclone.service.impl;

import com.densoft.whatsappclone.dto.SendMessageRequest;
import com.densoft.whatsappclone.exception.MessageException;
import com.densoft.whatsappclone.exception.UserException;
import com.densoft.whatsappclone.model.Chat;
import com.densoft.whatsappclone.model.Message;
import com.densoft.whatsappclone.model.User;
import com.densoft.whatsappclone.repository.MessageRepository;
import com.densoft.whatsappclone.service.ChatService;
import com.densoft.whatsappclone.service.MessageService;
import com.densoft.whatsappclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ChatService chatService;

    @Override
    public Message sendMessage(SendMessageRequest req, User user) {
        Chat chat = chatService.findChatById(req.getChatId());

        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(req.getContent());
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getChatMessages(Integer chatId, User reqUser) {
        Chat chat = chatService.findChatById(chatId);
        if (!chat.getUsers().contains(reqUser))
            throw new UserException("user not allowed to access chat: " + chat.getId());
        return messageRepository.findByChatId(chat.getId());
    }

    @Override
    public Message findMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElseThrow(() -> new MessageException("no message found with id: " + messageId));
    }

    @Override
    public void deleteMessage(Integer messageId, User reqUser) {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new MessageException("no message found with id: " + messageId));
        if(!message.getUser().getId().equals(reqUser.getId()))  throw new UserException("user not allowed to delete message: " + message.getId());
        messageRepository.delete(message);
    }
}
