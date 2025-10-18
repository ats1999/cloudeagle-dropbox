package com.cloudeagle.dropbox_demo.config;

import com.dropbox.core.DbxRequestConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
  @Bean
  public DbxRequestConfig getDbxRequestConfig() {
    return DbxRequestConfig.newBuilder("cloudEagleApp").build();
  }
}
