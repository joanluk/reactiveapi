package org.emaginalabs.reactiveapi.controller;

import com.mongodb.DuplicateKeyException;
import org.emaginalabs.reactiveapi.exception.ChatNotFoundException;
import org.emaginalabs.reactiveapi.model.Chat;
import org.emaginalabs.reactiveapi.model.ErrorResponse;
import org.emaginalabs.reactiveapi.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * API reactive chat
 */
@RestController
public class ChatController {

    private final  ChatRepository chatRepository;

    @Autowired
    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }
    @GetMapping("/chats")
    public Flux<Chat> getAllChats() {
        return chatRepository.findAll();
    }

    @PostMapping("/chats")
    public Mono<Chat> createChat(@Valid @RequestBody Chat chat) {
        return chatRepository.save(chat);
    }

    @GetMapping("/chats/{id}")
    public Mono<ResponseEntity<Chat>> getchatById(@PathVariable(value = "id") String chatId) {
        return chatRepository.findById(chatId)
                .map(savedChat -> ResponseEntity.ok(savedChat))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/chats/{id}")
    public Mono<ResponseEntity<Chat>> updateChat(@PathVariable(value = "id") String chatId,
                                                   @Valid @RequestBody Chat chat) {
        return chatRepository.findById(chatId)
                .flatMap(existingchat -> {
                    existingchat.setText(chat.getText());
                    return chatRepository.save(existingchat);
                })
                .map(updatechat -> new ResponseEntity<>(updatechat, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/chats/{id}")
    public Mono<ResponseEntity<Void>> deletechat(@PathVariable(value = "id") String chatId) {

        return chatRepository.findById(chatId)
                .flatMap(existingchat ->
                        chatRepository.delete(existingchat)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // chats are Sent to the client as Server Sent Events
    @GetMapping(value = "/stream/chats", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> streamAllchats() {
        return chatRepository.findAll();
    }




    /*
        Exception Handling Examples (These can be put into a @ControllerAdvice to handle exceptions globally)
    */

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity handleDuplicateKeyException(DuplicateKeyException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("A chat with the same text already exists"));
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity handlechatNotFoundException(ChatNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
