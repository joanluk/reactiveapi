package org.emaginalabs.reactiveapi.exception;

/**
 * User: jose
 * Date: 06/10/2018
 * Time: 18:52
 */
public class ChatNotFoundException extends RuntimeException {

    public ChatNotFoundException(String chatId) {
        super("Chat not found with id " + chatId);
    }
}