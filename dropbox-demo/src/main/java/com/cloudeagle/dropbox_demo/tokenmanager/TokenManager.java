package com.cloudeagle.dropbox_demo.tokenmanager;

import com.cloudeagle.dropbox_demo.model.OAuthCredentials;
import com.cloudeagle.dropbox_demo.oauthprovider.OAuthProvider;
import com.cloudeagle.dropbox_demo.oauthprovider.OAuthProviderRegistry;
import com.cloudeagle.dropbox_demo.tokenstore.OAuthCredentialStore;
import org.springframework.stereotype.Component;

@Component
public class TokenManager {
  private final OAuthCredentialStore OAuthCredentialStore;
  private final OAuthProviderRegistry providerRegistry;

  TokenManager(OAuthCredentialStore OAuthCredentialStore, OAuthProviderRegistry oAuthProviderRegistry) {
    this.OAuthCredentialStore = OAuthCredentialStore;
    this.providerRegistry = oAuthProviderRegistry;
  }

  public String getAccessToken(String provider, String tenantId) throws Exception {
    OAuthCredentials creds = getOAuthCredentials(provider, tenantId);

    if (creds.isExpired()) {
      OAuthProvider oAuthProvider = providerRegistry.getOAuthProvider(provider);
      creds =
          oAuthProvider.refreshOauthCredentials(creds.getAccessToken(), creds.getRefreshToken());
      OAuthCredentialStore.saveToken(provider, tenantId, creds);
    }

    return creds.getAccessToken();
  }

  public String getRefreshToken(String provider, String tenantId) throws Exception {
    OAuthCredentials creds = getOAuthCredentials(provider, tenantId);
    return creds.getRefreshToken();
  }

  private OAuthCredentials getOAuthCredentials(String provider, String tenantId) {
    return OAuthCredentialStore
        .getToken(provider, tenantId)
        .orElseThrow(() -> new IllegalStateException("User token not found for user: " + tenantId));
  }
}
