package com.core.controllers;

import com.core.services.MessageService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController

public class MessageCoreController {

    private final MessageService messageService;

    public MessageCoreController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages/student/{student}")
    public Mono<String> getMessagesForStudent(@PathVariable int student) {
        return messageService.getMessagesForStudent(student);
    }

    @PostMapping("/messages")
    public Mono<String> createMessage(@RequestBody Object message) {
        return messageService.createMessage(message);
    }

    @GetMapping("/messages/{id}")
    public Mono<String> getMessageById(@PathVariable String id) {
        return messageService.getMessageById(id);
    }

    @PutMapping("/messages/{id}")
    public Mono<String> updateMessage(@PathVariable String id, @RequestBody Object message) {
        return messageService.updateMessage(id, message);
    }

    @DeleteMapping("/messages/{id}")
    public Mono<String> deleteMessage(@PathVariable String id) {
        return messageService.deleteMessage(id);
    }
}
