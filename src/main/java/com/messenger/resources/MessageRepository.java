package com.messenger.resources;

import com.messenger.model.Message;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class MessageRepository {

    private final List<Message> messages;
    private int historySize = 100;

    public MessageRepository() {
        this.messages = new ArrayList<>();
    }

    private void update() {
        if (this.messages.size() >= this.historySize) {
            this.messages.remove(0);
            update();
        }
    }

    public Message getByUUID(UUID uuid) {
        return this.messages.stream()
                .filter(message -> uuid.equals(message.getUuid()))
                .findFirst()
                .orElse(null);
    }

    public Boolean remByUUID(UUID uuid) {
        Message m =
                this.messages.stream()
                        .filter(message -> uuid.equals(message.getUuid()))
                        .findFirst()
                        .orElse(null);
        if (m != null) {
            this.messages.remove(m);
            return true;
        }
        return false;
    }

    public List<Message> getAll() {
        return this.messages;
    }

    public Message setMessage(Message message) {
        this.messages.add(message);
        update();
        return message;
    }

    public void setHistorySize(int size) {
        this.historySize = size;
    }
}
