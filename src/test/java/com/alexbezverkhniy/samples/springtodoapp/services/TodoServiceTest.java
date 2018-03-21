package com.alexbezverkhniy.samples.springtodoapp.services;

import com.alexbezverkhniy.samples.springtodoapp.domain.Task;
import com.alexbezverkhniy.samples.springtodoapp.domain.TodoList;
import com.alexbezverkhniy.samples.springtodoapp.repositories.TaskRepository;
import com.alexbezverkhniy.samples.springtodoapp.repositories.TodoListRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by Alex Bezverkhniy on 2/22/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {
    @InjectMocks
    private TodoService service;

    @Mock
    private TodoListRepository todoListRepository;

    @Mock
    private TaskRepository taskRepository;
    
    @Test
    public void saveTaskShouldSaveInDBTest() {
        final Task task = new Task();

        when(taskRepository.save(task)).then(invocation -> {
            task.setId(1l);
            return task;
        });

        Task actual = service.saveTask(task);

        verify(taskRepository, times(1)).save(task);
        assertNotNull(actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getCreatedBy());
        assertNotNull(actual.getUpdatedAt());
        assertNotNull(actual.getUpdatedBy());
        assertFalse(actual.getComplete());
    }

    @Test
    public void saveTaskShouldSUpdateDateTest() {
        final Task task = new Task();
        Date oldDate = new Date();
        task.setUpdatedAt(oldDate);
        when(taskRepository.save(task)).then(invocation -> {
            task.setId(1l);
            return task;
        });
        try {
            Thread.sleep(5L); // Emulation of runtime
        } catch (InterruptedException e) {
        }
        Task actual = service.saveTask(task);

        verify(taskRepository, times(1)).save(task);
        assertNotNull(actual.getUpdatedAt());
        assertNotEquals(oldDate, actual.getUpdatedAt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveTaskShouldThrowIllegalArgumentExceptionTest() {
        service.saveTask(null);
    }

    @Test
    public void saveTodoShouldSaveInDBTest() {
        final TodoList todoList = new TodoList();

        when(todoListRepository.save(todoList)).then(invocation -> {
            todoList.setId(1l);
            return todoList;
        });

        TodoList actual = service.saveTodoList(todoList);

        verify(todoListRepository, times(1)).save(todoList);
        assertNotNull(actual.getId());
        assertNotNull(actual.getCreatedAt());
        assertNotNull(actual.getCreatedBy());
        assertNotNull(actual.getUpdatedAt());
        assertNotNull(actual.getUpdatedBy());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteTaskShouldThrowIllegalArgumentException1Test() {
        TodoList todoList = null;
        service.deleteTodoList(todoList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteTaskShouldThrowIllegalArgumentException2Test() {
        final TodoList todoList = new TodoList();
        service.deleteTodoList(todoList);
    }

    @Test
    public void deleteTodoShouldRemoveFromDBTest() {
        final TodoList todoList = new TodoList();
        todoList.setId(1L);

        when(todoListRepository.findOne(anyLong())).thenReturn(todoList);

        service.deleteTodoList(todoList);

        verify(todoListRepository, times(1)).delete(todoList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveTodoShouldThrowIllegalArgumentExceptionTest() {
        service.saveTodoList(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTaskToTodoList2ShouldThrowIllegalArgumentException1Test() {
        service.addTaskToTodoList(1L, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTaskToTodoList2ShouldThrowIllegalArgumentException2Test() {
        when(todoListRepository.findOne(anyLong())).thenReturn(new TodoList());
        service.addTaskToTodoList(null, 1L);
    }

    @Test(expected = TodoListNotFoundException.class)
    public void addTaskToTodoList2ShouldThrowTodoListNotFoundExceptionTest() {
        when(taskRepository.findOne(2L)).thenReturn(new Task());
        service.addTaskToTodoList(2L, 1L);
    }

    @Test(expected = TaskNotFoundException.class)
    public void addTaskToTodoList2ShouldThrowTaskNotFoundExceptionTest() {
        when(todoListRepository.findOne(anyLong())).thenReturn(new TodoList());
        when(taskRepository.findOne(1L)).thenReturn(null);
        service.addTaskToTodoList(1L, 1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTaskToTodoListShouldThrowIllegalArgumentException1Test() {
        service.addTaskToTodoList(null, new TodoList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTaskToTodoListShouldThrowIllegalArgumentException2Test() {
        service.addTaskToTodoList(new Task(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTaskToTodoListShouldThrowIllegalArgumentException3Test() {
        TodoList todoList = new TodoList();
        todoList.setId(1L);
        service.addTaskToTodoList(new Task(), todoList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addTaskToTodoListShouldThrowIllegalArgumentException4Test() {
        Task task = new Task();
        task.setId(1L);
        service.addTaskToTodoList(task, new TodoList());
    }

    @Test
    public void addTaskToTodoListShouldAddAndSaveTest() {
        TodoList todoList = new TodoList();
        todoList.setId(1L);
        Task task = new Task();
        task.setId(1L);

        service.addTaskToTodoList(task, todoList);

        assertTrue(todoList.getTasks().contains(task));
        verify(todoListRepository, times(1)).save(todoList);
    }


    @Test(expected = IllegalArgumentException.class)
    public void completeTaskShouldThrowIllegalArgumentException1Test() {
        Long id = null;
        service.togleTaskComplete(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void completeTaskShouldThrowIllegalArgumentException2Test() {
        Task task = null;
        service.togleTaskComplete(task);
    }

    @Test(expected = IllegalArgumentException.class)
    public void completeTaskShouldThrowIllegalArgumentException3Test() {
        Task task = new Task();
        service.togleTaskComplete(task);
    }

    @Test
    public void completeTaskShouldCompleteTaskTest() {
        Task task = new Task();
        task.setComplete(false);
        when(taskRepository.findOne(1L)).thenReturn(task);

        boolean actual = service.togleTaskComplete(1L);

        assertTrue(actual);
        verify(taskRepository, times(1)).save(task);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findTaskByTitleShouldThrowIllegalArgumentExceptionTest() {
        service.findTaskByTitle(null, 0, 0);
    }

    @Test(expected = TaskNotFoundException.class)
    public void findTaskByTitleShouldThrowTaskNotFoundExceptionTest() {
        final String title = "test";
        final int page = 0;
        final int size = 5;
        Task task = new Task();
        Page<Task> pageResponse = new PageImpl<Task>(Arrays.asList(task));

        when(taskRepository.findByTitle(anyString(), any(PageRequest.class))).thenReturn(null);

        Page<Task> actual = service.findTaskByTitle(title, page, size);


        verify(taskRepository, times(1)).findByTitle(anyString(), any(PageRequest.class));
    }

    @Test
    public void findTaskByTitleShouldReturnListOfTasksTest() {
        final String title = "test";
        final int page = 0;
        final int size = 5;
        Task task = new Task();
        Page<Task> pageResponse = new PageImpl<Task>(Arrays.asList(task));

        when(taskRepository.findByTitle(anyString(), any(PageRequest.class))).thenReturn(pageResponse);

        Page<Task> actual = service.findTaskByTitle(title, page, size);

        assertNotNull(actual);

        verify(taskRepository, times(1)).findByTitle(anyString(), any(PageRequest.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findTodoListByTitleShouldThrowIllegalArgumentExceptionTest() {
        service.findTodoListByTitle(null, 0, 0);
    }

    @Test(expected = TodoListNotFoundException.class)
    public void findTodoListByTitleShouldThrowTodoListNotFoundExceptionTest() {
        final String title = "test";
        final int page = 0;
        final int size = 5;
        TodoList todoList = new TodoList();
        Page<TodoList> pageResponse = new PageImpl<TodoList>(Arrays.asList(todoList));

        when(todoListRepository.findByTitle(anyString(), any(PageRequest.class))).thenReturn(null);

        Page<TodoList> actual = service.findTodoListByTitle(title, page, size);


        verify(todoListRepository, times(1)).findByTitle(anyString(), any(PageRequest.class));
    }

    @Test
    public void findTodoListByTitleShouldReturnListOfTodoListsTest() {
        final String title = "test";
        final int page = 0;
        final int size = 5;
        TodoList todoList = new TodoList();
        Page<TodoList> pageResponse = new PageImpl<TodoList>(Arrays.asList(todoList));

        when(todoListRepository.findByTitle(anyString(), any(PageRequest.class))).thenReturn(pageResponse);

        Page<TodoList> actual = service.findTodoListByTitle(title, page, size);

        assertNotNull(actual);

        verify(todoListRepository, times(1)).findByTitle(anyString(), any(PageRequest.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAllTodoListsShouldThrowIllegalArgumentExceptionTest() {
        service.findAllTodoLists(null);
    }

    @Test(expected = TodoListNotFoundException.class)
    public void findAllTodoListsShouldThrowTodoListNotFoundExceptionTest() {
        final PageRequest pageRequest = new PageRequest(0,5);

        when(todoListRepository.findAll(pageRequest)).thenReturn(null);

        service.findAllTodoLists(pageRequest);

        verify(todoListRepository, times(1)).findAll(pageRequest);
    }

    @Test
    public void findAllTodoListsTest() {
        final PageRequest pageRequest = new PageRequest(0,5);

        when(todoListRepository.findAll(pageRequest)).thenReturn(new PageImpl<TodoList>(Arrays.asList(new TodoList())));

        Page<TodoList> result = service.findAllTodoLists(pageRequest);

        assertNotNull(result);
        assertTrue(result.hasContent());
        assertTrue(result.getContent().size() == 1);

    }

    @Test(expected = IllegalArgumentException.class)
    public void findAllTasksShouldThrowIllegalArgumentExceptionTest() {
        service.findAllTasks(null);
    }

    @Test(expected = TaskNotFoundException.class)
    public void findAllTasksShouldThrowTaskNotFoundExceptionTest() {
        final PageRequest pageRequest = new PageRequest(0,5);

        when(taskRepository.findAll(pageRequest)).thenReturn(null);

        service.findAllTasks(pageRequest);

        verify(taskRepository, times(1)).findAll(pageRequest);
    }

    @Test
    public void findAllTasksTest() {
        final PageRequest pageRequest = new PageRequest(0,5);

        when(taskRepository.findAll(pageRequest)).thenReturn(new PageImpl<Task>(Arrays.asList(new Task())));

        Page<Task> result = service.findAllTasks(pageRequest);

        assertNotNull(result);
        assertTrue(result.hasContent());
        assertTrue(result.getContent().size() == 1);

    }
}
