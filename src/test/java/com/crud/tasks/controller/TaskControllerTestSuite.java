package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;


    @Test
    public void testFetchEmptyTasks() throws Exception {

        List<TaskDto> tasksDto = new ArrayList<>();

        when(taskMapper.mapToTaskDtoList(any())).thenReturn(tasksDto);


        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void testFetchTasks() throws Exception {
        List<TaskDto> tasksDto = new ArrayList<>();
        tasksDto.add(new TaskDto((long) 1, "task1", "content1"));
        tasksDto.add(new TaskDto((long) 2, "task2", "content2"));

        when(taskMapper.mapToTaskDtoList(any())).thenReturn(tasksDto);


        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("task1")))
                .andExpect(jsonPath("$[0].content", is("content1")));
    }

    @Test
    public void testFetchTask() throws Exception {

        TaskDto taskDto = new TaskDto(1L, "test_get", "test");
        Task task = new Task(1L, "test_get", "test");

        when(service.getTask(task.getId())).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        mockMvc.perform(get("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

    }

    @Test
    public void shouldCreateFetchTask() throws Exception {

        TaskDto taskDto = new TaskDto((long) 1, "task", "content");

        Task task = new Task((long) 1, "task", "content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        Gson gson = new Gson();
        String createTask = gson.toJson(task);

        mockMvc.perform(post("/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(createTask))
                .andExpect(status().isOk());

        verify(service, times(1)).saveTask(any());
    }

    @Test
    public void testFetchUpdateTask() throws Exception {

        TaskDto taskDto = new TaskDto(1L, "test", "test");
        TaskDto updatedTaskDto = new TaskDto(1L, "test_update", "test");
        Task mappedTask = new Task(updatedTaskDto.getId(), updatedTaskDto.getTitle(), updatedTaskDto.getContent());

        when(taskMapper.mapToTask(Matchers.any(TaskDto.class))).thenReturn(mappedTask);
        when(service.saveTask(mappedTask)).thenReturn(mappedTask);
        when(taskMapper.mapToTaskDto(mappedTask)).thenReturn(updatedTaskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test task")))
                .andExpect(jsonPath("$.content", is("Test Content")));
    }

    @Test
    public void testFetchDeleteTask() throws Exception {

        Task task = new Task(1L,"test_delete", "test");

        service.deleteTask(1L);
        mockMvc.perform(delete("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON));
    }


}
