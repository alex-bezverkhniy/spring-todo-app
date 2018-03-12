package com.alexbezverkhniy.samples.springtodoapp.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Alex Bezverkhniy on 2/22/18.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TodoListNotFoundException extends RuntimeException {
    public TodoListNotFoundException() {
        super("Todo list is not found!");
    }

    public TodoListNotFoundException(Long todoListId) {
        super(String.format("Todo list with id: %d is not found!", todoListId));
    }

    public TodoListNotFoundException(String message) {
        super(message);
    }
}
