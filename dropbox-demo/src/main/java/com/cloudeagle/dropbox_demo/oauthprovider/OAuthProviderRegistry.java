package com.cloudeagle.dropbox_demo.oauthprovider;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class OAuthProviderRegistry {
  private final Map<String, OAuthProvider> registry;

  OAuthProviderRegistry(Map<String, OAuthProvider> registry) {
    this.registry = registry;
  }

  public OAuthProvider getOAuthProvider(String providerName) {
    var provider = registry.get(providerName);
    if (provider == null) {
      throw new IllegalArgumentException("No OAuth client registered for: " + providerName);
    }

    return provider;
  }
}
