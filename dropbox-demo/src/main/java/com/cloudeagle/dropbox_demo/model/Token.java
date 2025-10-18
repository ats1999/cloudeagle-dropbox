package com.cloudeagle.dropbox_demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Token {
  private String accessToken;
  private String refreshToken;
  private long accessTokenExpiryTime;
}
