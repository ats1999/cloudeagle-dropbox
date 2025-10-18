package com.cloudeagle.dropbox_demo.oauthprovider;

import com.cloudeagle.dropbox_demo.model.Token;
import com.dropbox.core.*;
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
  public Token exchangeCodeForToken(String provider, String code) throws DbxException {
    DbxRequestConfig config = DbxRequestConfig.newBuilder("cloudEagleApp").build();
    DbxAppInfo appInfo = new DbxAppInfo(clientId, clientSecret);
    DbxWebAuth webAuth = new DbxWebAuth(config, appInfo);

    DbxAuthFinish authFinish = webAuth.finishFromCode(code, redirectUrl);

    String accessToken = authFinish.getAccessToken();
    String refreshToken = authFinish.getRefreshToken();
    long expiryTimestamp = System.currentTimeMillis() + (authFinish.getExpiresAt() * 1000);

    return Token.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .accessTokenExpiryTime(expiryTimestamp)
        .build();
  }
}
