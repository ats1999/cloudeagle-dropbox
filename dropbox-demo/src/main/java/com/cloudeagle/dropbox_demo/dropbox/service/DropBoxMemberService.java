package com.cloudeagle.dropbox_demo.dropbox.service;

import com.dropbox.core.v2.team.MembersListResult;

public interface DropBoxMemberService {
    MembersListResult fetchMemberList(String tenantId, String cursor) throws Exception;
}
