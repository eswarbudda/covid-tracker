package com.xebia.entities;


import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Location {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "covid_details_id")
  private CovidDetails covidDetails;

}
