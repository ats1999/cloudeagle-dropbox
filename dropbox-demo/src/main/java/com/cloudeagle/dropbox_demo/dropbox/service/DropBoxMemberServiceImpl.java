package com.cloudeagle.dropbox_demo.dropbox.service;

import com.cloudeagle.dropbox_demo.tokenmanager.TokenManager;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.oauth.DbxCredential;
import com.dropbox.core.v2.DbxTeamClientV2;
import com.dropbox.core.v2.team.MembersListResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DropBoxMemberServiceImpl implements DropBoxMemberService{
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

  public MembersListResult fetchMemberList(String tenantId, String cursor) throws Exception {
    String accessToken = tokenManager.getAccessToken(PROVIDER, tenantId);
    String refreshToken = tokenManager.getRefreshToken(PROVIDER, tenantId);

    DbxCredential credential =
        new DbxCredential(accessToken, -1L, refreshToken, clientId, clientSecret);
    DbxTeamClientV2 teamClient = new DbxTeamClientV2(dbxRequestConfig, credential);

    return fetchMembers(teamClient, cursor);
  }

  private MembersListResult fetchMembers(DbxTeamClientV2 teamClient, String cursor)
      throws DbxException {
    if (cursor == null) {
      return teamClient.team().membersList();
    }

    return teamClient.team().membersListContinue(cursor);
  }
}
