package com.cloudeagle.dropbox_demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthCredentials {
  private String accessToken;
  private String refreshToken;
  private long accessTokenExpiryTime;

  public boolean isExpired() {
    long now = System.currentTimeMillis();
    return now < accessTokenExpiryTime;
  }
}
