package com.densoft.whatsappclone.repository;

import com.densoft.whatsappclone.model.Chat;
import com.densoft.whatsappclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    @Query("SELECT c FROM Chat c JOIN c.users u WHERE u.id = :userId")
    List<Chat> findChatByUserId(@Param("userId") Integer userId);

    @Query("SELECT c FROM  Chat  c WHERE c.isGroup= false  AND :user MEMBER  OF c.users AND :reqUser MEMBER OF c.users")
    Chat findSingleChatByUserIds(@Param("user") User user, @Param("reqUser") User reqUser);
}
