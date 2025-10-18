package com.cloudeagle.dropbox_demo.dropbox;

import com.cloudeagle.dropbox_demo.dropbox.service.DropBoxMemberService;
import com.dropbox.core.v2.team.MembersListResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dropbox/members")
public class MemberApiController {
  private final DropBoxMemberService dropBoxMemberService;

  MemberApiController(DropBoxMemberService dropBoxMemberService) {
    this.dropBoxMemberService = dropBoxMemberService;
  }

  @GetMapping("/list")
  public MembersListResult listMembers(@RequestParam(required = false) String cursor)
      throws Exception {
    String tenantId = "rahul";
    return dropBoxMemberService.fetchMemberList(tenantId, cursor);
  }
}
