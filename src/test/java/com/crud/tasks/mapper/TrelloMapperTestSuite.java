package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTestSuite {

    @InjectMocks
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoardAndDto() {
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1", "Card1", false));
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "Card1", false));
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(new TrelloBoard("1", "Board1", trelloList));
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        trelloBoardDtoList.add(new TrelloBoardDto("1", "Board1", trelloListDto));

        List<TrelloBoard> mappedTrelloBoardList = trelloMapper.mapToBoards(trelloBoardDtoList);
        List<TrelloBoardDto> mappedTrelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoardList);

        assertEquals(trelloBoardList.get(0).getName(), mappedTrelloBoardList.get(0).getName());
        assertEquals(trelloBoardDtoList.get(0).getName(), mappedTrelloBoardDtoList.get(0).getName());
    }

    @Test
    public void testMapToListAndDto() {
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1", "Card1", true));
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "Card1", true));

        List<TrelloList> mappedList = trelloMapper.mapToList(trelloListDto);
        List<TrelloListDto> mappedDtoList = trelloMapper.mapToListDto(trelloList);

        assertEquals(trelloList.get(0).getName(), mappedList.get(0).getName());
        assertEquals(trelloListDto.get(0).getName(), mappedDtoList.get(0).getName());
    }

    @Test
    public void testMapToCardAndDto() {
        TrelloCard trelloCard = new TrelloCard("Card1", "Card_description", "1", "1");
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card1", "Card_description", "1", "1");

        TrelloCard mappedCard = trelloMapper.mapToCard(trelloCardDto);
        TrelloCardDto mappedDtoCard = trelloMapper.mapToCardDto(trelloCard);

        assertEquals(trelloCard.getName(), mappedCard.getName());
        assertEquals(trelloCardDto.getName(), mappedDtoCard.getName());
    }
}
