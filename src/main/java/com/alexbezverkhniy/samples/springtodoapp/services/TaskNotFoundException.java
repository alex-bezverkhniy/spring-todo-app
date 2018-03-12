package com.alexbezverkhniy.samples.springtodoapp.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Alex Bezverkhniy on 2/22/18.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
        super("Task is not found!");
    }

    public TaskNotFoundException(Long todoListId) {
        super(String.format("Task with id: %d is not found!", todoListId));
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
