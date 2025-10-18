package com.cloudeagle.dropbox_demo;

import com.dropbox.core.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class Test {
  @GetMapping
  public void test(HttpServletRequest httpServletRequest) {
    DbxRequestConfig config =
        DbxRequestConfig.newBuilder("cloudEagleApp")
            .withUserLocale(Locale.getDefault().toString())
            .build();
    DbxAppInfo appInfo = new DbxAppInfo("k5vgfxdhjmkauy0", "y9qp541r8vp1clb");
    DbxWebAuth webAuth = new DbxWebAuth(config, appInfo);

    String sessionKey = "dropbox-auth-csrf-token";
    DbxSessionStore csrfTokenStore =
        new DbxStandardSessionStore(httpServletRequest.getSession(), sessionKey);
    DbxWebAuth.Request webAuthRequest =
        DbxWebAuth.newRequestBuilder()
            .withRedirectUri("http://localhost:8080/dropbox/oauth/callback", csrfTokenStore)
            .withTokenAccessType(TokenAccessType.OFFLINE)
            .build();

    String res = webAuth.authorize(webAuthRequest);

    System.out.println("Done..");
    System.out.println(res);
  }
}
