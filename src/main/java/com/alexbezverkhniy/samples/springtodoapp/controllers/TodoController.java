package com.alexbezverkhniy.samples.springtodoapp.controllers;

import com.alexbezverkhniy.samples.springtodoapp.domain.Task;
import com.alexbezverkhniy.samples.springtodoapp.domain.TodoList;
import com.alexbezverkhniy.samples.springtodoapp.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Alex Bezverkhniy on 2/22/18.
 */
@RestController
@RequestMapping("/api")
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping("/todos/")
    public Page<TodoList> findAllTodoListsPagination(@RequestParam(value = "page", defaultValue = "0") Integer pages, @RequestParam(value = "size", defaultValue = "5")Integer size) {
        return service.findAllTodoLists(new PageRequest(pages, size));
    }

    @GetMapping("/todos/{todoListId}")
    public TodoList findTodoListById(@PathVariable("todoListId") Long todoListId) {
        return service.findTodoList(todoListId);
    }

    @PostMapping("/todos/")
    public TodoList createTodo(@RequestBody TodoList todoList) {
        return service.saveTodoList(todoList);
    }

    @PutMapping("/todos/{todoListId}")
    public TodoList updateTodo(@PathVariable("todoListId") Long todoListId, @RequestBody TodoList todoList) {
        service.findTodoList(todoListId);
        todoList.setId(todoListId);
        return service.saveTodoList(todoList, true);
    }

    @GetMapping("/tasks/")
    public Page<TodoList> findAllTasksPagination(@RequestParam(value = "page", defaultValue = "0") Integer pages, @RequestParam(value = "size", defaultValue = "5")Integer size) {
        return service.findAllTodoLists(new PageRequest(pages, size));
    }

    @GetMapping("/tasks/{taskId}")
    public Task findTaskById(@PathVariable("taskId") Long taskId) {
        return service.findTask(taskId);
    }

    @PostMapping("/tasks/")
    public Task createTask(@RequestBody Task task) {
        return service.saveTask(task);
    }

    @PutMapping("/tasks/{taskId}")
    public Task updateTask(@PathVariable("taskId") Long taskId, @RequestBody Task task) {
        service.findTask(taskId);
        task.setId(taskId);
        return service.saveTask(task, true);
    }
}
