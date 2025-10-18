package com.cloudeagle.dropbox_demo.tokenstore;

import com.cloudeagle.dropbox_demo.model.OAuthCredentials;

import java.util.Optional;

public interface OAuthCredentialStore {
  void saveToken(String provider, String tenantId, OAuthCredentials OAuthCredentials);

  Optional<OAuthCredentials> getToken(String provider, String tenantId);
}
