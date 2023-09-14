package com.densoft.whatsappclone.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String chatName;
    private String chatImage;

    private Boolean isGroup;
    @ManyToOne
    private User createdBy;
    @ManyToMany
    private Set<User> admins = new HashSet<>();
    @ManyToMany
    private Set<User> users = new HashSet<>();

    @OneToMany
    private List<Message> messages = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;

        if (!Objects.equals(id, chat.id)) return false;
        if (!Objects.equals(chatName, chat.chatName)) return false;
        if (!Objects.equals(chatImage, chat.chatImage)) return false;
        if (!Objects.equals(isGroup, chat.isGroup)) return false;
        if (!Objects.equals(createdBy, chat.createdBy)) return false;
        if (!Objects.equals(users, chat.users)) return false;
        return Objects.equals(messages, chat.messages);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (chatName != null ? chatName.hashCode() : 0);
        result = 31 * result + (chatImage != null ? chatImage.hashCode() : 0);
        result = 31 * result + (isGroup != null ? isGroup.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (messages != null ? messages.hashCode() : 0);
        return result;
    }
}
