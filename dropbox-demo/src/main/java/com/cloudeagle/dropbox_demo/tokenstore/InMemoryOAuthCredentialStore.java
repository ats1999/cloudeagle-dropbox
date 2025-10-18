package com.cloudeagle.dropbox_demo.tokenstore;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.cloudeagle.dropbox_demo.model.OAuthCredentials;
import org.springframework.stereotype.Component;

@Component
public class InMemoryOAuthCredentialStore implements OAuthCredentialStore {
  private final ConcurrentHashMap<String, ConcurrentHashMap<String, OAuthCredentials>> credentialStore;

  InMemoryOAuthCredentialStore() {
    this.credentialStore = new ConcurrentHashMap<>();
  }

  @Override
  public void saveToken(String provider, String tenantId, OAuthCredentials OAuthCredentials) {
    credentialStore.computeIfAbsent(provider, k -> new ConcurrentHashMap<>()).put(tenantId, OAuthCredentials);
  }

  @Override
  public Optional<OAuthCredentials> getToken(String provider, String tenantId) {
    var providerStore = credentialStore.get(provider);
    if (providerStore == null) {
      return Optional.empty();
    }

    var token = providerStore.get(tenantId);
    return Optional.ofNullable(token);
  }
}
