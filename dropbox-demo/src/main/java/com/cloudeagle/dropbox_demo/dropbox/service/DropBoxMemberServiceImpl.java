package com.cloudeagle.dropbox_demo.dropbox.service;

import com.cloudeagle.dropbox_demo.dropbox.dto.MemberDTO;
import com.cloudeagle.dropbox_demo.dropbox.dto.MembersListDTO;
import com.cloudeagle.dropbox_demo.tokenmanager.TokenManager;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.oauth.DbxCredential;
import com.dropbox.core.v2.DbxTeamClientV2;
import com.dropbox.core.v2.team.MembersListResult;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DropBoxMemberServiceImpl implements DropBoxMemberService {
  private static final String PROVIDER = "dropbox";
  private final TokenManager tokenManager;
  private final DbxRequestConfig dbxRequestConfig;
  private final String clientId;
  private final String clientSecret;

  DropBoxMemberServiceImpl(
      TokenManager tokenManager,
      DbxRequestConfig dbxRequestConfig,
      @Value("${oauth.dropbox.client-id}") String clientId,
      @Value("${oauth.dropbox.client-secret}") String clientSecret) {
    this.tokenManager = tokenManager;
    this.dbxRequestConfig = dbxRequestConfig;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  public MembersListDTO fetchMemberList(String tenantId, String cursor) throws Exception {
    String accessToken = tokenManager.getAccessToken(PROVIDER, tenantId);
    String refreshToken = tokenManager.getRefreshToken(PROVIDER, tenantId);

    DbxCredential credential =
        new DbxCredential(accessToken, -1L, refreshToken, clientId, clientSecret);
    DbxTeamClientV2 teamClient = new DbxTeamClientV2(dbxRequestConfig, credential);

    return fetchMembers(teamClient, cursor);
  }

  private MembersListDTO fetchMembers(DbxTeamClientV2 teamClient, String cursor)
      throws DbxException {
    MembersListResult membersListResult;
    if (cursor == null) {
      membersListResult = teamClient.team().membersList();
    } else {
      membersListResult = teamClient.team().membersListContinue(cursor);
    }

    return dropboxMemberListToMemberListDTO(membersListResult);
  }

  private MembersListDTO dropboxMemberListToMemberListDTO(MembersListResult membersListResult) {
    List<MemberDTO> members =
        membersListResult.getMembers().stream()
            .map(
                m -> {
                  var profile = m.getProfile();
                  return MemberDTO.builder()
                      .accountId(profile.getAccountId())
                      .email(profile.getEmail())
                      .status(profile.getStatus().toString())
                      .displayName(profile.getName().getDisplayName())
                      .build();
                })
            .toList();
    return MembersListDTO.builder()
        .members(members)
        .cursor(membersListResult.getCursor())
        .hasMore(membersListResult.getHasMore())
        .build();
  }
}
