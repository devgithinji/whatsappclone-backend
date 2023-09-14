package com.densoft.whatsappclone.service;

import com.densoft.whatsappclone.dto.GroupChatRequest;
import com.densoft.whatsappclone.model.Chat;
import com.densoft.whatsappclone.model.User;

import java.util.List;

public interface ChatService {

    Chat createChat(User reqUser, Integer recipientUserId);

    Chat findChatById(Integer chatId);

    List<Chat> findAllChatByUserId(User userId);

    Chat createGroup(GroupChatRequest req, User reqUser);

    Chat addUserToGroup(Integer userId, Integer chatId,User reqUser);

    Chat renameGroup(Integer chatId, String groupName, User reqUser);

    Chat removeFromGroup(Integer chatId, Integer userId, User reqUser);

    void deleteChat(Integer chatId, User reqUser);
}
