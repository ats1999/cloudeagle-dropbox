package com.cloudeagle.dropbox_demo.oauthprovider;

import com.cloudeagle.dropbox_demo.tokenstore.Token;
import com.dropbox.core.DbxException;

public interface OAuthProvider {
  Token exchangeCodeForToken(String provider, String code) throws DbxException;
}
