package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloServiceTestSuite {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Test
    public void testTrelloServiceFetchTrelloBoards() {

        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1","test",false));

        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto( "1","test",trelloListDto));

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);

        List<TrelloBoardDto> listBoard = trelloService.fetchTrelloBoards();

        assertEquals(1,listBoard.size());
        assertEquals(trelloBoardDtos,listBoard);
    }

    @Test
    public void testFetchEmptyList() {

        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "test", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "test", trelloLists));

        when(trelloClient.getTrelloBoards()).thenReturn(new ArrayList<>());


        List<TrelloBoardDto> trelloBoardDtos = trelloService.fetchTrelloBoards();

         assertNotNull(trelloBoardDtos);
        assertEquals(0, trelloBoardDtos.size());
    }

//    @Test
//    public void shouldCreateNewCardAndSendEmail() {
//
//        TrelloCardDto trelloCardDto = new TrelloCardDto(
//                "Test task",
//                "Test description",
//                "top",
//                "test_id"
//        );
//        TrelloTrelloDto trelloDto = new TrelloTrelloDto(3, 5);
//
//        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
//                "1",
//                "Test task",
//                "http://test.com"
//        );
//
//        when(trelloClient.createdNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
////        when(adminConfig.getAdminMail()).thenReturn("test@test.com");
//
//        CreatedTrelloCardDto newCard = trelloService.createTrelloCard(trelloCardDto);
//
//        assertNotNull(newCard);
//        assertEquals("Test task", newCard.getName());
//        verify(simpleEmailService, times(1)).send(any(Mail.class));
//    }
}