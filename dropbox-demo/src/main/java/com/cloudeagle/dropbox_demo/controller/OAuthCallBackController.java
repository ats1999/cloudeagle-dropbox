package com.cloudeagle.dropbox_demo.controller;

import com.cloudeagle.dropbox_demo.oauthprovider.OAuthProviderRegistry;
import com.cloudeagle.dropbox_demo.tokenstore.TokenStore;
import com.dropbox.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/{provider}/oauth/callback")
public class OAuthCallBackController {
  private final TokenStore tokenStore;
  private final OAuthProviderRegistry oAuthProviderRegistry;

  OAuthCallBackController(TokenStore tokenStore, OAuthProviderRegistry oAuthProviderRegistry) {
    this.tokenStore = tokenStore;
    this.oAuthProviderRegistry = oAuthProviderRegistry;
  }

  @GetMapping
  public RedirectView oAuthCallback(@PathVariable String provider, @RequestParam String code)
      throws DbxException {

    var token =
        oAuthProviderRegistry.getOAuthProvider(provider).exchangeCodeForToken(provider, code);

    // NOTE: userid is hardcoded here for simplicity
    tokenStore.saveToken(provider, "rahul", token);

    return new RedirectView("/");
  }
}
