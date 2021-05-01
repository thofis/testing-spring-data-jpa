package com.example.accessingdatajpa;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  private String street;

  private String city;

}
