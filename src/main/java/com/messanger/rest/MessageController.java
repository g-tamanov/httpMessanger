package com.messanger.rest;

import com.messanger.services.MessageService;
import com.messanger.model.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/message")
public class MessageController {

    private MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    /**
     * Получить все сообщения
     * @return список сообщений
     */
    @GetMapping(path = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Message> getAll() {
        return this.service.getAll();
    }

    /**
     * Получить сообщение по uuid
     * @param message сообщение с параметром uuid сообщения
     * @return возвращает полное сообщение по uuid или ошибку
     */
    @GetMapping(path = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Message getMessage(Message message) {
        return this.service.getById(message.getUuid());
    }

    /**
     * Отправить сообщение
     * Пример {message: "текст сообщения"}
     * @param message сообщение с параметром message
     * @param uuid uuid текущего пользователя
     * @return в случае успеха возвращает uuid сообщения
     */
    @PostMapping(path = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String setMessage(@RequestBody Message message, @RequestHeader("user-uuid") UUID uuid) {
        return this.service.setMessage(message, uuid);
    }

    /**
     * Удалить сообщение
     * @param message сообщение с параметром uuid сообщения
     * @param uuid uuid текущего пользователя
     * @return в случае успеха возвращает uuid сообщения
     */
    @DeleteMapping(path = "/remove", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String remMessage(@RequestBody Message message, @RequestHeader("user-uuid") UUID uuid) {
        return this.service.remMessage(message, uuid);
    }

    /**
     * Отредактировать сообщение
     * @param message новое сообщение с uuid сообщения которое нужно отредактировать
     * @param uuid uuid текущего пользователя
     * @return в случае успеха возвращает uuid сообщения
     */
    @PutMapping(path = "/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String editMessage(@RequestBody Message message, @RequestHeader("user-uuid") UUID uuid) {
        return this.service.editMessage(message, uuid);
    }
}
