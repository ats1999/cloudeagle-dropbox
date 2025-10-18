package com.cloudeagle.dropbox_demo.tokenstore;

import java.util.Optional;

public interface TokenStore {
  void saveToken(String provider, String userId, Token token);

  Optional<Token> getToken(String provider, String userId);
}
