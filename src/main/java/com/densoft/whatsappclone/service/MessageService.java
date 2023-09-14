package com.densoft.whatsappclone.service;

import com.densoft.whatsappclone.dto.SendMessageRequest;
import com.densoft.whatsappclone.model.Message;
import com.densoft.whatsappclone.model.User;

import java.util.List;

public interface MessageService {

    Message sendMessage(SendMessageRequest req, User user);

    List<Message> getChatMessages(Integer chatId, User reqUser);

    Message findMessageById(Integer messageId);

    void deleteMessage(Integer messageId, User reqUser);
}
