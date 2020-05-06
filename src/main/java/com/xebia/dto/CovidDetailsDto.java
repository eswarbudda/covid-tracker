package com.xebia.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CovidDetailsDto {

  private Long id;
  private String location;
  private Long tested;
  private Long confirmed;
  private Long active;
  private Long recovered;
  private Long dead;

  private LocalDateTime updateTime;
}
