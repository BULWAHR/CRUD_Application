package com.crud.tasks.trello.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TrelloConfig {

//    @Value("${trello.api.endpoint.prod}")
//    private String trelloApiEndpoint;
//
//    @Value("${trello.app.key}")
//    private String trelloAppKey;
//
//    @Value("${trello.app.token}")
//    private String trelloToken;
//
//    @Value("${trello.datasource.username}")
//    private String trelloUsername;

    @Value("https://api.trello.com/1")
    private String trelloApiEndpoint;

    @Value("aeb2bffdd0b65d368f3adfc2bef08987")
    private String trelloAppKey;

    @Value("0af479f0b6dd42d6f109b6e2ed825b439d174ae1295477e5e5d342f448e0de6e")
    private String trelloToken;

    @Value("bulwahr")
    private String trelloUsername;

}
