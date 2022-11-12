package com.messenger.services;

import com.messenger.resources.MessageRepository;
import com.messenger.resources.UserRepository;
import com.messenger.model.Message;
import com.messenger.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    private final MessageRepository repository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Message> getAll() {
        return this.repository.getAll();
    }

    public Message getByUuid(UUID uuid) {
        return this.repository.getByUUID(uuid);
    }

    public Message getBySenderSession(UUID session) {
        User sender=userRepository.getUserBySession(session);
        if (sender != null) {
            return this.repository.getAll().stream()
                    .filter(message -> message.getSender().getSession().equals(sender.getSession()))
                    .findFirst().orElse(null);
        }
        return null;
    }

    public void setMessage(Message message, UUID uuid) {
        User sender=userRepository.getUserBySession(uuid);
        if (sender != null) {
            message.setUuid(UUID.randomUUID());
            message.setTimestamp(new java.util.Date());
            message.setSender(sender);
            this.repository.setMessage(message);
            return;
        }
        throw new RuntimeException("User not found");
    }

    public void remMessage(Message message, UUID uuid) {
        if (userRepository.getUserBySession(uuid) != null) {
            this.repository.remByUUID(message.getUuid());
            return;
        }
        throw new RuntimeException("User not found");
    }

    public void editMessage(Message message, UUID session) {
        Message m=this.repository.getByUUID(message.getUuid());
        if (userRepository.getUserBySession(session) != null && m != null) {
            if(m.getSender().equals(userRepository.getUserBySession(session))){
                m.setMessage(message.getMessage());
                m.setTimestamp(new java.util.Date());
                m.setSender(userRepository.getUserBySession(session));
                return;
            }
            throw new RuntimeException("User not found");
        }
        throw new RuntimeException("User not found");
    }
}
