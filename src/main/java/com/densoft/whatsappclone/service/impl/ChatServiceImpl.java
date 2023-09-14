package com.densoft.whatsappclone.service.impl;

import com.densoft.whatsappclone.dto.GroupChatRequest;
import com.densoft.whatsappclone.exception.ChatException;
import com.densoft.whatsappclone.exception.UserException;
import com.densoft.whatsappclone.model.Chat;
import com.densoft.whatsappclone.model.User;
import com.densoft.whatsappclone.repository.ChatRepository;
import com.densoft.whatsappclone.service.ChatService;
import com.densoft.whatsappclone.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;

    @Override
    public Chat createChat(User reqUser, Integer recipientUserId) {
        User recipientUser = userService.findUserById(recipientUserId);

        Chat existingChat = chatRepository.findSingleChatByUserIds(recipientUser, reqUser);

        if (existingChat != null) return existingChat;

        Chat chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().addAll(List.of(reqUser, recipientUser));
        chat.setIsGroup(false);

        return chat;
    }

    @Override
    public Chat findChatById(Integer chatId) {
        return chatRepository.findById(chatId).orElseThrow(() -> new ChatException("no chat found with id: " + chatId));
    }

    @Override
    public List<Chat> findAllChatByUserId(User user) {
        return chatRepository.findChatByUserId(user.getId());
    }

    @Override
    public Chat createGroup(GroupChatRequest req, User user) {

        Chat group = new Chat();
        group.setIsGroup(true);
        group.setChatImage(req.getChatImage());
        group.setChatName(req.getChatName());
        group.setCreatedBy(user);
        group.getAdmins().add(user);

        for (Integer userId : req.getUserIds()) {
            User existingUser = userService.findUserById(userId);
            group.getUsers().add(existingUser);
        }

        return chatRepository.save(group);
    }

    @Override
    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatException("no chat found with id: " + chatId));
        User user = userService.findUserById(userId);
        if (!chat.getAdmins().contains(reqUser)) throw new UserException("you are  not an admin");
        chat.getUsers().add(user);
        return chatRepository.save(chat);
    }

    @Override
    public Chat renameGroup(Integer chatId, String groupName, User reqUser) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatException("no chat found with id: " + chatId));

        if (!chat.getUsers().contains(reqUser)) throw new UserException("you are  not a group member");

        chat.setChatName(groupName);

        return chatRepository.save(chat);
    }

    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatException("no chat found with id: " + chatId));

        User userToBeRemoved = userService.findUserById(userId);

        if (!chat.getAdmins().contains(reqUser)) throw new UserException("you are  not an admin");

        chat.getUsers().remove(userToBeRemoved);
        chat.getAdmins().remove(userToBeRemoved);

        return chatRepository.save(chat);
    }

    @Override
    public void deleteChat(Integer chatId, User user) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatException("no chat found with id: " + chatId));
        chatRepository.deleteById(chat.getId());
    }
}
