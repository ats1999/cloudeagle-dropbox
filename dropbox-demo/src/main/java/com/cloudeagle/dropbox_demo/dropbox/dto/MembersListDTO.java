package com.cloudeagle.dropbox_demo.dropbox.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MembersListDTO {
    private List<MemberDTO> members;
    private String cursor;
    private boolean hasMore;
}
