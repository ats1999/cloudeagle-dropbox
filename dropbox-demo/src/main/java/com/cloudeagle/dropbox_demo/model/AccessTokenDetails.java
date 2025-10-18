package com.cloudeagle.dropbox_demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessTokenDetails {
  private String accessToken;
  private long accessTokenExpiryTime;
}
