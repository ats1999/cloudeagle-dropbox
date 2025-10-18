package com.cloudeagle.dropbox_demo.dropbox.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDTO {
    private String accountId;
    private String email;
    private String status; 
    private String displayName;
}
