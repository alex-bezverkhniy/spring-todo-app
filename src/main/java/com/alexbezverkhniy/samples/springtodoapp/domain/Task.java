package com.alexbezverkhniy.samples.springtodoapp.domain;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Alex Bezverkhniy on 2/21/18.
 */
@Entity
@Table
public class Task extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private Boolean isComplete;

    @ManyToOne
    private TodoList todoList;

    public Task() {
    }

    public Task(String title, String description, Boolean isComplete) {
        this.title = title;
        this.description = description;
        this.isComplete = isComplete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getComplete() {
        return isComplete;
    }

    public void setComplete(Boolean complete) {
        isComplete = complete;
    }

    public TodoList getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }
}
