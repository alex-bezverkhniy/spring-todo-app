package com.alexbezverkhniy.samples.springtodoapp.services;

import com.alexbezverkhniy.samples.springtodoapp.domain.Task;
import com.alexbezverkhniy.samples.springtodoapp.domain.TodoList;
import com.alexbezverkhniy.samples.springtodoapp.repositories.TaskRepository;
import com.alexbezverkhniy.samples.springtodoapp.repositories.TodoListRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Date;

import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertSame;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by Alex Bezverkhniy on 2/21/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoServiceIntegTest {

    @Autowired
    private TodoService service;

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void saveItemShouldCreateNewItemInDBTest() {
        Task task = new Task("task #1", "simple task #1", false);
        Task actual = service.saveTask(task);
        assertNotNull(actual);
        assertNotNull(actual.getId());
    }


    @Test(expected = IllegalArgumentException.class)
    public void saveItemShouldThrowExceptionIfNullArgsTest() {
        Task actual = service.saveTask(null);
        assertNull(actual);
    }

    @Test
    @Transactional
    public void addItemToTodoListShouldCreateNewItemInDBTest() {
        TodoList todoList = todoListRepository.save(new TodoList());

        Task task = taskRepository.save(new Task("task #1", "simple task #1", false));
        service.addTaskToTodoList(task, todoList);

        TodoList actual = todoListRepository.findOne(todoList.getId());

        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertNotNull(actual.getTasks());
        assertTrue(actual.getTasks().contains(task));
    }

    @Test
    @Transactional
    public void addItemToTodoList2ShouldCreateNewItemInDBTest() {
        TodoList todoList = todoListRepository.save(new TodoList());

        Task task = taskRepository.save(new Task("task #1", "simple task #1", false));
        service.addTaskToTodoList(task.getId(), todoList.getId());

        TodoList actual = todoListRepository.findOne(todoList.getId());

        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertNotNull(actual.getTasks());
        assertTrue(actual.getTasks().contains(task));
    }

    @Test
    @Transactional
    public void updateShouldChangeDateTest() {
        TodoList todoList = service.saveTodoList(new TodoList());
        TodoList oldTodoList = new TodoList();
        BeanUtils.copyProperties(todoList, oldTodoList);

        Task task = service.saveTask(new Task("task #1", "simple task #1", false));
        Task oldTask = new Task();
        BeanUtils.copyProperties(task, oldTask);

        todoList.setTitle("new title");
        TodoList actualTodoList = service.saveTodoList(todoList, true);

        assertNotNull(actualTodoList);
        assertNotSame(oldTodoList.getUpdatedAt(), actualTodoList.getUpdatedAt());
        assertSame(oldTodoList.getUpdatedBy(), actualTodoList.getUpdatedBy());
        assertSame(oldTodoList.getCreatedAt(), actualTodoList.getCreatedAt());
        assertSame(oldTodoList.getCreatedBy(), actualTodoList.getCreatedBy());

        task.setTitle("new title");
        task.setComplete(true);
        Task actualTask = service.saveTask(task, true);

        assertNotNull(actualTask);
        assertNotSame(oldTask.getUpdatedAt(), actualTask.getUpdatedAt());
        assertNotSame(oldTask.getTitle(), actualTask.getTitle());
        assertNotSame(oldTask.getComplete(), actualTask.getComplete());
        assertSame(oldTask.getUpdatedBy(), actualTask.getUpdatedBy());
        assertSame(oldTask.getCreatedAt(), actualTask.getCreatedAt());
        assertSame(oldTask.getCreatedBy(), actualTask.getCreatedBy());
    }

}
