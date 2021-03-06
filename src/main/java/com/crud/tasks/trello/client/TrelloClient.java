package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    @Autowired
    private TrelloConfig trelloConfig;

    @Autowired
    private RestTemplate restTemplate;



    public List<TrelloBoardDto> getTrelloBoards() {

        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(urlCreate(), TrelloBoardDto[].class);
            return Arrays.asList(Optional.ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public CreatedTrelloCardDto createdNewCard(TrelloCardDto trelloCardDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/cards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloToken())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId()).build().encode().toUri();

        return restTemplate.postForObject(url, null, CreatedTrelloCardDto.class);
    }

    private URI urlCreate() {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/" + trelloConfig.getTrelloUsername() + "/boards")
//        URI url = UriComponentsBuilder.fromHttpUrl("https://api.trello.com/1" + "/members/" + "bulwahr" + "/boards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
//                .queryParam("key", "aeb2bffdd0b65d368f3adfc2bef08987")
                .queryParam("token", trelloConfig.getTrelloToken())
//                .queryParam("token", "0af479f0b6dd42d6f109b6e2ed825b439d174ae1295477e5e5d342f448e0de6e")
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();
        return url;
    }
}





//        if (boardsResponse != null) {
//            return Arrays.asList(boardsResponse);
//        }
//        return new ArrayList<>();


//        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);
//        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(
//                trelloApiEndpoint + "/members/bulwahr/boards" + "?key=" + trelloAppKey + "&token" + trelloToken,
//                TrelloBoardDto[].class);









