package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrelloAttachmentByTypeDto {

    @JsonProperty("trello")
    private TrelloTrelloDto trelloTrelloDto;
}
