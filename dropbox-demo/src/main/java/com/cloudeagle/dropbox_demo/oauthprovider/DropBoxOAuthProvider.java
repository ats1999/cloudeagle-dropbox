package com.cloudeagle.dropbox_demo.oauthprovider;

import com.cloudeagle.dropbox_demo.model.OAuthCredentials;
import com.dropbox.core.*;
import com.dropbox.core.oauth.DbxCredential;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("dropbox")
public class DropBoxOAuthProvider implements OAuthProvider {
  private final String clientId;
  private final String clientSecret;
  private final String redirectUrl;

  DropBoxOAuthProvider(
      @Value("${oauth.dropbox.client-id}") String clientId,
      @Value("${oauth.dropbox.client-secret}") String clientSecret,
      @Value("${oauth.dropbox.redirect-url}") String redirectUrl) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.redirectUrl = redirectUrl;
  }

  @Override
  public OAuthCredentials exchangeCodeForToken(String code) throws DbxException {
    DbxRequestConfig config = DbxRequestConfig.newBuilder("cloudEagleApp").build();
    DbxAppInfo appInfo = new DbxAppInfo(clientId, clientSecret);
    DbxWebAuth webAuth = new DbxWebAuth(config, appInfo);

    DbxAuthFinish authFinish = webAuth.finishFromCode(code, redirectUrl);

    String accessToken = authFinish.getAccessToken();
    String refreshToken = authFinish.getRefreshToken();
    long expiryTimestamp =
        Duration.ofMillis(System.currentTimeMillis())
            .plusSeconds(authFinish.getExpiresAt())
            .toMillis();

    return OAuthCredentials.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .accessTokenExpiryTime(expiryTimestamp)
        .build();
  }

  @Override
  public OAuthCredentials refreshOauthCredentials(String oldAccessToken, String refreshToken)
      throws DbxException {

    DbxCredential dbxCredential =
        new DbxCredential(
            oldAccessToken,
            -1L, // expiration time (optional)
            refreshToken,
            clientId,
            clientSecret);

    DbxRequestConfig dbxRequestConfig = DbxRequestConfig.newBuilder("cloudEagleApp").build();

    dbxCredential.refresh(dbxRequestConfig);

    long expiryTimestamp =
        Duration.ofMillis(System.currentTimeMillis())
            .plusSeconds(dbxCredential.getExpiresAt())
            .toMillis();

    return OAuthCredentials.builder()
        .accessToken(dbxCredential.getAccessToken())
        .refreshToken(refreshToken)
        .accessTokenExpiryTime(expiryTimestamp)
        .build();
  }
}
