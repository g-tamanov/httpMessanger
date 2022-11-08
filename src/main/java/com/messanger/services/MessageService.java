package com.messanger.services;

import com.messanger.resources.MessageRepository;
import com.messanger.resources.UserRepository;
import com.messanger.model.Message;
import com.messanger.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    private MessageRepository repository;
    private UserRepository userRepository;

    public MessageService(MessageRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Message> getAll() {
        return this.repository.getAll();
    }

    public Message getById(UUID uuid) {
        return this.repository.getByUUID(uuid);
    }

    public String setMessage(Message message, UUID uuid) {
        if (userRepository.getUserByUUID(uuid) != null) {
            message.setUuid(UUID.randomUUID());
            message.setTimestamp(new java.util.Date());
            User sender=userRepository.getUserByUUID(uuid);
            sender.setLogin("");
            sender.setPassword("");
            message.setSender(sender);
            this.repository.setMessage(message);
            return message.getUuid().toString();
        }
        throw new RuntimeException("User not found");
    }

    public String remMessage(Message message, UUID uuid) {
        if (userRepository.getUserByUUID(uuid) != null) {
            this.repository.remByUUID(message.getUuid());
            return message.getUuid().toString();
        }
        throw new RuntimeException("User not found");
    }

    public String editMessage(Message message, UUID uuid) {
        Message m=this.repository.getByUUID(message.getUuid());
        if (userRepository.getUserByUUID(uuid) != null && m != null) {
            if(m.getSender().equals(userRepository.getUserByUUID(uuid))){
                m.setMessage(message.getMessage());
                m.setTimestamp(new java.util.Date());
                m.setSender(userRepository.getUserByUUID(uuid));
                return message.getUuid().toString();
            }
            throw new RuntimeException("User not found");
        }
        throw new RuntimeException("User not found");
    }
}
