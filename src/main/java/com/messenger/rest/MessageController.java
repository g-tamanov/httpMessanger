package com.messenger.rest;

import com.messenger.services.MessageService;
import com.messenger.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService service;


    public MessageController(MessageService service) {
        this.service = service;
    }

    /**
     * Получить все сообщения
     *
     * @return список сообщений
     */
    @GetMapping(path = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Message> getAll() {
        return this.service.getAll();
    }

    /**
     * Получить сообщение по uuid
     *
     * @param message сообщение с параметром uuid сообщения
     * @return возвращает полное сообщение по uuid или ошибку
     */
    @GetMapping(path = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity<Message> getMessage(Message message) {
        Message m = this.service.getByUuid(message.getUuid());
        if (m != null) {
            return new ResponseEntity<>(m, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    /**
     * Отправить сообщение
     * Пример {message: "текст сообщения"}
     *
     * @param message сообщение с параметром message
     * @param uuid    uuid текущего пользователя
     * @return в случае успеха возвращает uuid сообщения
     */
    @PostMapping(path = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Message> setMessage(@RequestBody Message message, @RequestHeader("user-uuid") UUID uuid) {
        this.service.setMessage(message, uuid);
        Message mes = this.service.getBySenderSession(uuid);
        if (mes != null)
            return new ResponseEntity<>(message, HttpStatus.OK);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    /**
     * Удалить сообщение
     *
     * @param message сообщение с параметром uuid сообщения
     * @param uuid    uuid текущего пользователя
     * @return в случае успеха возвращает uuid сообщения
     */
    @DeleteMapping(path = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Message> remMessage(@RequestBody Message message, @RequestHeader("user-uuid") UUID uuid) {
        Message rem = this.service.getByUuid(message.getUuid());
        if (rem != null) {
            this.service.remMessage(message, uuid);
            return new ResponseEntity<>(rem, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    /**
     * Отредактировать сообщение
     *
     * @param message новое сообщение с uuid сообщения которое нужно отредактировать
     * @param uuid    uuid текущего пользователя
     * @return в случае успеха возвращает uuid сообщения
     */
    @PutMapping(path = "/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Message> editMessage(@RequestBody Message message, @RequestHeader("user-uuid") UUID uuid) {
        Message edit = this.service.getByUuid(message.getUuid());
        if (edit != null) {
            this.service.editMessage(message, uuid);
            return new ResponseEntity<>(edit, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
