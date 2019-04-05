package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTestSuite {

    @InjectMocks
    private TaskMapper taskMapper;


    @Test
    public void testMapToTask() {
        TaskDto taskDto = new TaskDto(1L, "test", "test");

        Task task = taskMapper.mapToTask(taskDto);

        assertNotNull(task);
        assertEquals("test", task.getTitle());
    }

    @Test
    public void testMapToTaskDto() {

        Task task = new Task(1L,"test","test");

        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        assertNotNull(taskDto);
        assertEquals("test", taskDto.getContent());
    }

    @Test
    public void testMapToTaskDtoList() {

        Task task = new Task(1L,"test_title","test_content");

        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        List<TaskDto> taskDtoLists = taskMapper.mapToTaskDtoList(taskList);

        assertNotNull(taskDtoLists);
        assertEquals(1, taskDtoLists.size());

        taskDtoLists.forEach(taskDtoList -> {
            assertEquals(1L, taskDtoList.getId().longValue());
            assertEquals("test_title",taskDtoList.getTitle());
            assertEquals("test_content",taskDtoList.getContent());
        });
    }


}
