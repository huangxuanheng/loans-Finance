package com.harry.boostrap.startup.analyze.utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailInfo {

  private String host;
  private String from;
  private String address;
  private String subject;
  private String content;
  private String filePath;
  private String port;
  private String password;
  private String nickName;
}
