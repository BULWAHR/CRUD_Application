package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository repository;

    @Test
    public void testFetchTaskWithId() {

        Task task_1 = new Task(1L, "test", "test");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task_1);

        when(repository.findById(1L)).thenReturn(Optional.of(taskList.get(0)));

        Optional<Task> task = dbService.getTask(1L);

        assertNotNull(task);
        assertEquals("test", task.get().getTitle());
    }

    @Test
    public void testSaveTask() {

        Task task = new Task(1L, "test", "test");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        when(repository.save(task)).thenReturn(taskList.get(0));

        Task savedTask = dbService.saveTask(task);

        assertNotNull(savedTask);
        assertEquals("test", savedTask.getTitle());
    }

    @Test
    public void testDeleteTask() {

        dbService.deleteTask(1L);

        verify(repository, times(1)).delete(1L);
    }

    @Test
    public void testFetchEmptyTaskList() {

        when(repository.findAll()).thenReturn(new ArrayList<>());

        List<Task> taskList = dbService.getAllTasks();

        assertNotNull(taskList);
        assertEquals(0, taskList.size());
    }

}