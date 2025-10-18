package com.cloudeagle.dropbox_demo.tokenstore;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.cloudeagle.dropbox_demo.model.OAuthCredentials;
import org.springframework.stereotype.Component;

@Component
public class InMemoryOAuthCredentialStore implements OAuthCredentialStore {
  private final ConcurrentHashMap<String, ConcurrentHashMap<String, OAuthCredentials>> tokenStore;

  InMemoryOAuthCredentialStore() {
    this.tokenStore = new ConcurrentHashMap<>();
  }

  @Override
  public void saveToken(String provider, String tenantId, OAuthCredentials OAuthCredentials) {
    tokenStore.computeIfAbsent(provider, k -> new ConcurrentHashMap<>()).put(tenantId, OAuthCredentials);
  }

  @Override
  public Optional<OAuthCredentials> getToken(String provider, String tenantId) {
    var providerStore = tokenStore.get(provider);
    if (providerStore == null) {
      return Optional.empty();
    }

    var token = providerStore.get(tenantId);
    return Optional.ofNullable(token);
  }
}
