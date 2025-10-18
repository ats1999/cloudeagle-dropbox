package com.cloudeagle.dropbox_demo.dropbox;

import com.cloudeagle.dropbox_demo.dropbox.dto.MembersListDTO;
import com.cloudeagle.dropbox_demo.dropbox.service.DropBoxMemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dropbox/members")
public class MemberController {
  private final DropBoxMemberService dropBoxMemberService;

  MemberController(DropBoxMemberService dropBoxMemberService) {
    this.dropBoxMemberService = dropBoxMemberService;
  }

  @GetMapping("/list")
  public MembersListDTO listMembers(@RequestParam(required = false) String cursor)
      throws Exception {
    String tenantId = "rahul";
    return dropBoxMemberService.fetchMemberList(tenantId, cursor);
  }
}
