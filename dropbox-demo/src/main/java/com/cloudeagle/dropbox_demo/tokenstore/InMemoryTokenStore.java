package com.cloudeagle.dropbox_demo.tokenstore;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class InMemoryTokenStore implements TokenStore {
  private final ConcurrentHashMap<String, ConcurrentHashMap<String, Token>> tokenStore;

  InMemoryTokenStore() {
    this.tokenStore = new ConcurrentHashMap<>();
  }

  @Override
  public void saveToken(String provider, String userId, Token token) {
    tokenStore.computeIfAbsent(provider, k -> new ConcurrentHashMap<>()).put(userId, token);
  }

  @Override
  public Optional<Token> getToken(String provider, String userId) {
    var providerStore = tokenStore.get(provider);
    if (providerStore == null) {
      return Optional.empty();
    }

    var token = providerStore.get(userId);
    return Optional.ofNullable(token);
  }
}
