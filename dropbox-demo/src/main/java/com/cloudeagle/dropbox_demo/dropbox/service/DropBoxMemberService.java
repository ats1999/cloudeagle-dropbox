package com.cloudeagle.dropbox_demo.dropbox.service;

import com.cloudeagle.dropbox_demo.dropbox.dto.MembersListDTO;

public interface DropBoxMemberService {
    MembersListDTO fetchMemberList(String tenantId, String cursor) throws Exception;
}
