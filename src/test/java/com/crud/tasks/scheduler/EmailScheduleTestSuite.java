package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailScheduleTestSuite {


    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private TaskRepository taskRepository;


    @Test
    public void testSendEmail() {
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");
        when(taskRepository.count()).thenReturn(1L);

        emailScheduler.sendInformationEmail();

        verify(simpleEmailService, times(1)).send(any(Mail.class));
    }
}
