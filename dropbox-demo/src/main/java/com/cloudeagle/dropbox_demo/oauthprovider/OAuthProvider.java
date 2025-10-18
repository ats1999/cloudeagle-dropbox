package com.cloudeagle.dropbox_demo.oauthprovider;

import com.cloudeagle.dropbox_demo.model.OAuthCredentials;
import com.dropbox.core.DbxException;

public interface OAuthProvider {
  OAuthCredentials exchangeCodeForToken(String code) throws DbxException;

  OAuthCredentials refreshOauthCredentials(String oldAccessToken, String refreshToken)
      throws DbxException;
}
