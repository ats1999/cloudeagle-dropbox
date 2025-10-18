package com.cloudeagle.dropbox_demo.controller;

import com.cloudeagle.dropbox_demo.oauthprovider.OAuthProviderRegistry;
import com.cloudeagle.dropbox_demo.tokenstore.OAuthCredentialStore;
import com.dropbox.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/{provider}/oauth/callback")
public class OAuthCallBackController {
  private final OAuthCredentialStore OAuthCredentialStore;
  private final OAuthProviderRegistry oAuthProviderRegistry;

  OAuthCallBackController(OAuthCredentialStore OAuthCredentialStore, OAuthProviderRegistry oAuthProviderRegistry) {
    this.OAuthCredentialStore = OAuthCredentialStore;
    this.oAuthProviderRegistry = oAuthProviderRegistry;
  }

  @GetMapping
  public RedirectView oAuthCallback(@PathVariable String provider, @RequestParam String code)
      throws DbxException {

    var token = oAuthProviderRegistry.getOAuthProvider(provider).exchangeCodeForToken(code);

    // NOTE: tenantId is hardcoded here for simplicity
    String tenantId = "rahul";
    OAuthCredentialStore.saveToken(provider, tenantId, token);

    return new RedirectView("/dropbox/members/list");
  }
}
