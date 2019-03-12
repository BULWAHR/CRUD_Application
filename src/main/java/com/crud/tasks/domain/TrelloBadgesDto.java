package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrelloBadgesDto {

    private int votes;

    @JsonProperty("attachmentByType")
    private TrelloAttachmentByTypeDto trelloAttachmentByTypeDto;
}
