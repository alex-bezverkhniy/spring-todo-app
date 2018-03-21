package com.alexbezverkhniy.samples.springtodoapp.controllers;

import com.alexbezverkhniy.samples.springtodoapp.domain.Task;
import com.alexbezverkhniy.samples.springtodoapp.services.TodoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by Alex Bezverkhniy on 3/21/18.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Test
    public void souldReturnOneTaskById() throws Exception {
        String expectedJson = "{\"createdAt\":null,\"updatedAt\":null,\"createdBy\":null,\"updatedBy\":null,\"id\":null,\"title\":\"Simple task\",\"description\":\"Just simple task\",\"todoList\":null,\"complete\":false}";

        when(todoService.findTask(1L)).thenReturn(new Task("Simple task", "Just simple task", false));

        mockMvc
                .perform(get("/api/tasks/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}
