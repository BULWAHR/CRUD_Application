package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
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
    public void testFetchTasksList() throws Exception {
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

        Task task = new Task(1L, "test_get", "test");
        TaskDto taskDto = new TaskDto(1L, "test_get", "test");

        when(service.getTask(task.getId())).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        mockMvc.perform(get("/v1/tasks/1").param("taskId", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test_get")))
                .andExpect(jsonPath("$.content", is("test")));

    }

    @Test
    public void testFetchCreateTask() throws Exception {

        Task task = new Task(1L, "task", "content");
        TaskDto taskDto = new TaskDto(1L, "task", "content");

        when(taskMapper.mapToTask(Matchers.any(TaskDto.class))).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);

        mockMvc.perform(post("/v1/tasks").contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testFetchUpdateTask() throws Exception {

        TaskDto taskDto = new TaskDto(1L, "task", "test");
        TaskDto updatedTaskDto = new TaskDto(1L, "taskUpdated", "testUpdated");
        Task mappedTask = new Task(updatedTaskDto.getId(), updatedTaskDto.getTitle(), updatedTaskDto.getContent());

        when(taskMapper.mapToTask(Matchers.any(TaskDto.class))).thenReturn(mappedTask);
        when(service.saveTask(mappedTask)).thenReturn(mappedTask);
        when(taskMapper.mapToTaskDto(mappedTask)).thenReturn(updatedTaskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        mockMvc.perform(put("/v1/tasks").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("taskUpdated")))
                .andExpect(jsonPath("$.content", is("testUpdated")));

    }

    @Test
    public void testFetchDeleteTask() throws Exception {

        Task task = new Task(2L,"test_delete", "test");

        service.deleteTask(2L);
        mockMvc.perform(delete("/v1/tasks/2").contentType(MediaType.APPLICATION_JSON));

        verify(service, times(1)).deleteTask(any());
    }

}
