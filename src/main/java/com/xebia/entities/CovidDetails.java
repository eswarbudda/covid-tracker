package com.xebia.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CovidDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String locationName;

  @Column(nullable = false)
  private Long tested;

  @Column(nullable = false, columnDefinition = "bigint default 0")
  private Long confirmed;

  @Column(nullable = false, columnDefinition = "bigint default 0")
  private Long active;

  @Column(nullable = false, columnDefinition = "bigint default 0")
  private Long recovered;

  @Column(nullable = false, columnDefinition = "bigint default 0")
  private Long dead;

  @Column(nullable = false)
  private LocalDateTime createTime;

  private LocalDateTime updateTime;


}
